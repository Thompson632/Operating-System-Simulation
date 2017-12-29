/**
 * @author Robert Thompson
 * Dec 4, 2017
 */

public class File
{
	// Stores the file ID
	public int fileID;
	// Stores the file name
	public String fileName;
	// Stores the true / false value of whether or not the file is free
	public boolean fileFree = true;
	
	// Creates a Device object that is set to null
	private Device device = null;
	
	/**
	 * Default file constructor that creates a file with an ID and name
	 * @param fileID
	 * @param fileName
	 */
	public File (int fileID, String fileName)
	{
		this.fileID = fileID;
		this.fileName = fileName;
	}
	
	/**
	 * Method that prints the file ID and name, and whether or not it is free / busy
	 * If it is busy, it will print the Device Name
	 */
	public String toString()
	{
		return "\tFile: " + this.fileID
				+ " | Name: " + this.fileName + "\t\t\t\tStatus: "
				+ (fileFree? "Free" : ("Busy\t Device: " + device.getDeviceName())) + "\n";
	}
	
	/**
	 * Method that will return the file ID
	 * @return fileID
	 */
	public int getFileID()
	{
		return fileID;
	}
	
	/**
	 * Method that will return the file name
	 * @return fileName
	 */
	public String getFileName()
	{
		return fileName;
	}
	
	/**
	 * Method that will return true if the file is free and false if the file is busy
	 * @return true if file is free / false if file is busy
	 */
	public boolean isFree()
	{
		return fileFree;
	}
	
	/**
	 * Method that assign a device object to a specific file and sets the file to busy
	 * @param currentDevice
	 */
	public void assignDevice (Device currentDevice)
	{
		this.device = currentDevice;
		fileFree = false;
	}
	
	/**
	 * Method that will return the Device object
	 * @return device
	 */
	public Device getDevice()
	{
		return device;
	}
	
	/**
	 * Method that sets the file free by setting the fileFree to true and fileRunning to false
	 */
	public void setFileFree()
	{
		fileFree = true;
	}
}
