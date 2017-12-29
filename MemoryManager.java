import java.util.ArrayList;

/**
 * @author Robert Thompson
 * Nov 12, 2017
 */

public class MemoryManager 
{
	// Creates a Partition object to store partitions in an array list
	static final ArrayList<Partition> MemoryManager = new ArrayList<>();
	
	/**
	 * Default MemoryManager constructor that adds ten partitions to the array list 
	 */
	public MemoryManager()
	{
		MemoryManager.add(new Partition(1, 25));
		MemoryManager.add(new Partition(2, 50));
		MemoryManager.add(new Partition(3, 100));
		MemoryManager.add(new Partition(4, 125));
		MemoryManager.add(new Partition(5, 200));
		MemoryManager.add(new Partition(6, 25));
		MemoryManager.add(new Partition(7, 50));
		MemoryManager.add(new Partition(8, 100));
		MemoryManager.add(new Partition(9, 125));
		MemoryManager.add(new Partition(10, 200));
	}
	
	/**
	 * Method that tries to allocate a job to a specific partition 
	 * @param job
	 */
	public void allocateJob (Job job)
	{
		// If statement that will check to see if the job size is greater than 0 and less than or equal to the max partition
		if (job.getJobSize() > 0 && job.getJobSize() <= maxPartition())
		{
			// For loop that will run until the end of the MemoryManager array list size
			for (int i = 0; i < MemoryManager.size(); i++)
			{
				// Create a temporary Partition object and sets it equal to Partition at the "i" index
				Partition temp = MemoryManager.get(i);
				
				// If statement that checks if the temporary Partition object is free and
				// If the temporary Partition size is greater than or equal to the job size
				if (temp.isFree() && temp.getPartitionSize() >= job.getJobSize())
				{
					// Schedules a new job
					CPU.scheduler.scheduleJob(job);
					// Puts the job in the CPUJobList hash map
					CPU.CPUJobList.put(job.jobID, job);
					// Puts the job in the JobStats hash map
					CPU.JobStats.put(job.jobID, job);


					System.out.println("Job ID: " + job.getJobID() + " | Size: " + job.getJobSize() + " has been allocated to \nPartition ID: " + MemoryManager.get(i).getPartitionID());
					
					// Assigns the partition at index "i" to the job 
					MemoryManager.get(i).assignJob(job);
					
					// Prints the MemoryManager array list
					System.out.println(MemoryManager);
					
					// Returns to start allocating again
					return;
				}
				
			}
			
			// If a job is less than or greater than the max partition or
			// If the job cannot be allocated to a partition because it is busy running / or has been allocated already
			// The job will then be added back to the JOB_QUEUE
			System.out.println("Job ID: " + job.getJobID() + " | Size: " + job.getJobSize() +  " cannot be allocated at the time. \nNow adding it back to job queue...");
			returnUnAllocatedJob(job);
			
			// Returns to start allocating again
			return;
		}
		// Delete job as a whole as it is too big or to small for min/max partitions
		System.out.println("Job ID: " + job.getJobID() + " | Size: " + job.getJobSize() +  " is removed due to size to large.");
		removeJob(job);

		// Returns to start allocating again
		return;
	}
	
	/**
	 * Method that returns the unallocated job to the JOB_QUEUE in the CPU.java class
	 * By adding it back to the JOB_QUEUE
	 * @param job
	 */
	private void returnUnAllocatedJob (Job job)
	{
		CPU.JOB_QUEUE.add(job);
	}
	
	/**
	 * Method that will remove the job that is too big from use in the future
	 * @param job
	 */
	public void removeJob (Job job)
	{
		CPU.DELETED_JOB_QUEUE.add(job);
	}
	
	/**
	 * Method that will free the partition at the specific index if it is busy
	 * @param job
	 */
	public void deallocatePartition (int jobID)
	{
		for (int i = 0; i < MemoryManager.size(); i++)
		{
			Partition temp = MemoryManager.get(i);
			
			if (!MemoryManager.get(i).isFree())
			{
				if (MemoryManager.get(i).getJob().getJobID() == jobID)
				{
					temp.setPartitionFree();
					System.out.println(MemoryManager);
					
					return;
				}
			}
		}
		
		return;
	}
	
	/**
	 * Method that calculates the max partitions and returns the value
	 * @return max
	 */
	public int maxPartition()
	{
		int max = 0;
		int temp = 0;
		
		for (int i = 0; i < MemoryManager.size(); i++)
		{
			temp = MemoryManager.get(i).getPartitionSize();

			if (temp > max)
			{
				max = temp;
			}
		}

		return max;
	}
}
