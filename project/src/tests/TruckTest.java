package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import delivery.OrdinaryTruck;
import delivery.RefrigeratedTruck;
import delivery.Truck;
import objects.Stock;
import exceptions.DeliveryException;

/**
 * A series of tests for the truck class
 * @author Dean McHugh
 *
 */
public class TruckTest {

	private Truck truck;
	private Stock stock = new Stock();
	
	/**
	 * Constructs a ordinary truck 
	 * @throws DeliveryException 
	 */
	@Test 
	public void constructOrdinaryTruck() throws DeliveryException {
		truck = new OrdinaryTruck(100, 100, stock);
	}
	
	/**
	 * constructs refridgerated truck 
	 * @throws DeliveryException 
	 */
	@Test
	public void constructRefrigeratedTruck() throws DeliveryException {
		truck = new RefrigeratedTruck(100, 100, stock, 10);
	}
	
	/**
	 * cant construct truck with negative cost
	 * @throws DeliveryException 
	 */
	@Test (expected = DeliveryException.class)
	public void negativeCost() throws DeliveryException {
		truck = new RefrigeratedTruck(-100, 100, stock, 10);
	}
	
	/**
	 * cant construct truck with negative stock cap
	 * @throws DeliveryException 
	 */
	@Test (expected = DeliveryException.class)
	public void negativeCargoCap() throws DeliveryException {
		truck = new RefrigeratedTruck(100, -100, stock, 10);
	}
	
	/**
	 * cant construct a truck without cargo 
	 * @throws DeliveryException 
	 */
	@Test (expected = DeliveryException.class)
	public void noCargo() throws DeliveryException {
		truck = new RefrigeratedTruck(100, 100, null, 10);
	}
	
	/**
	 * cant construct refridgerated truck with negative temp 
	 * @throws DeliveryException 
	 */
	@Test (expected = DeliveryException.class)
	public void negativeTemp() throws DeliveryException {
		truck = new RefrigeratedTruck(100, 100, stock, -10);
	}
	
	/**
	 * test cost getter function
	 * @throws DeliveryException 
	 */
	@Test
	public void testGetCost() throws DeliveryException {
		truck = new RefrigeratedTruck(100, 100, stock, 10);
		assertEquals(100, truck.getCost());
	}
	
	/**
	 * test cargo cap getter function
	 * @throws DeliveryException 
	 */
	@Test
	public void testGetCargoCap() throws DeliveryException {
		truck = new RefrigeratedTruck(100, 100, stock, 10);
		assertEquals(100, truck.getCargoCap());
	}
	
	/**
	 * test cargo getter function 
	 * @throws DeliveryException 
	 */
	@Test
	public void testGetCargo() throws DeliveryException {
		truck = new RefrigeratedTruck(100, 100, stock, 10);
		assertEquals(stock, truck.getCargo());
	}
	
	/**
	 * test temp getter 
	 * @throws DeliveryException 
	 */
	@Test
	public void testGetTemp() throws DeliveryException {
		truck = new RefrigeratedTruck(100, 100, stock, 10);
		assertEquals(10, truck.getTemp());
	}
}
