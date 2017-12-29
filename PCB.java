/**
 * @author Robert Thompson
 * Nov 12, 2017
 */

public class PCB
{
	// Creates State object to store the current state of the job
	private State JOB_STATE;
	
	// The job ID of the current job 
	public int jobID = 0;
	
	// Stores the job's start time;
	private long jobStartTime = -1;

	// Job object 
	Job job;
		
	/**
	 * Default PCB constructor that is created based on the Job ID
	 * @param jobID
	 */
	public PCB (int jobID)
	{
		this.jobID = jobID;
		JOB_STATE = State.NEW;
		job = new Job(jobID);
				
		setJobStartTime(System.currentTimeMillis());
	}
	
	/**
	 * PCB constructor that is used to create a new job in the PCB and schedule it
	 * @param job
	 */
	public PCB (Job job)
	{
		this (job.jobID);
		this.job = job;
		
		setJobStartTime(System.currentTimeMillis());
	}
	
	/**
	 * Method that will output the job ID, state, and actual / desired CPU time
	 */
	public String toString()
	{
		return "Job ID: " + jobID 
				+ " | State: " + JOB_STATE 
				+ " | Actual CPU Time (ms): "+ (System.currentTimeMillis() - jobStartTime) 
				+ " | Desired CPU Time (ms): " + job.getCPUBurstTime();
	}
	
	/**
	 * Method that will return the job from the job class
	 * @return job
	 */
	public Job getJob()
	{
		return job;
	}
	
	/**
	 * Method that will change the current state of the job in the PCB
	 * @param state
	 * @return JOB_STATE
	 */
	public PCB changeJobState (State state)
	{
		JOB_STATE = state;
		return this;
	}
	
	/**
	 * Method that will be used to simulate an IO device interrupt
	 * This method is used when a job cannot be allocated to a device because it is already busy
	 * Unallocated job state is set to HOLD
	 */
	public void setJobDeviceHold()
	{
		JOB_STATE = State.HOLD;
	}
	
	/**
	 * Method that will be used to simulate an IO device / File request interrupt.
	 * This method is used when a job's request device cannot be allocated to its file request because it is already busy
	 * Unallocated device sets the job's current state to READY 
	 */
	public void setJobDeviceReady()
	{
		JOB_STATE = State.READY;
	}
	
	/**
	 * Method that will be used to simulate an IO device interrupt
	 * This method is used when a job has been allocated to a device
	 * Allocated job state is set to WAITING
	 */
	public void startIODeviceSimulation()
	{
		JOB_STATE = State.WAITING;
	}
	
	/**
	 * Method that will be used to simulate a completion of an IO device interrupt
	 * This is done by setting the state of the job equal to RUNNING
	 */
	public void finishIODeviceSimulation()
	{
		JOB_STATE = State.RUNNING;
	}
	
	/**
	 * Method that will return the current state of the job in the PCB
	 * @return
	 */
	public State getState()
	{
		return JOB_STATE;
	}
	
	/**
	 * Method that will return the job ID 
	 * @return jobID
	 */
	public int getJobID()
	{
		return jobID;
	}
	
	/**
	 * Method that is used to set the job start time
	 * @param startTime
	 */
	public void setJobStartTime (long startTime)
	{
		this.jobStartTime = startTime;
	}
	
	/**
	 * Method that will return the job start time
	 * @return jobStartTime
	 */
	public long getStartTime()
	{
		return this.jobStartTime;
	}
}

