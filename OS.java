import java.util.Scanner;

/**
 * @author Robert Thompson
 * Nov 12, 2017
 */

public class OS
{
	public static void main (String[] args)
	{
		int numJobs;
		CPU cpu = new CPU();
		
		Scanner scan = new Scanner(System.in);

		System.out.println("Project #7: OPERATING SYSTEMS SIMULATION");
		System.out.println("NOTE: Memory Manager Uses Partitions of Size: 25, 50, 100, 125, and 200.");
		System.out.println("NOTE: Device Manager Uses Devices: Printer, Disk Drive, USB Drive, Keyboard \nMonitor, Mouse, Scanner, and Speaker. ");
		System.out.println("NOTE: File Manager Uses File Requests: Open, Save, and Close");
		System.out.println();			
		
		System.out.println("Please enter the number of jobs that you'd like to add:");
		System.out.println("NOTE: Jobs will be added based on a random size, random device request, and random CPU burst time.");
		System.out.println("NOTE: Device File Requests are generated on a random basis.");
		numJobs = scan.nextInt();
		scan.close();
		
		System.out.println("Attempting to allocate jobs to memory partitions...\n");

		for (int i = 0; i < numJobs; i++)
		{
			cpu.addNewJob();
		}
		
		System.out.println("\nPartitioned Jobs Ready to Run: \nBeginning Job Execution....");

		cpu.runCPU();
		cpu.printStats();
		
	}
}
