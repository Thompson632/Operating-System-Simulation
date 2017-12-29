import java.util.HashMap;
import java.util.Random;

/**
 * @author Robert Thompson
 * Nov 12, 2017
 */

public class CPU
{
	// Creates an object of the Random class to use to randomly generate a job size, job IO request, and CPU Burst time
	// Also, used for calculating probability of an IO device interrupt
	Random random = new Random();
	
	// Creates a static object of the JobScheduler class
	static JobScheduler scheduler;
	
    // Creates a static object of the MemoryManager class
    static MemoryManager memory = new MemoryManager();
	// Creates a static object of the DeviceManager class
    static DeviceManager device = new DeviceManager();

	// Creates an object of the PCB class that is equal to null
	PCB currentJob = null;
	
	// Stores the job ID of the job being added 
	int jobID = 1;
	
	// Stores the job start time of the job beginning to run
	long jobStartTime;
	
	// Stores the jobs to be run by the CPU in a HashMap of type Integer, Job
	// This is really storing the JobID, Job since the key is the JobID of the Job
	public static HashMap<Integer, Job> CPUJobList = new HashMap<Integer, Job>();
	
	// Stores the jobs that were allocated to a partition in a HashMap of type Integer, Job
	// This is really storing the JobID, Job since the key is the JobID of the Job
	// Using this HashMap to print the statistics of each job when printing the data
	public static HashMap<Integer, Job> JobStats = new HashMap<Integer, Job>();
	
	// Creates a linked list of type JobQueue which stores the jobs that were added 
	public static final JobQueue JOB_QUEUE = new JobQueue();
	
	// Creates a linked list of type JobQueue that stores the jobs that were unable to fit into a partition
	// because the partition was either busy or the job was too big
	public static final JobQueue DELETED_JOB_QUEUE = new JobQueue();
	
	// Stores the CPU time for the specific Job ID
	public static HashMap<Integer, Long> CPUTime = new HashMap<Integer, Long>();
	// Stores the wait time for the specific Job ID
	public static HashMap<Integer, Long> JobWaitTime = new HashMap<Integer, Long>();
	// Stores the execution time for the specific Job ID
	public static HashMap<Integer, Long> JobExecutionTime = new HashMap<Integer, Long>();
	// Stores the arrival time for the specific Job ID
	public static HashMap<Integer, Long> JobArrivalTime = new HashMap<Integer, Long>();
		
	/**
	 * Default CPU constructor that creates an instance of the JobScheduler, which creates a a FCFS queue
	 */
	public CPU()
	{
		scheduler = new JobScheduler();
	}

	/**
	 * Method that will add a new job of random size, random IO request, and CPU burst time to the Job Queue
	 */
	public void addNewJob()
	{
		JOB_QUEUE.add(new Job(jobID, (random.nextInt(200) + 1), (random.nextInt(8) + 1)).setCPUBurstTime(random.nextInt(10) + 1) );

		jobAllocation(getNextJob());
		
		jobID++;
	}
	
	/**
	 * Method that is used to allocate job to a memory partition
	 * @param job
	 */
	public void jobAllocation(Job job)
	{
		JobArrivalTime.put(jobID, System.currentTimeMillis());
		
		memory.allocateJob(job);
	}
	
	/**
	 * Gets the next job from the JOB_QUEUE 
	 * @return the next job
	 */
	public Job getNextJob()
	{
		return JOB_QUEUE.remove();
	}
	
	/**
	 * Method that will terminate the job by removing it from the CPU Job List
	 * The method will also calculate the total CPU time and wait time of the Job
	 * @param jobID
	 */
	public void terminateJob (int jobID)
	{
		CPUTime.put(jobID, (System.currentTimeMillis() - currentJob.getStartTime()));
		
		JobWaitTime.put(jobID, (JobWaitTime.get(jobID) - jobStartTime));
		
		CPUJobList.remove(jobID);
		
		currentJob.changeJobState(State.FINISHED);
		
		System.out.println("Job " + jobID + " has finished.");
		
		// Deallocates the partition at the removed job ID
		memory.deallocatePartition(jobID);
		
		// If statement to try to allocate a new job if the JOB_QUEUE is not empty
		if (!JOB_QUEUE.isEmpty())
		{
			memory.allocateJob(getNextJob());
		}
		else
		{
			System.out.println("No more jobs waiting to be allocated to memory!");
		}
	}
	
