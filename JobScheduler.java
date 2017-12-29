/**
 * @author Robert Thompson
 * Nov 12, 2017
 */

public class JobScheduler
{
	// Creates a FirstComeFirstServed object
	private FirstComeFirstServed queue;
		
	/**
	 * Default constructor that will create a new FCFSQueue 
	 */
	public JobScheduler()
	{
		queue = new FirstComeFirstServed();
	}
	
	/**
	 * Method that will remove a job from the PCB based on the Job's ID
	 * @param pcb
	 */
	public void removeJob (PCB pcb)
	{
		queue.removePCB(pcb.getJobID());
	}
	
	/**
	 * Method that will remove a job from the PCB based on the Job's ID if its state if FINISHED
	 * Or will change the job's state to HOLD if it is doing an IO interrupt
	 * @param pcb
	 * @return PCB of state change
	 */
	public PCB handleJobState (PCB pcb)
	{
		if (pcb.getState() == State.FINISHED)
		{
			queue.removePCB(pcb.getJobID());
			return null;
		}
		else if (pcb.getState() == State.HOLD)
		{
			pcb = pcb.changeJobState(State.HOLD);
		}
		else if (pcb.getState() == State.WAITING)
		{
			pcb = pcb.changeJobState(State.WAITING);
		}
		else if (pcb.getState() == State.READY)
		{
			pcb = pcb.changeJobState(State.READY);
		}
		else if (pcb.getState() == State.RUNNING)
		{
			pcb = pcb.changeJobState(State.RUNNING);
		}
		
		return pcb;
	}
	
	/**
	 * Method that will add a new job to the PCB
	 * The PCB object will then be added to the FCFSQueue object for use
	 * @param job
	 */
	public void scheduleJob (Job job)
	{
		queue.addPCB(new PCB(job));
	}
	
	/**
	 * Method that will schedule a job that was previously interrupted back into the PCB 
	 * @param pcb
	 */
	public void scheduleInterruptedJob (PCB pcb)
	{
		pcb = handleJobState(pcb);
		
		if (pcb == null)
		{
			return;
		}
		
		if (pcb.getState() == State.FINISHED)
		{
			queue.removePCB(pcb.getJobID());
		}
		else
		{
			pcb = handleJobState(pcb);

			queue.addPCB(pcb);
		}
				
	}
	
	/**
	 * Method that will get the 
	 * @return
	 */
	public PCB getNextJob()
	{
		PCB pcb = queue.deQueue();
		
		if (pcb == null)
		{
			return null;
		}
		
		return pcb;
	}
}
