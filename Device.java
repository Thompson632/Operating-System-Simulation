/**
 * @author Robert Thompson
 * Nov 12, 2017
 */

public class Device
{
	// Stores the device ID
	public int deviceID;
	// Stores the device name
	public String deviceName;
	// Stores the true / false value of whether or not the device is free
	public boolean deviceFree = true;
	
	// Stores the required file type
	public int fileDesired;
	
	// Creates a PCB object that is set to null
	private PCB job = null;
	
	/**
	 * Default Device constructor that creates a device with an ID and name
	 * @param deviceID
	 * @param deviceName
	 */
	public Device (int deviceID, String deviceName, int fileDesired)
	{
		this.deviceID = deviceID;
		this.deviceName = deviceName;
		this.fileDesired = fileDesired;
	}
	
	/**
	 * Method that prints the device ID and name, and whether or not it is free / busy
	 * If it is busy, it will print the Job ID
	 */
	public String toString()
	{
		return "\tDevice: " + this.deviceID
				+ " | Name: " + this.deviceName + "\t\t\tStatus: "
				+ (deviceFree? "Free" : ("Busy\t Job: " + job.getJobID())) + "\n";
	}
	
	/**
	 * Method that will return the device ID
	 * @return deviceID
	 */
	public int getDeviceID()
	{
		return deviceID;
	}
	
	/**
	 * Method that will return the device name
	 * @return deviceName
	 */
	public String getDeviceName()
	{
		return deviceName;
	}
	
	/**
	 * Method that will return the file desired
	 * @return fileDesired
	 */
	public int getFileDesired()
	{
		return fileDesired;
	}
	
	/**
	 * Method that will return true if the device is free and false if the device is busy
	 * @return true if device is free / false if device is busy
	 */
	public boolean isFree()
	{
		return deviceFree;
	}
	
	/**
	 * Method that assigns a PCB of a job to a specific device and sets the device to busy;
	 * @param job
	 */
	public void assignJob (PCB currentJob)
	{
		this.job = currentJob;
		deviceFree = false;
	}
	
	/**
	 * Method that will return the PCB of the current job
	 * @return job
	 */
	public PCB getJob()
	{
		return job;
	}
	
	/**
	 * Method that sets the device free by setting the deviceFree to true and deviceRunning to false
	 */
	public void setDeviceFree()
	{
		deviceFree = true;
	}
}