	/**
	 * Method that will execute the job 
	 * Method has a 15% chance of simulating an IO Device Interrupt
	 */
	public void executeJob()
	{
		System.out.println("Running Current Job: \n" + currentJob);
		System.out.println();
		
		if (currentJob.getState() == State.HOLD)
		{
			System.out.println("Device is now free for job to perform IO Interrupt. \nReturning control to the CPU...");
			System.out.println();
			
			device.allocateJob(currentJob);
			
			System.out.println();
			
			return;
		}
		
		if (currentJob.getState() == State.READY)
		{
			System.out.println("Reattempting to allocate device to its requested file. \nReturning control to the CPU...");
			System.out.println();
			
			device.allocateJob(currentJob);
			
			System.out.println();
			
			return;
		}
		
		if (currentJob.getState() != State.HOLD && currentJob.getState() != State.WAITING && currentJob.getState() != State.RUNNING)
		{
			if (random.nextInt(1000) > 850)
			{
				System.out.println("Current job needs to perform an IO Interrupt. Attempting to allocate the job to the desired device. \nReturning control to the CPU...");
				System.out.println();
				
				device.allocateJob(currentJob);
				
				System.out.println();
				
				return;
			}
		}
		
		if (!JobWaitTime.containsKey(currentJob.getJobID()))
		{
			JobWaitTime.put(currentJob.getJobID(), System.currentTimeMillis());
		}
		
		JobWaitTime.put(currentJob.getJobID(), JobWaitTime.get(currentJob.getJobID()) 
				- (System.currentTimeMillis() - JobWaitTime.get(currentJob.getJobID())));
		
		currentJob.changeJobState(State.FINISHED);
	}
	
	/**
	 * Method will run the program by getting consistently getting the next job from the PCB, checking its status, and then removing it from the PCB
	 * If the job is in a waiting state, it will be re-added to the end of the queue to be run at a later time
	 */
	public void runCPU()
	{
		jobStartTime = System.currentTimeMillis();
		
		do
		{			
			currentJob = scheduler.getNextJob();
			
			if (currentJob == null)
			{
				System.out.println("________________________________________");
				System.out.println("No jobs in job queue! Printing statistics...");
				System.out.println("________________________________________");
				break;
			}
			
			printCPUStatus();

			System.out.println("Attempting to get a Job from the Job Queue...");
			System.out.println();
			
			if (!JobExecutionTime.containsKey(currentJob.getJobID()))
			{
				JobExecutionTime.put(currentJob.getJobID(), System.currentTimeMillis() - JobArrivalTime.get(currentJob.getJobID()));
			}
			
			if (currentJob.getState() == State.WAITING)
			{		
				device.deallocateDevice(currentJob);
			
				currentJob.finishIODeviceSimulation();
				
				System.out.println();
			}
			
			executeJob();
			
			if (currentJob.getState() == State.FINISHED)
			{
				terminateJob(currentJob.getJobID());
			}

		}
		
		while (currentJob != null);
	}
	
	/**
	 * Method that will print the list of jobs that are waiting to run
	 */
	public void printCPUStatus()
	{
		System.out.println("________________________________________");
		System.out.println("Current CPU Job List:");
		
		for (Job job : CPUJobList.values())
		{
			System.out.println("Job: " + job.jobID + " | Size: " + job.jobSize);
		}
		
		System.out.println("________________________________________");

	}
	
	/**
	 * Method that will print the statistics for each job in the job list
	 * The method will also calculate the average / total wait time and the average / total turnaround time
	 */
	public void printStats()
	{
		double averageWaitTime;
		double totalWaitTime = 0;
		
		double averageTurnAroundTime;
		double totalTurnAroundTime = 0;
		
		int numJobs = JobStats.size();

		System.out.println("================================================================================================= ");
        System.out.println("   Job ID  | Arrival Time (ms) | CPU Burst Time (ms) | Waiting Time (ms) | Turnaround Time (ms) ");
		System.out.println("================================================================================================= ");
 
		
        for (int i = 1; i < jobID; i++)
        {
        	if (JobStats.containsKey(i))
        	{
	        	System.out.println("      " + i  + "    |" 
	        			+ "         " + JobStats.get(i).getJobArrivalTime() + "         |"
						+ "          " + JobStats.get(i).getCPUBurstTime() + "         |"
						+ "         " + JobWaitTime.get(i) + "          |"
						+ "      " + (CPUTime.get(i) + JobWaitTime.get(i) + JobExecutionTime.get(i)) );
	        	
	        	totalWaitTime = totalWaitTime + JobWaitTime.get(i);
	        	totalTurnAroundTime = totalTurnAroundTime + CPUTime.get(i) + JobWaitTime.get(i) + JobExecutionTime.get(i);
        	}
        }
        
        averageWaitTime = totalWaitTime / numJobs;
        averageTurnAroundTime = totalTurnAroundTime / numJobs;
        
		System.out.println("============================================================================================= ");
        System.out.println("Average Waiting Time: " + averageWaitTime);
        System.out.println("Total Wait Time: " + totalWaitTime);
		System.out.println("============================================================================================= ");
        System.out.println("Average Turnaround Time: " + averageTurnAroundTime);
        System.out.println("Total Turnaround Time " + totalTurnAroundTime);
		System.out.println("============================================================================================= ");
	}
}

