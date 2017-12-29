import java.util.ArrayList;

/**
 * @author Robert Thompson
 * Dec 4, 2017
 */

public class FileManager
{
	// Creates a File object to store file requests in an array list
	static final ArrayList<File> FileManager = new ArrayList<>();
	// Variable used to determine if the device is able to be allocated
	boolean check;

	/**
	 * Default FileManager constructor that adds 3 file requests to the array list
	 * ADD 3 File Request Devices
	 */
	public FileManager()
	{
		FileManager.add(new File(1, "OPEN"));
		FileManager.add(new File(2, "SAVE"));
		FileManager.add(new File(3, "CLOSE"));
	}
	
	/**
	 * Method that tries to allocate a Device to its requested file request
	 * @param currentDevice
	 */
	public boolean allocateDevice (Device currentDevice)
	{
		// For loop that will run until the end of the FileManager array list size
		for (int i = 0; i < FileManager.size(); i++)
		{
			// Creates a temporary File object and sets it equal to the File at the "i" index
			File temp = FileManager.get(i);
			
			// If statement that checks if the temporary File object is free and
			// If the temporary File ID is equal current devices file desired
			if (temp.isFree() && temp.getFileID() == currentDevice.getFileDesired())
			{
				// Prints that the current device has been allocated to the file request at the given index
				System.out.println("Device: " + currentDevice.deviceName + " has been allocated to use File Request: " + FileManager.get(i).getFileName());
				System.out.println();
				
				// Assigns the device for the specific file request
				FileManager.get(i).assignDevice(currentDevice);
				System.out.println(FileManager);
				
				// Sets check equal to true to indicate that it was allocated.
				check = true;
				
				// Returns check
				return check;
			}
		}
		
		// Print that the device couldn't be allocated to the file request because it is busy
		returnWaitingDevice(currentDevice);	
		
		// Returns check
		return check;
	}
	
	/**
	 * Method that will print that the file requested for the current device is busy
	 * @param currentDevice
	 */
	public void returnWaitingDevice (Device currentDevice)
	{
		int fileID = currentDevice.getFileDesired();
		String fileName = "";
		
		if (fileID == 1)
		{
			fileName = "OPEN";
		}
		else if (fileID == 2)
		{
			fileName = "SAVE";
		}
		else
		{
			fileName = "CLOSE";
		}
		
		// Print that the file requested for the current device is busy
		System.out.println("Device Requesting File: " + fileName + " is being used.");
		
		// Sets check equal to false to indicate that the device was not allocated to the specific file request
		check = false;
	}
	
	/**
	 * Method that will deallocate the file request at the current device
	 * @param currentDevice
	 */
	public void deallocateDevice (Device currentDevice)
	{
		// For loop that will run until the end of the FileManager array list size
		for (int i = 0; i < FileManager.size(); i++)
		{	
			// Creates a temporary File object and sets it equal to the File at the "i" index
			File temp = FileManager.get(i);
			
			// If statement that will check to see if the file request at the given index is NOT free
			// Or in other words, if the File is busy
			if (!FileManager.get(i).isFree())
			{
				// If statement that checks if the File at the given index is assigned a device with a device ID 
				// that is equal to the current device's ID
				if (FileManager.get(i).getDevice().getDeviceID() == currentDevice.getDeviceID())//&& !FileManager.get(i).isFree()
				{
					// Prints that the current device has finished using the file request it requested
					System.out.println("Device: " + currentDevice.getDeviceName() + " has finished using " + temp.getFileName());
					
					// Sets the file free 
					temp.setFileFree();
					// Prints the FileManager array list
					System.out.println(FileManager);
					
					return;
				}
			}
		}
		
		return;
	}
}
