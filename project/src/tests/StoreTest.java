package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import exceptions.StockException;
import objects.*;

/**
 * A series of tests to assess the capability of the Store class.
 * Store contains a Stock object, which in turn is a collection of Items.
 * Item and Stock classes must be complete for these tests to be successful.
 * @author Plenco
 */
public class StoreTest {

	private Store store;
	private Item apple;
	private Stock expectedOrderList;
	
	/**
	 * Creates singleton Store from global access point
	 */
	@Test
	public void constructStore() {
		store = Store.getInstance();
	}
	
	/**
	 * Gets starting capital of store (Dollars portion)
	 */
	@Test
	public void startingCapital() {
		assertEquals(100000, store.getCapitalDollars());
	}
	
	/**
	 * Gets starting capital of store (Cents portion)
	 */
	@Test 
	public void startingCents() {
		assertEquals(0, store.getCapitalCents());
	}
	
	/**
	 * Gives name to store
	 */
	@Test
	public void nameStore() {
		store.setName("Market");
		assertEquals("Market", store.getname());
	}
	
	/**
	 * Checks inventory upon creation is an empty stock object
	 */
	@Test
	public void startingInventory() throws StockException {
		assertEquals(new Stock(), store.getInventory());
	}
	
	/**
	 * Sells an item, quantity in inventory should be reduced
	 */
	@Test
	public void sellItemReduceQauntity() throws StockException {
		apple = new Item("apple", 1, 2, 3, 4); //When there are 3 or fewer apples, 4 will be ordered
		store.addToInventory(apple, 5); //Brings apple quantity to 5
		store.sellItem(apple, 1);		//Brings apple quantiy to 4
		
		assertEquals(4, store.getQuantity(apple));
	}
	
	/**
	 * Item is sold in previous test, capital should increase
	 */
	@Test
	public void sellItemIncreaseCapital() {
		assertEquals(100002, store.getCapitalDollars());
		assertEquals(0, store.getCapitalCents());
	}
	
	/**
	 * Order an item by exporting to manifest
	 * Neither capital nor inventory should change
	 */
	@Test
	public void orderItem() throws StockException {
		store.orderItem(apple, 6);
		assertEquals(100002, store.getCapitalDollars());
		assertEquals(0, store.getCapitalCents());
		assertEquals(4, store.getQuantity(apple));
	}
	
	/**
	 * Items ordered must be added automatically to order list
	 */
	@Test
	public void getStockOrder() throws StockException {
		expectedOrderList = new Stock();
		expectedOrderList.modifyQuantity(apple, 6);
		assertEquals(expectedOrderList, store.getOrderList());
	}
	
	/**
	 * Sell item to below re-order point
	 */
	@Test
	public void automaticOrder() throws StockException {
		store.sellItem(apple, 1); //Brings apple quantity to 3
		expectedOrderList.modifyQuantity(apple, 4); //4 additional apples will be ordered
		assertEquals(expectedOrderList, store.getOrderList());
	}
	
}
