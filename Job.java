/**
 * @author Robert Thompson
 * Nov 12, 2017
 */

public class Job
{
	// Job ID of the job
	public int jobID = 0;
	// Job size of the job
	public int jobSize;
	// Job arrival time
	public int jobArrivalTime = 0;
	// Job request IO device
	public int deviceDesired;
	
	// Job requested CPU burst time 
	private long CPUBurstTime;
	
	/**
	 * Default Job constructor that creates a job with only an ID for the PCB
	 * @param jobID
	 */
	public Job (int jobID)
	{
		this.jobID = jobID;
	}
	
	/**
	 * Job Constructor that creates a job with a job ID and job size
	 * @param jobID
	 * @param jobSize
	 */
	public Job (int jobID, int jobSize, int deviceDesired)
	{
		this.jobID = jobID;
		this.jobSize = jobSize;	
		this.deviceDesired = deviceDesired;
	}
	
	/**
	 * Method that returns the job ID of the job
	 * @return jobID
	 */
	public int getJobID()
	{
		return jobID;
	}
	
	/**
	 * Method that returns the job size of the job
	 * @return jobSize
	 */
	public int getJobSize()
	{
		return jobSize;
	}
    
    public int getDeviceDesired()
    {
        return deviceDesired;
    }
	
	/**
	 * Method that returns the job arrival time of the job
	 * @return jobArrivalTime
	 */
	public int getJobArrivalTime()
	{
		return jobArrivalTime;
	}
	
	/**
	 * Sets the job requested CPU burst time to the specific job
	 * @param ms
	 * @return
	 */
	public Job setCPUBurstTime (long ms)
	{
		this.CPUBurstTime = ms;
		return this;
	}
	
	/**
	 * Method that returns the CPU burst time of the job 
	 * @return
	 */
	public long getCPUBurstTime()
	{
		return CPUBurstTime;
	}
	
}
