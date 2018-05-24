package exceptions;

/**
 * 
 * @author Tim
 */
@SuppressWarnings("serial")
public class DeliveryException extends Exception {

	/**
	 * Default Exception constructor.
	 */
	public DeliveryException() {
		super();
	}
	
	/**
	 * Throws exception with message.
	 * @param message String containing message explaining why exception was thrown.
	 */
	public DeliveryException(String message){
		super(message);
	}
	
}
