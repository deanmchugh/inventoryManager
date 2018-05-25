package exceptions;

/**
 * Exception class used to construct exceptions related to file read/write.
 * @author Tim
 */
@SuppressWarnings("serial")
public class CSVFormatException extends Exception {
	
	/**
	 * Default Exception constructor.
	 */
	public CSVFormatException() {
		super();
	}
	
	
	/**
	 * Throws exception with message.
	 * @param message String containing message explaining why exception was thrown.
	 */
	public CSVFormatException(String message){
		super(message);
	}
	
}
