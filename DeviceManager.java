import java.util.ArrayList;
import java.util.Random;
/**
 * @author Robert Thompson
 * Nov 12, 2017
 */

public class DeviceManager
{
	// Creates an object of the Random class to use to randomly generate a file request
	Random random = new Random();
	
	// Creates a Device object to store devices in an array list
	static final ArrayList<Device> DeviceManager = new ArrayList<>();
	
	// Creates a FileManager object to run the device in the FileManager class
	static FileManager FILE = new FileManager();

	/**
	 * Default DeviceManager constructor that adds eight devices to the array list
	 * ADD 8 IO DEVICES
	 */
	public DeviceManager()
	{
		DeviceManager.add(new Device(1, "PRINTER", (random.nextInt(3) + 1)));
		DeviceManager.add(new Device(2, "DISK DRIVE", (random.nextInt(3) + 1)));
		DeviceManager.add(new Device(3, "USB DRIVE", (random.nextInt(3) + 1)));
		DeviceManager.add(new Device(4, "KEYBOARD", (random.nextInt(3) + 1)));
		DeviceManager.add(new Device(5, "MONITOR", (random.nextInt(3) + 1)));
		DeviceManager.add(new Device(6, "MOUSE ", (random.nextInt(3) + 1))); // Extra space after "MOUSE" for print purposes
		DeviceManager.add(new Device(7, "SCANNER", (random.nextInt(3) + 1)));
		DeviceManager.add(new Device(8, "SPEAKER", (random.nextInt(3) + 1)));
	}
	
	/**
	 * Method that tries to allocate a PCB of a job to their requested IO device
	 * @param currentJob
	 */
	public void allocateJob (PCB currentJob)
	{
		// For loop that will run until the end of the DeviceManager array list size
		for (int i = 0; i < DeviceManager.size(); i++)
		{
			// Creates a temporary Device object and sets it equal to the Device at the "i" index
			Device temp = DeviceManager.get(i);
			
			// If statement that checks if the temporary Device object is free and
			// If the temporary Device ID is equal current jobs device desired
			if (temp.isFree() && temp.getDeviceID() == currentJob.getJob().getDeviceDesired())
			{
				// Prints that the current job has been allocated to the device at the given index
				System.out.println("Job ID: " + currentJob.jobID + " has been allocated to Device: " + DeviceManager.get(i).getDeviceName());
				
				// If statement that attempts to allocate the specific device to its required file request
				if (FILE.allocateDevice(temp))
				{		
					// Sets the current job state equal to WAITING
					currentJob.startIODeviceSimulation();
					
					// Assign the job for the specific device 
					DeviceManager.get(i).assignJob(currentJob);
					System.out.println(DeviceManager);
					
					// Reschedules the current job to the end of the queue with a state of WAITING
					CPU.scheduler.scheduleInterruptedJob(currentJob);
					
					// Returns to start allocating again
					return;
				}
				else
				{
					// Sets the current job state equal to READY
					currentJob.setJobDeviceReady();
					
					// Reschedules the current job to the end of the queue with a state of READY
					CPU.scheduler.scheduleInterruptedJob(currentJob);
					
					return;
				}
				
			}
		}
		
		// Print that the job couldn't be allocated to the device because it is busy
		// It is now getting rescheduled to the end of the job queue 
		returnWaitingJob(currentJob);
		
		// Returns to start allocating again
		return;
	}
	
	/**
	 * Method that will reschedule the unallocated PCB of the current job to the end of the queue.
	 * @param currentJob
	 */
	public void returnWaitingJob (PCB currentJob)
	{
		int deviceID = currentJob.getJob().getDeviceDesired();
		String deviceName = "";
		
		if (deviceID == 1)
		{
			deviceName = "PRINTER";
		}
		else if (deviceID == 2)
		{
			deviceName = "DISK DRIVE";

		}
		else if (deviceID == 3)
		{
			deviceName = "USB DRIVE";

		}
		else if (deviceID == 4)
		{
			deviceName = "KEYBOARD";

		}
		else if (deviceID == 5)
		{
			deviceName = "MONITOR";

		}
		else if (deviceID == 6)
		{
			deviceName = "MOUSE";

		}
		else if (deviceID == 7)
		{
			deviceName = "SCANNER";

		}
		else
		{
			deviceName = "SPEAKER";

		}
		
		// Print that the device that the current job is requesting is busy
		System.out.println("Job Requesting: " + deviceName + " is being used.");
		
		// Set state of the current job to HOLD
		currentJob.setJobDeviceHold();
		
		// Reschedules the current job to the end of the queue with a state of HOLD
		CPU.scheduler.scheduleInterruptedJob(currentJob);
		
		return;
	}
	
	/**
	 * Method that will deallocate the device at the current job
	 * @param currentJob
	 */
	public void deallocateDevice (PCB currentJob)
	{
		// For loop that will run until the end of the DeviceManager array list size
		for (int i = 0; i < DeviceManager.size(); i++)
		{	
			// Creates a temporary Device object and sets it equal to the Device at the "i" index
			Device temp = DeviceManager.get(i);
			
			// If statement that will check to see if the Device at the given index is NOT free
			// Or in other words, if the Device is busy
			if (!DeviceManager.get(i).isFree())
			{
				// If statement that checks if the Device at the given index is assigned a PCB with a job ID 
				// that is equal to the current job's ID
				if (DeviceManager.get(i).getJob().getJobID() == currentJob.getJobID()) //&& !DeviceManager.get(i).isFree()
				{
					// Prints that the current job has finished using the device it requested
					System.out.println("Job ID: " + currentJob.getJobID() + " has finished using " + temp.getDeviceName());
					
					// Deallocates the specific device from the File Request
					FILE.deallocateDevice(temp);
					
					// Sets the current job state equal to RUNNING
					currentJob.finishIODeviceSimulation();
					
					// Sets the device free 
					temp.setDeviceFree();
					// Prints the DeviceManager array list
					System.out.println(DeviceManager);
					
					return;
				}
			}
		}
		
		return;
	}
	
}
