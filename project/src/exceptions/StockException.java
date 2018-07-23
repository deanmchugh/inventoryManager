package exceptions;

/**
 * Exception class used to generate exceptions related to Item, Stock, and Store objects.
 * @author Tim
 */
@SuppressWarnings("serial")
public class StockException extends Exception {

	/**
	 * Default Exception constructor.
	 */
	public StockException() {
		super();
	}
	
	
	/**
	 * Throws exception with message.
	 * @param message String containing message explaining why exception was thrown.
	 */
	public StockException(String message){
		super(message);
	}
	
}
