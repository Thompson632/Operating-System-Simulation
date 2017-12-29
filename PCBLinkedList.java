/**
 * @author Robert Thompson
 * Nov 12, 2017
 */

public class PCBLinkedList
{
	// Creates PCB object and sets it equal to null
	private PCB pcb = null;
	// Creates PCBLinkedList object and sets it equal to null
	public PCBLinkedList next = null;
	
	/**
	 * Empty constructor
	 */
	public PCBLinkedList() {}
	
	/**
	 * Default PCB constructor that creates a PCB object
	 * @param pcb
	 */
	public PCBLinkedList (PCB pcb)
	{
		this.pcb = pcb;
	}
	
	/**
	 * PCB constructor that is used to set the head of the PCBLinkedList in the FCFS.java class
	 * @param pcb
	 * @param next
	 */
	public PCBLinkedList (PCB pcb, PCBLinkedList next)
	{
		this.pcb = pcb;
		this.next = next;
	}
	
	/**
	 * Method that gets the PCB object from the PCBLinkedList
	 * @return pcb
	 */
	public PCB getPCB()
	{
		return pcb;
	}
	
}
