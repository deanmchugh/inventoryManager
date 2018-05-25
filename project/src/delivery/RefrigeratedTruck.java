package delivery;

import exceptions.DeliveryException;
import exceptions.StockException;
import objects.Stock;

/**
 * Class to create a RefrigeratedTruck object that contains Stock at a specified temperature at a cost.
 * This class extends the abstract class Truck.
 * @author Tim
 */
public class RefrigeratedTruck extends Truck {

	private static final int MIN_TEMP = -20;
	private static final int MAX_TEMP = 10;
	private static final int CAPACITY = 800;
	private static final String TYPE = "Refrigerated";
	
	private double cost;
	private double temperature;
	

	/**
	 * Constructor for RefrigeratedTruck. Stores contents at a set temperature, and calculates cost to use Truck.
	 * @param contents Stock object to represent the contents of the Truck.
	 * @throws DeliveryException Throws an exception when contents is null, temperature is out of bounds, or use of a RefrigeratedTruck is not needed.
	 * @throws StockException Re-throws exceptions thrown by methods used within.
	 */
	public RefrigeratedTruck(Stock contents) throws DeliveryException, StockException {
		super(contents);
		
		//Find minimum temperature needed of Items in contents, throws exception if temperature out of bounds or if no Items are temp controlled.
		if (!contents.needsTempControl()) {
			throw new DeliveryException("Using a refrigerated truck for non-controlled items!");
		}
		temperature = contents.getMinTemp();
		if (temperature < MIN_TEMP || temperature > MAX_TEMP) {
			throw new DeliveryException("Temperature not within bounds!");
		}
		
		//Calculates cost as a function of temperature, no rounding occurs.
		cost = 900d + 200d * Math.pow(0.7, temperature / 5d);
	}

	
	/**
	 * Getter method for set temperature of Truck.
	 * @return Integer temperature of Truck.
	 */
	public double getTemp() {
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


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getType() {
		return TYPE;
	}

	
}
