package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import objects.*;
import exceptions.*;

/**
 * A series of tests to assess the capability of the Stock class.
 * As Stock is a collection of Items, the Item class must be complete.
 * @author Tim
 */
public class StockTest {

	private Item apple;
	private Item banana;
	private Stock fruits;
	

	/**
	 * Creates Stock object
	 * Creates two Items to use in tests
	 * Stock class depends on Item class, so Item class must be complete
	 */
	@Test
	public void createStock() throws StockException {
		fruits = new Stock();
		apple = new Item("apple", 1, 1, 1, 1, -10);
		banana = new Item("banana", 1, 1, 1, 1, 10);
	}
	
	/**
	 * Modifying quantity of Item not in Stock adds Item to Stock
	 */
	@Test
	public void addNewItem() throws StockException {
		fruits.modifyQuantity(apple, 5);
		assertEquals(5, fruits.getQuantity(apple));
	}
	
	/**
	 * Check whether Item exists in Stock
	 */
	@Test
	public void itemPresent() throws StockException {
		assertTrue(fruits.itemInStock(apple));
	}
	
	/**
	 * Check whether Item does not exist in Stock
	 */
	@Test
	public void itemNotPresent() throws StockException {
		assertFalse(fruits.itemInStock(banana));
	}
	
	/**
	 * Adds additional quantity to Item already in Stock
	 */
	@Test
	public void addToExistingItem() throws StockException {
		fruits.modifyQuantity(apple, 5);
		assertEquals(10, fruits.getQuantity(apple));
	}
	
	/**
	 * Removes quantity from Item already in Stock
	 */
	@Test 
	public void removeFromExistingItem() throws StockException {
		fruits.modifyQuantity(apple, -5);
		assertEquals(5, fruits.getQuantity(apple));
	}
	
	/**
	 * Removing an Item to zero quantity does not remove Item object from Stock
	 */
	@Test
	public void removeToZero() throws StockException {
		fruits.modifyQuantity(apple, -5);
		assertEquals(0, fruits.getQuantity(apple));
	}
	/**
	 * Cannot remove quantity from Item not in Stock
	 * @throws StockException
	 */
	@Test (expected = StockException.class)
	public void removeFromNonExistingItem() throws StockException {
		fruits.modifyQuantity(banana, -5);
	}
	
	/**
	 * Cannot reduce an Item's quantity past zero
	 * @throws StockException
	 */
	@Test (expected = StockException.class)
	public void quantityToNegative() throws StockException {
		//Attempts to bring apple quantity from 5 to -5
		fruits.modifyQuantity(apple, -10);
	}
	
	/**
	 * Gets quantity of all Items in Stock
	 */
	@Test
	public void getTotalQuantity() throws StockException {
		// Adds banana to fruits with a quantity of 1
		fruits.modifyQuantity(banana, 1);
		// Apple quantity should previously be 5
		assertEquals(6, fruits.getTotalQuantity());
	}
	
	/**
	 * When no Items are in Stock, quantity should still exist
	 */
	@Test
	public void getEmptyQuantity() throws StockException {
		fruits = new Stock();
		assertEquals(0, fruits.getTotalQuantity());
	}
	
	/**
	 * Checks whether any Items in Stock are temperature controlled
	 */
	@Test
	public void hasTempRequirement() throws StockException {
		apple = new Item("apple", 1, 2, 3, 4, 5);
		fruits.modifyQuantity(apple, 10);
		assertTrue(fruits.needsTempControl());
	}
	
	/**
	 * Gets minimum required temperature of all Items in Stock
	 */
	@Test
	public void getMinTemp() throws StockException {
		apple = new Item("apple", 1, 1, 1, 1, -10);
		banana = new Item("banana", 1, 1, 1, 1, 5);
		fruits = new Stock();
		fruits.modifyQuantity(apple, 1);
		fruits.modifyQuantity(banana, 1);
		
		assertEquals(-10, fruits.getMinTemp());
	}
	
	/**
	 * Cannot get minimum required temperature of purely non-controlled Items
	 */
	@Test (expected = StockException.class)
	public void getTempNotControlled() throws StockException {
		apple = new Item("apple", 1, 1, 1, 1);
		banana = new Item("banana", 1, 1, 1, 1);
		fruits = new Stock();
		fruits.modifyQuantity(apple, 1);
		fruits.modifyQuantity(banana, 1);
		
		fruits.getMinTemp();
	}
	
	/**
	 * Removes desired number of Items from one Stock and places in another
	 * Lowest temperature Items removed first
	 */
	@Test
	public void takeSomeStock() throws StockException {
		Stock fruitBasket;
		apple = new Item("apple", 1, 2, 3, 4, 5);
		banana = new Item("banana", 6, 7, 8, 9, 10);
		fruits = new Stock();
		
		fruits.modifyQuantity(apple, 5);
		fruits.modifyQuantity(banana, 5);
		
		fruitBasket = fruits.take(6);
		
		//fruitbasket should contain the 6 coldest Items from fruits
		//That is, 5 apples and 1 banana
		//fruits should contain 4 bananas
		assertEquals(6, fruitBasket.getTotalQuantity());
		assertEquals(4, fruits.getTotalQuantity());
		assertEquals(10, fruits.getMinTemp());
	}
	
	/**
	 * Iterates over Items in Stock to print names
	 */
	@Test
	public void iterateItems() throws StockException {
		String fruitNames = "";
		
		apple = new Item("apple", 1, 2, 3, 4, 5);
		banana = new Item("banana", 6, 7, 8, 9, 10);
		fruits = new Stock();
		fruits.modifyQuantity(apple, 5);
		fruits.modifyQuantity(banana, 5);
		
		for(Item fruitType : fruits) {
			fruitNames += fruitType.getName();
		}
		
		assertEquals("applebanana", fruitNames);
	}
	
	/**
	 * Iterates over Items in Stock to print names
	 * Must iterate alphabetically
	 */
	@Test
	public void iterateAlphabetically() throws StockException {
		String fruitNames = "";
		
		apple = new Item("apple", 1, 2, 3, 4, 5);
		banana = new Item("banana", 6, 7, 8, 9, 10);
		fruits = new Stock();
		fruits.modifyQuantity(banana, 5);
		fruits.modifyQuantity(apple, 5);
		
		for(Item fruitType : fruits) {
			fruitNames += fruitType.getName();
		}
		
		assertEquals("applebanana", fruitNames);
	}
	
}
