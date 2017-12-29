import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Robert Thompson
 * Nov 12, 2017
 */

public class JobQueue extends LinkedList<Job>
{
    ReentrantLock lock = new ReentrantLock();
	
    /**
     * Method that will add a Job object to the JOB_QUEUE 
     */
	public boolean add (Job job)
	{
		lock.lock();
		
		try
		{
			return super.add(job);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * Method that will remove a Job object from the JOB_QUEUE
	 */
	public Job remove() throws NoSuchElementException
	{
		lock.lock();
		
		try
		{
			return super.remove();
		}
		finally
		{
			lock.unlock();
		}
		
	}
	
	/**
	 * Method that checks to see if the JOB_QUEUE is empty
	 */
	public boolean isEmpty()
	{
		lock.lock();
		
		try
		{
			return super.isEmpty();
		}
		finally
		{
			lock.unlock();
		}
	}
}

