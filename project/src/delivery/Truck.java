package delivery;

import exceptions.DeliveryException;
import objects.Stock;

/**
 * Abstract class that serves as a basis for both the RefridgeratedTruck and OrdinaryTruck classes.
 * Constructs a Truck object that has both a cost, a capacity, and contents that must be within certain bounds.
 * @author Tim
 */
public abstract class Truck {

	private float cost;
	private int capacity;
	private Stock contents;
	
	/**
	 * Constructs a Truck object to contain a Stock object for a price.
	 * @param cost Float value of the cost to hire the truck in dollars.
	 * @param capacity Integer value of the maximum number of items.
	 * @param contents A Stock object containing the Items and quantity of Items to be transported.
	 * @throws DeliveryException Throws an exception with a message if inputs are out of defined bounds.
	 * @author Tim
	 */
	public Truck(float cost, int capacity, Stock contents) throws DeliveryException {
		if (cost < 0) {
			throw new DeliveryException("Truck cannot have negative cost!");
		} else if (capacity < 0) {
			throw new DeliveryException("Truck cannot have negative capacity!");
		} else if (contents == null) {
			throw new DeliveryException("Truck must have valid contents!");
		}
		
		this.cost = cost;
		this.capacity = capacity;
		this.contents = contents;
	}
	
	/**
	 * Getter method for Truck's cost.
	 * @return A float value of the truck's cost in dollars.
	 * @author Tim
	 */
	public float getCost() {
		return this.cost;
	}
	
	/**
	 * Getter method for the total capacity of the Truck.
	 * @return An integer capacity of the truck.
	 * @author Tim
	 */
	public int getCargoCap() {
		return this.capacity;
	}
	
	/**
	 * Getter method for the contents of the Truck.
	 * @return A Stock object representing the contents of the Truck.
	 * @author Tim
	 */
	public Stock getCargo() {
		return this.contents;
	}
	
	/**
	 * Abstract method to be implemented differently by refrigeratedTruck and ordinaryTruck.
	 * @return An integer value of the temperature of the Truck.
	 * @throws DeliveryException Throws exception if the Truck has no set temperature.
	 * @author Tim
	 */
	abstract public int getTemp() throws DeliveryException;

}
