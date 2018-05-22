package delivery;

import exceptions.DeliveryException;
import objects.Stock;

/**
 * 
 * @author Tim
 */
public class RefrigeratedTruck extends Truck {

	private int temperature;
	
	public RefrigeratedTruck(int cost, int capacity, Stock contents, int temperature) throws DeliveryException {
		super(cost, capacity, contents);
		this.temperature = temperature;
	}

}
