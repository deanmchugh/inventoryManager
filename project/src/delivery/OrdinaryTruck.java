package delivery;

import exceptions.DeliveryException;
import objects.Stock;

/**
 * Class to create an OrdinaryTruck object that contains Stock at no specific temperature at a cost.
 * @author Tim
 */
public class OrdinaryTruck extends Truck {

	private static final int CAPACITY = 1000;
	
	private double cost;
	

	/**
	 * Constructor for Ordinary Truck. Stores contents at a set temperature, and calculates cost to use Truck.
	 * @param contents Stock object to represent the contents of the Truck.
	 * @throws DeliveryException Throws an exception when contents is null or contents contain temperature-controlled items.
	 */
	public OrdinaryTruck(Stock contents) throws DeliveryException{
		super(contents);
		if (contents.needsTempControl()) {
			throw new DeliveryException("Cannot store temperature controlled items in an ordinary truck!");
		}
		
		//Calculates cost as a function of quantity, and rounds to nearest cent.
		this.cost = (750 + 0.25 * contents.getTotalQuantity());
		this.cost = (double)Math.round(this.cost * 100d)/100d;
	}


	/**
	 * Getter method for temperature of Truck. As OrdinaryTrucks do not have set temperatures, 
	 * an exception will always be thrown.
	 * @return Will never return a value.
	 * @throws DeliveryException always.
	 */
	public int getTemp() throws DeliveryException {
		throw new DeliveryException("Ordinary trucks do not have a set temperature!");
	}


	/**
	 * {@inheritDoc}
	 */
	public double getCost() {
		return cost;
	}

	
	/**
	 * {@inheritDoc}
	 */
	public int getCargoCap() {
		return CAPACITY;
	}

}
