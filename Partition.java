/**
 * @author Robert Thompson
 * Nov 12, 2017
 */

public class Partition
{
	// Stores the partition ID
	public int partitionID;
	// Stores the partition size
	public int partitionSize;
	// Stores the true / false value of whether or not the partition is free
	public boolean partitionFree = true;
	
	// Create a Job object that is set to null
	private Job job = null;
	
	/**
	 * Default Partition constructor that creates a partition with an ID and size
	 * @param partID
	 * @param partSize
	 */
	public Partition (int partID, int partSize)
	{
		this.partitionID = partID;
		this.partitionSize = partSize;
	}
	
	/**
	 * Method that prints the partition ID and size, and whether or not it is free / busy
	 * If it is busy, it will print the job ID and size of the job
	 */
	public String toString()
	{
		return "\tPartition: " + this.partitionID 
				+ " | Size: " + this.partitionSize + " \t\t\tStatus: " 
				+ (partitionFree? "Free" : ("Busy\t Job: " + job.getJobID() 
				+ " | Size: " + job.getJobSize())) + "\n";
	}
	
	/**
	 * Method that will return the partition ID
	 * @return partitionID
	 */
	public int getPartitionID()
	{
		return partitionID;
	}
	
	/**
	 * Method that will return the partition size 
	 * @return partitionSize
	 */
	public int getPartitionSize()
	{
		return partitionSize;
	}
	
	/**
	 * Method that will return true if the partition is free and false if the partition is busy
	 * @return true if partition is free / false if partition is busy
	 */
	public boolean isFree()
	{
		return partitionFree;
	}
	
	/**
	 * Method that returns the job object
	 * @return job
	 */
	public Job getJob()
	{
		return job;
	}
	
	/**
	 * Method that assigns a job to a specific partition and sets the partition to busy;
	 * @param job
	 */
	public void assignJob (Job job)
	{
		this.job = job;
		partitionFree = false;
	}
	
	/**
	 * Method that sets the partition free by setting the partitionFree to true and partitionRunning to false
	 */
	public void setPartitionFree()
	{
		System.out.println("Partition ID: " + partitionID + " is now free.\n");
		partitionFree = true;
	}
	
}
