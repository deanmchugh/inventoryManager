package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import objects.*;
import exceptions.*;

/**
 * A series of tests to assess the capability of the Stock class.
 * As Stock is a collection of Items, the Item class must be complete.
 * @author Tim
 * @param <E>
 */
public class StockTest {

	private Item apple;
	private Item banana;
	private Stock<Item> fruits;
	

	/**
	 * Creates Stock object
	 * Creates two Items to use in tests
	 * Stock class depends on Item class, so Item class must be complete
	 * @throws StockException 
	 */
	@Test
	public void createStock() throws StockException {
		fruits = new Stock<Item>();
		apple = new Item("apple", 1, 1, 1, 1, -10);
		banana = new Item("banana", 1, 1, 1, 1, 10);
	}
	
	/**
	 * Modifying quantity of Item not in Stock adds Item to Stock
	 * @throws StockException 
	 */
	@Test
	public void addNewItem() throws StockException {
		apple = new Item("apple", 1, 1, 1, 1, 10);
		fruits = new Stock<Item>();
		fruits.add(apple, 5);
		assertEquals(5, fruits.getQuantity(apple));
	}
	
	/**
	 * Check whether Item exists in Stock
	 * @throws StockException 
	 */
	@Test
	public void itemPresent() throws StockException {
		fruits = new Stock<Item>();
		apple = new Item("apple", 1, 1, 1, 1, 10);
		fruits.add(apple, 5);
		assertTrue(fruits.itemInStock(apple));
	}
	
	/**
	 * Check whether Item does not exist in Stock
	 * @throws StockException 
	 */
	@Test
	public void itemNotPresent() throws StockException {
		fruits = new Stock<Item>();
		apple = new Item("apple", 1, 1, 1, 1, 10);
		fruits.add(apple, 5);
		assertFalse(fruits.itemInStock(banana));
	}
	
	/**
	 * Adds additional quantity to Item already in Stock
	 * @throws StockException 
	 */
	@Test
	public void addToExistingItem() throws StockException {
		fruits = new Stock<Item>();
		apple = new Item("apple", 1, 1, 1, 1, 10);
		fruits.add(apple, 5);
		fruits.add(apple, 5);
		assertEquals(10, fruits.getQuantity(apple));
	}
	
	/**
	 * Removes quantity from Item already in Stock
	 * @throws StockException 
	 */
	@Test 
	public void removeFromExistingItem() throws StockException {
		fruits = new Stock<Item>();
		apple = new Item("apple", 1, 1, 1, 1, 10);
		fruits.add(apple, 10);
		fruits.remove(apple, 5);
		assertEquals(5, fruits.getQuantity(apple));
	}
	
	/**
	 * Removing an Item to zero quantity does not remove Item object from Stock
	 * @throws StockException 
	 */
	@Test
	public void removeToZero() throws StockException {
		fruits = new Stock<Item>();
		apple = new Item("apple", 1, 1, 1, 1, 10);
		fruits.add(apple, 5);
		fruits.remove(apple, 5);
		assertEquals(0, fruits.getQuantity(apple));
	}
	/**
	 * Cannot remove quantity from Item not in Stock
	 * @throws StockException
	 */
	@Test (expected = StockException.class)
	public void removeFromNonExistingItem() throws StockException {
		fruits = new Stock<Item>();
		fruits.remove(banana, 5);
	}
	
	/**
	 * Cannot reduce an Item's quantity past zero
	 * @throws StockException
	 */
	@Test (expected = StockException.class)
	public void quantityToNegative() throws StockException {
		fruits = new Stock<Item>();
		apple = new Item("apple", 1, 1, 1, 1, 10);
		fruits.add(apple, 5);
		fruits.remove(apple, 10);
	}
	
	/**
	 * Gets quantity of all Items in Stock
	 * @throws StockException 
	 */
	@Test
	public void getTotalQuantity() throws StockException {
		fruits = new Stock<Item>();
		apple = new Item("apple", 1, 1, 1, 1, 10);
		fruits.add(apple, 5);
		fruits.add(banana, 1);
		assertEquals(6, fruits.getTotalQuantity());
	}
	
	/**
	 * When no Items are in Stock, quantity should still exist
	 */
	@Test
	public void getEmptyQuantity() {
		fruits = new Stock<Item>();
		assertEquals(0, fruits.getTotalQuantity());
	}
	
	/**
	 * Checks whether any Items in Stock are temperature controlled
	 * @throws StockException 
	 */
	@Test
	public void hasTempRequirement() throws StockException {
		fruits = new Stock<Item>();
		apple = new Item("apple", 1, 2, 3, 4, 5);
		fruits.add(apple, 10);
		assertTrue(fruits.needsTempControl());
	}
	
	/**
	 * Gets minimum required temperature of all Items in Stock
	 */
	@Test
	public void getMinTemp() {
		fruits = new Stock<Item>();
		apple = new Item("apple", 1, 1, 1, 1, -10);
		banana = new Item("banana", 1, 1, 1, 1, 5);
		fruits.add(apple, 1);
		fruits.add(banana, 1);
		
		assertEquals(-10, fruits.getMinTemp());
	}
	
	/**
	 * Cannot get minimum required temperature of purely non-controlled Items
	 */
	@Test
	public void getTempNotControlled() throws StockException(){
		fruits = new Stock<Item>();
		apple = new Item("apple", 1, 1, 1, 1, 0);
		banana = new Item("banana", 1, 1, 1, 1, 0);
		fruits.add(apple, 1);
		fruits.add(banana, 1);
		
		fruits.getMinTemp();
	}
	
	/**
	 * Removes desired number of Items from one Stock and places in another
	 * Lowest temperature Items removed first
	 */
	@Test
	public void takeSomeStock() {
		Stock<Item> fruitBasket;
		apple = new Item("apple", 1, 2, 3, 4, 5);
		banana = new Item("banana", 6, 7, 8, 9, 10);
		fruits = new Stock<Item>();
		
		fruits.add(apple, 5);
		fruits.add(banana, 5);
		
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
	 * @throws StockException 
	 */
	@Test
	public void iterateItems() throws StockException {
		String fruitNames = "";
		apple = new Item("apple", 1, 2, 3, 4, 5);
		banana = new Item("banana", 6, 7, 8, 9, 10);
		fruits = new Stock<Item>();
		fruits.add(apple, 5);
		fruits.add(banana, 5);
		for(Item fruit : fruits.toSet()) {
			fruitNames += fruit.getName();
		}
		assertEquals("applebanana", fruitNames);
	}
	
	/**
	 * Iterates over Items in Stock to print names
	 * Must iterate alphabetically
	 * @throws StockException 
	 */
	@Test
	public void iterateAlphabetically() throws StockException {
		String fruitNames = "";
		apple = new Item("apple", 1, 2, 3, 4, 5);
		banana = new Item("banana", 6, 7, 8, 9, 10);
		fruits = new Stock<Item>();
		fruits.add(banana, 5);
		fruits.add(apple, 5);
		
		for(Item fruit : fruits.toSet()) {
			fruitNames += fruit.getName();
		}	
		assertEquals("applebanana", fruitNames);
	}
	
}
