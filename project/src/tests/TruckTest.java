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
	private Stock stock = null;
	
	/**
	 * Constructs a ordinary truck 
	 */
	@Test 
	public void constructOrdinaryTruck() {
		truck = new OrdinaryTruck(100, 100, stock);
	}
	
	/**
	 * constructs refridgerated truck 
	 */
	@Test
	public void constructRefrigeratedTruck() {
		truck = new RefridgeratedTruck(100, 100, stock, 10);
	}
	
	/**
	 * cant construct truck with negative cost
	 */
	@Test (expected = DeliveryException.class)
	public void negativeCost() {
		truck = new RefridgeratedTruck(-100, 100, stock, 10);
	}
	
	/**
	 * cant construct truck with negative stock cap
	 */
	@Test (expected = DeliveryException.class)
	public void negativeCargoCap() {
		truck = new RefridgeratedTruck(100, -100, stock, 10);
	}
	
	/**
	 * cant construct a truck without cargo 
	 */
	@Test (expected = DeliveryException.class)
	public void noCargo() {
		truck = new RefridgeratedTruck(100, 100, null, 10);
	}
	
	/**
	 * cant construct refridgerated truck with negative temp 
	 */
	@Test (expected = DeliveryException.class)
	public void negativeTemp() {
		truck = new RefridgeratedTruck(100, 100, stock, -10);
	}
	
	/**
	 * test cost getter function
	 */
	@Test
	public void testGetCost() {
		truck = new RefridgeratedTruck(100, 100, stock, 10);
		assertEquals(100, truck.getCost());
	}
	
	/**
	 * test cargo cap getter function
	 */
	@Test
	public void testGetCargoCap() {
		truck = new RefridgeratedTruck(100, 100, stock, 10);
		assertEquals(100, truck.getCargoCap());
	}
	
	/**
	 * test cargo getter function 
	 */
	@Test
	public void testGetCargo() {
		truck = new RefridgeratedTruck(100, 100, stock, 10);
		assertEquals(stock, truck.getCargo());
	}
	
	/**
	 * test temp getter 
	 */
	@Test
	public void testGetTemp() {
		truck = new RefridgeratedTruck(100, 100, stock, 10);
		assertEquals(10, truck.getTemp());
	}
}
