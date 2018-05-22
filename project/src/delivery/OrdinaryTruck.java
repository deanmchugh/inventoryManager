package delivery;

import exceptions.DeliveryException;
import objects.Stock;

/**
 * 
 * @author Tim
 */
public class OrdinaryTruck extends Truck {

	/**
	 * Constructs a Truck object to contain a Stock object for a price.
	 * @param cost Float value of the cost to hire the truck in dollars.
	 * @param capacity Integer value of the maximum number of items.
	 * @param contents A Stock object containing the Items and quantity of Items to be transported.
	 * @throws DeliveryException Throws an exception with a message if inputs are out of defined bounds.
	 * @author Tim
	 */
	public OrdinaryTruck(float cost, int capacity, Stock contents) throws DeliveryException{
		super(cost, capacity, contents);
	}

	/**
	 * @throws Throws exception as an OrdinaryTruck does not have a set temperature.
	 * @author Tim
	 */
	@Override
	public int getTemp() throws DeliveryException {
		throw new DeliveryException("Ordinary trucks do not have a set temperature!");
	}
	
}
