package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import delivery.OrdinaryTruck;
import delivery.RefrigeratedTruck;
import delivery.Truck;
import objects.Item;
import objects.Stock;
import exceptions.DeliveryException;
import exceptions.StockException;

/**
 * A series of tests for the truck class
 * @author Dean McHugh
 *
 */
public class TruckTest {

	private Truck truck;
	private Stock contents = new Stock();
	private Item apple;
	private Item orange;
	
	/**
	 * Constructs a ordinary truck without temp controlled item. 
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative. 
	 */
	@Test 
	public void constructOrdinaryTruck() throws DeliveryException, StockException {
		apple = new Item("apple", 1, 2, 3, 4);
		contents.modifyQuantity(apple, 5);
		truck = new OrdinaryTruck(contents);
	}
	
	/**
	 * should not be able to construct ordinary truck with temp controlled item.
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative. 
	 */
	@Test (expected = DeliveryException.class)
	public void constructTempOrdinaryTruck() throws DeliveryException, StockException {
		orange = new Item("orange", 1, 2, 3, 4, 5);
		contents.modifyQuantity(orange, 5);
		truck = new OrdinaryTruck(contents);
	}
	
	/**
	 * construct Refrigerated truck with temp controlled item.
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative. 
	 */
	@Test
	public void constructTempRefrigeratedTruck() throws DeliveryException, StockException {
		orange = new Item("orange", 1, 2, 3, 4, 5);
		contents.modifyQuantity(orange, 5);
		truck = new RefrigeratedTruck(contents);
	}
	
	/**
	 * should not be able to construct refrigerated truck with non temp controlled item. 
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative.
	 */
	@Test (expected = DeliveryException.class)
	public void constuctNonTempRefrigeratedTruck() throws DeliveryException, StockException {
		apple = new Item("apple", 1, 2, 3, 4);
		contents.modifyQuantity(apple, 5);
		truck = new RefrigeratedTruck(contents);
	}
	
	/**
	 * test cost getter function if refrigerated truck.
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative. 
	 */
	@Test
	public void testGetCostOrdinary() throws DeliveryException, StockException {
		apple = new Item("apple", 1, 2, 3, 4);
		contents.modifyQuantity(apple, 4);
		truck = new OrdinaryTruck(contents);
		assertEquals(751.0, truck.getCost(), 0.1);
	}
	
	/**
	 * test cost getter function if ordinary truck.
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative. 
	 */
	@Test
	public void testGetCostRefrigerated() throws DeliveryException, StockException {
		orange = new Item("orange", 1, 2, 3, 4, 5);
		contents.modifyQuantity(orange, 4);
		truck = new RefrigeratedTruck(contents);
		assertEquals(1040.0, truck.getCost(), 0.1);
	}
	
	/**
	 * test cargo cap getter function.
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative.
	 */
	@Test
	public void testGetCargoCapOrdinary() throws DeliveryException, StockException {
		truck = new OrdinaryTruck(contents);
		assertEquals(1000, truck.getCargoCap());
	}
	
	/**
	 * test cargo cap getter function.
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative. 
	 */
	@Test
	public void testGetCargoCapRefrigerated() throws DeliveryException, StockException {
		orange = new Item("orange", 1, 2, 3, 4, 5);
		contents.modifyQuantity(orange, 4);
		truck = new RefrigeratedTruck(contents);
		assertEquals(800, truck.getCargoCap());
	}
	
	/**
	 * test cargo getter function ordinary.
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative. 
	 */
	@Test
	public void testGetCargoOrdinary() throws DeliveryException, StockException {
		apple = new Item("apple", 1, 2, 3, 4);
		contents.modifyQuantity(apple, 4);
		truck = new OrdinaryTruck(contents);
		assertEquals(contents, truck.getCargo());
	}
	
	/**
	 * test cargo getter function refrigerated. 
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative. 
	 */
	@Test
	public void testGetCargoRefrigerated() throws DeliveryException, StockException {
		orange = new Item("orange", 1, 2, 3, 4, 5);
		contents.modifyQuantity(orange, 4);
		truck = new RefrigeratedTruck(contents);
		assertEquals(contents, truck.getCargo());
	}
	
	/**
	 * test temp getter. 
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative. 
	 */
	@Test
	public void testGetTemp() throws DeliveryException, StockException {
		orange = new Item("orange", 1, 2, 3, 4, 5);
		contents.modifyQuantity(orange, 4);
		truck = new RefrigeratedTruck(contents);
		assertEquals(5.0, truck.getTemp(), 0.1);
	}
	
	/**
	 * test type getter function if refrigerated truck.
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative. 
	 */
	@Test
	public void testGetTypeOrdinary() throws DeliveryException, StockException {
		apple = new Item("apple", 1, 2, 3, 4);
		contents.modifyQuantity(apple, 4);
		truck = new OrdinaryTruck(contents);
		assertEquals("Ordinary", truck.getType());
	}
	
	/**
	 * test type getter function if ordinary truck.
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative.
	 */
	@Test
	public void testGetTypeRefrigerated() throws DeliveryException, StockException {
		orange = new Item("orange", 1, 2, 3, 4, 5);
		contents.modifyQuantity(orange, 4);
		truck = new RefrigeratedTruck(contents);
		assertEquals("Refrigerated", truck.getType());
	}
}
