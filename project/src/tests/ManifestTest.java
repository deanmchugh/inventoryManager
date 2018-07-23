package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import delivery.Manifest;
import delivery.OrdinaryTruck;
import delivery.RefrigeratedTruck;
import delivery.Truck;
import exceptions.DeliveryException;
import exceptions.StockException;
import objects.Item;
import objects.Stock;

/**
 * Tests for the manifest constructor
 * @author Dean McHugh
 *
 */
public class ManifestTest {

	private Stock totalOrder;
	private Item apple;
	private Item orange;
	private Manifest manifest;
	private Truck truck;
	
	/**
	 * Helper method to create order list of temp controlled items.
	 * @return temp controlled item list.
	 * @throws StockException if items get reduced to below 0.
	 */
	public Stock createTempOrderList() throws StockException {
		apple = new Item("apple", 1, 2, 3, 4, 5);
		orange = new Item("orange", 1, 2, 3, 4, 5);
		totalOrder = new Stock();
		totalOrder.modifyQuantity(apple, 5);
		totalOrder.modifyQuantity(orange, 5);
		return totalOrder;
	}
	
	/**
	 * Helper method to create order list of non temp controlled items.
	 * @return non temp controlled item list.
	 * @throws StockException if items get reduced to below 0.
	 */
	public Stock createNonTempOrderList() throws StockException {
		apple = new Item("apple", 1, 2, 3, 4);
		orange = new Item("orange", 1, 2, 3, 4);
		totalOrder = new Stock();
		totalOrder.modifyQuantity(apple, 5);
		totalOrder.modifyQuantity(orange, 5);
		return totalOrder;
	}
	
	/**
	 * Helper method to create a mixed list of temp and non temp controlled items. 
	 * @return temp and non temp item list.
	 * @throws StockException if items get reduced to below 0.
	 */
	public Stock createMixedTempOrderList() throws StockException {
		apple = new Item("apple", 1, 2, 3, 4);
		orange = new Item("orange", 1, 2, 3, 4, 5);
		totalOrder = new Stock();
		totalOrder.modifyQuantity(apple, 5);
		totalOrder.modifyQuantity(orange, 5);
		return totalOrder;
	}
	/**
	 * Test to create a manifest without a order list.
	 */
	@Test
	public void createManifest() {
		manifest = new Manifest();
	}
	
	/**
	 * Test to create a manifest with a order list.
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative.
	 */
	@Test
	public void createManifestCargo() throws StockException, DeliveryException {
		totalOrder = createMixedTempOrderList();
		manifest = new Manifest(totalOrder);
	}
	
	/**
	 * Test to add ordinary truck to manifest.
	* @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative.
	 */
	@Test
	public void createManifestAddOrdinary() throws StockException, DeliveryException {
		totalOrder = createNonTempOrderList();
		truck = new OrdinaryTruck(totalOrder);
		manifest = new Manifest();
		manifest.addTruck(truck);
	}
	
	/**
	 * Test to add refrigerated truck to manifest.
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative.
	 */
	@Test
	public void createManifestAddRefrigerated() throws StockException, DeliveryException {
		totalOrder = createTempOrderList();
		truck = new RefrigeratedTruck(totalOrder);
		manifest = new Manifest();
		manifest.addTruck(truck);
	}
	
	/**
	 * Test to get total cost of ordinary truck manifest.
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative. 
	 */
	@Test 
	public void costOfManifestOrdinary() throws StockException, DeliveryException {
		totalOrder = createNonTempOrderList();
		manifest = new Manifest(totalOrder);
		assertEquals(762.5, manifest.getTotalCost(), 0.1);
	}
	
	/**
	 * Test to get total cost of refrigerated truck manifest.
	* @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative. 
	 */
	@Test 
	public void costOfManifestRefrigerated() throws StockException, DeliveryException {
		totalOrder = createTempOrderList();
		manifest = new Manifest(totalOrder);
		assertEquals(1050, manifest.getTotalCost(), 0.1);
	}
	
	/**
	 * Test to get total cost of mixed truck manifest.
	 * @throws StockException if items are below 0.
	 * @throws DeliveryException if truck list is negative. 
	 */
	@Test 
	public void costOfManifestMixed() throws StockException, DeliveryException {
		totalOrder = createMixedTempOrderList();
		manifest = new Manifest(totalOrder);
		assertEquals(1050, manifest.getTotalCost(), 0.1);
	}
}
