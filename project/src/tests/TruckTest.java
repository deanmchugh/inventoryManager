package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import delivery.RefridgeratedTruck;

public class TruckTest {

	private Truck truck;
	
	@Test 
	public void constructOrdinaryTruck() {
		truck = new OrdinaryTruck(100, 100, "apple");
	}
	
	@Test
	public void constructRefrigeratedTruck() {
		truck = new RefrigeratedTruck(100, 100, "apple", 10);
	}
	
	@Test
	public void testRefrigeratedGetCost() {
		assertEquals(100, truck.getCost());
	}
	
	@Test
	public void testRefrigeratedGetCargoCap() {
		assertEquals(100, truck.getCargoCap());
	}
	
	@Test
	public void testGetCargo() {
		assertEquals("apple", truck.getCargo());
	}
	
	@Test
	public void testGetTemp() {
		assertEquals(10, truck.getTemp());
	}
}
