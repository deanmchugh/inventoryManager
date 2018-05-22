package delivery;

import exceptions.DeliveryException;
import objects.Stock;

/**
 * 
 * @author Tim
 */
public abstract class Truck {

	private int cost;
	private int capacity;
	private Stock contents;
	
	public Truck(int cost, int capacity, Stock contents) throws DeliveryException {
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
	
}
