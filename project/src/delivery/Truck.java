package delivery;

import exceptions.DeliveryException;
import objects.Stock;

/**
 * Abstract class that defines the basic operations of a Truck, an object that stores a singular Stock object.
 * Contains a constructor method to store contents, and a getter method to retrieve them.
 * Abstract methods are implemented different in child classes.
 * @author Tim
 */
public abstract class Truck {

	private Stock contents;
	
	/**
	 * Basic constructor method for a Truck. Checks for valid contents, and then stores them.
	 * @param contents Stock object to represent the contents of the Truck.
	 * @throws DeliveryException Throws exception when contents is null (i.e. Not initialized)
	 * @author Tim
	 */
	public Truck(Stock contents) throws DeliveryException {
		if (contents == null) {
			throw new DeliveryException("Truck must have valid contents!");
		}
		this.contents = contents;
	}
	
	
	/**
	 * Getter method for Truck's contents.
	 * @return Stock object representing contents of the Truck.
	 * @author Tim
	 */
	public Stock getCargo() {
		return this.contents;
	}

	
	/**
	 * Getter method for the the cost of the Truck.
	 * @return Cost of the truck in dollars.
	 * @author Tim
	 */
	abstract public double getCost();
	
	
	/**
	 * Getter method for maximum capacity of Truck.
	 * @return Maximum Integer number of items Truck can carry.
	 * @author Tim
	 */
	abstract public int getCargoCap();

	
	/**
	 * Getter method for the temperature of the Truck.
	 * @return Integer set temperature of Truck.
	 * @throws DeliveryException Exception thrown by Truck types with no set temperature.
	 * @author Tim
	 */
	abstract public double getTemp() throws DeliveryException;

	
	/**
	 * Describes the type of Truck.
	 * @return A string containing the class name of the Truck.
	 * @author Tim
	 */
	abstract public String getType();
	
}
