package delivery;

import exceptions.DeliveryException;
import objects.Stock;

/**
 * Class to create a RefridgeratedTruck object that contains Stock at a specified temperature at a cost.
 * @author Tim
 */
public class RefrigeratedTruck extends Truck {

	private static final int MIN_TEMP = -20;
	private static final int MAX_TEMP = 10;
	private static final int CAPACITY = 800;
	
	private double cost;
	private int temperature;
	

	/**
	 * Constructor for Refrigerated Truck. Stores contents at a set temperature, and calculates cost to use Truck.
	 * @param contents Stock object to represent the contents of the Truck.
	 * @param temperature Set temperature of Truck, -20 <=temperature <= 10
	 * @throws DeliveryException Throws an exception when contents is null or temperature is out of bounds.
	 */
	public RefrigeratedTruck(Stock contents, int temperature) throws DeliveryException {
		super(contents);
		if (temperature < MIN_TEMP || temperature > MAX_TEMP) {
			throw new DeliveryException("Temperature not within bounds!");
		}
		this.temperature = temperature;
		
		//Calculates cost as a function of temperature, and rounds to the nearest cent.
		this.cost = 900.0 + 200.0 * Math.pow(0.7, temperature / 5.0);
		this.cost = (double)Math.round(this.cost * 100d)/100d;
	}

	
	/**
	 * Getter method for set temperature of Truck.
	 * @return Integer temperature of Truck.
	 */
	public int getTemp() {
		return this.temperature;
	}


	/**
	 * {@inheritDoc}
	 */
	public double getCost() {
		return this.cost;
	}


	/**
	 * {@inheritDoc}
	 */
	public int getCargoCap() {
		return CAPACITY;
	}

	
}
