/**
 * @author Robert Thompson
 * Nov 12, 2017
 */

public enum State
{
	NEW
	{
		/**
		 * Method that prints the state of a PCB job object as NEW.
		 * This method is used when a PCB for a job object is FIRST created
		 */
		public String toString()
		{
			return "NEW";
		}
	},
	
	READY
	{
		/**
		 * Method that prints the state of a PCB job object as READY.
		 * This method is used after the job has been allocated to a device 
		 * but the file request by the device is busy.
		 */
		public String toString()
		{
			return "READY";
		}
	},
	
	WAITING
	{
		/**
		 * Method that prints the state of a PCB job object as WAITING.
		 * This method is used after a job has been allocated to a device 
		 * and the device has been allocated to it's desired file request.
		 */
		public String toString()
		{
			return "WAITING";
		}
	},
	
	HOLD
	{
		/**
		 * Method that prints the state of a PCB job object as HOLD.
		 * This method is used when a job has not been allocated to its desired device.
		 */
		public String toString()
		{
			return "HOLD";
		}
	},
	
	RUNNING
	{
		/**
		 * Method that prints the state of a PCB job object as RUNNING.
		 * This method is used after a job has run its IO interrupt successfully.
		 */
		public String toString()
		{
			return "RUNNING";
		}
	},
	
	FINISHED
	{
		/**
		 * Method that prints the state of a PCB job object as FINISHED.
		 * This method is used when the job has completed running successfully.
		 */
		public String toString()
		{
			return "FINISHED";
		}
	};
	
}
