package delivery;

import exceptions.DeliveryException;
import objects.Stock;

/**
 * 
 * @author Tim
 */
public class RefrigeratedTruck extends Truck {

	private static final int MIN_TEMP = -20;
	private static final int MAX_TEMP = 10;
	
	private int temperature;
	
	/**
	 * Constructs a Truck object to contain a Stock object for a price, with a set temperature.
	 * @param cost Float value of the cost to hire the truck in dollars.
	 * @param capacity Integer value of the maximum number of items.
	 * @param contents A Stock object containing the Items and quantity of Items to be transported.
	 * @param temperature An Integer value of the set temperature of the Truck.
	 * @throws DeliveryException Throws an exception with a message if inputs are out of defined bounds.
	 * @author Tim
	 */
	public RefrigeratedTruck(float cost, int capacity, Stock contents, int temperature) throws DeliveryException {
		super(cost, capacity, contents);
		if (temperature < MIN_TEMP || temperature > MAX_TEMP) {
			throw new DeliveryException("Temperature not within bounds!");
		}
		this.temperature = temperature;
	}

	/**
	 * Getter method for the temperature of the truck.
	 * @return The integer temperature of the Truck.
	 * @author Tim
	 */
	@Override
	public int getTemp() {
		return this.temperature;
	}
	
}
