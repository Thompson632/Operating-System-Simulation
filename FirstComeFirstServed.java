/**
 * FirstComeFirstServed is essentially a First In, First Out (FIFO) queue simulation.
 * However, I changed method names to make it easier to understand while writing the code.
 * @author Robert Thompson
 * Nov 12, 2017
 */

public class FirstComeFirstServed
{
	// Creates PCBLinkedList object head and sets it equal to null
	protected PCBLinkedList head = null;
	// Creates PCBLinkedList object tail and sets it equal to null
	protected PCBLinkedList tail = null;
	
	// Variable to store the current size of the PCBLinkedList
	public int currentSize = 0;
	
	/**
	 * Empty constructor
	 */
	public FirstComeFirstServed() {}
	
	/**
	 * Adds PCB to the FCFS as the new head of the linked list
	 * @param pcb
	 */
	protected void setHead (PCB pcb)
	{
		if (head == null)
		{
			head = new PCBLinkedList(pcb);
			tail = head;
		}
		else
		{
			PCBLinkedList newHead = new PCBLinkedList(pcb, head);
			head = newHead;
		}
	}
	
	/**
	 * Adds PCB to the FCFS linked list
	 * @param pcb
	 */
	public void addPCB (PCB pcb)
	{
		if (head == null)
		{
			setHead(pcb);
		}
		else
		{
			if (tail == null)
			{
				tail = new PCBLinkedList(pcb);
				
				PCBLinkedList temp;
				
				for (temp = head; temp.next != null; temp = temp.next)
				{
					
				}
				
				temp.next = tail;
			}
			else
			{
				tail.next = new PCBLinkedList(pcb);
				tail = tail.next;
			}
		}
		
		currentSize++;
	}
	
	/**
	 * Method that returns and removes the head of the queue 
	 * @return
	 */
	public PCB deQueue()
	{
		if (tail == null && head == null)
		{
			return null;
		}
		else
		{
			PCBLinkedList temp = head;
			head = head.next;
			
			tail = head == null ? null : tail;
			
			currentSize--;
			
			return temp.getPCB();
		}
	}

	/**
	 * Method that removes the PCB of the request job ID from the linked list
	 * @param jobID of the job
	 * @return true if the job is removed and alse if the job is not in the linked list
	 */
	public boolean removePCB (int jobID)
	{
		if (head == null)
		{
			return false;
		}
		
		PCBLinkedList previous = null;
		PCBLinkedList temp = head;
		
		boolean found = false;
		
		for (; temp != null && !found; temp = temp.next)
		{
			if (temp.getPCB().getJobID() == jobID)
			{
				found = true;
				break;
			}
			else
			{
				previous = temp;
			}
		}
		
		if (previous == null)
		{
			head = head.next;
			
			tail = head == null ? null : tail;
			currentSize = 0;
			return true;
		}
		else
		{
			if (!found)
			{
				return false;
			}
			
			if (temp.next == null)
			{
				tail = previous;
				previous.next = null;
			}
			else
			{
				previous.next = temp.next;
			}
			
			currentSize--;
			return true;
		}
	}
	
	/**
	 * Method that returns if the linked list is empty
	 * @return
	 */
	public boolean isEmpty()
	{
		return head == null;
	}
	
	/**
	 * Method that prints the linked list
	 */
	public void printFCFSQueue()
	{
		for (PCBLinkedList temp = head; temp != null; temp = temp.next)
		{
			System.out.println(temp.getPCB());
		}
	}
	
}

