package delivery;

import exceptions.DeliveryException;
import objects.Stock;

/**
 * 
 * @author Tim
 */
public class OrdinaryTruck extends Truck {

	public OrdinaryTruck(int cost, int capacity, Stock contents) throws DeliveryException{
		super(cost, capacity, contents);
	}
	
}
