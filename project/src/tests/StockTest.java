package tests;

import static org.junit.Assert.*;
import org.junit.*;
import objects.*;
import exceptions.*;


/**
 * A series of tests to assess the capability of the Stock class.
 * As Stock is a collection of Items, the Item class must be complete to ItemTest specifications.
 * @author Tim
 */
public class StockTest {

	private final double DELTA = 0.01d;
	
	private Item apple;
	private Item banana;
	private Stock fruits;
	

	/**
	 * Constructs Stock object and Items to use before each test.
	 * Acts as a pseudo-test for the constructor of Stock objects.
	 */
	@Before
	public void setUpObjects() throws StockException{
		fruits = new Stock();
		apple = new Item("apple", 1, 2, 3, 4);
		banana = new Item("banana", 6, 7, 8, 9);
	}
	
	
	/**
	 * Modifying quantity of Item not in Stock adds Item to Stock.
	 */
	@Test
	public void addNewItem() throws StockException {
		fruits.modifyQuantity(apple, 5);

		assertEquals(5, fruits.getQuantity(apple));
	}
	
	
	/**
	 * Check whether Item exists in Stock.
	 */
	@Test
	public void itemPresent() throws StockException {
		fruits.modifyQuantity(apple, 5);
		
		assertTrue(fruits.itemInStock(apple));
	}
	
	
	/**
	 * Check whether Item does not exist in Stock.
	 */
	@Test
	public void itemNotPresent() throws StockException {
		fruits.modifyQuantity(apple, 5);
		
		assertFalse(fruits.itemInStock(banana));
	}
	
	
	/**
	 * Adds additional quantity to Item already in Stock.
	 */
	@Test
	public void addToExistingItem() throws StockException {
		fruits.modifyQuantity(apple, 5);
		fruits.modifyQuantity(apple, 5);
		
		assertEquals(10, fruits.getQuantity(apple));
	}
	
	
	/**
	 * Removes quantity from Item already in Stock.
	 */
	@Test 
	public void removeFromExistingItem() throws StockException {
		fruits.modifyQuantity(apple, 10);
		fruits.modifyQuantity(apple, -5);
		
		assertEquals(5, fruits.getQuantity(apple));
	}
	
	
	/**
	 * Removing an Item to zero quantity does not remove Item object from Stock.
	 */
	@Test
	public void removeToZero() throws StockException {
		fruits.modifyQuantity(apple, 5);
		fruits.modifyQuantity(apple, -5);
		
		assertEquals(0, fruits.getQuantity(apple));
	}
	
	
	/**
	 * Cannot remove quantity from Item not in Stock.
	 */
	@Test (expected = StockException.class)
	public void removeFromNonExistingItem() throws StockException {
		fruits.modifyQuantity(apple, 5);
		
		fruits.modifyQuantity(banana, -5);
	}
	
	
	/**
	 * Cannot reduce an Item's quantity past zero.
	 */
	@Test (expected = StockException.class)
	public void quantityToNegative() throws StockException {
		fruits.modifyQuantity(apple, 5);
		fruits.modifyQuantity(apple, -10);
	}
	
	
	/**
	 * Gets quantity of all Items in Stock.
	 */
	@Test
	public void getTotalQuantity() throws StockException {
		fruits.modifyQuantity(apple, 5);
		fruits.modifyQuantity(banana, 1);

		assertEquals(6, fruits.getTotalQuantity());
	}
	
	
	/**
	 * When no Items are in Stock, quantity should still exist.
	 */
	@Test
	public void getEmptyQuantity() throws StockException {
		assertEquals(0, fruits.getTotalQuantity());
	}
	
	
	/**
	 * Checks for presence of cold Items in Stock.
	 */
	@Test
	public void hasTempRequirement() throws StockException {
		banana = new Item("banana", 6, 7, 8, 9, 10);
		fruits.modifyQuantity(apple, 5);
		assertFalse(fruits.needsTempControl());
		fruits.modifyQuantity(banana, 5);
		assertTrue(fruits.needsTempControl());
	}
	
	

	/**
	 * Gets minimum required temperature of all Items in Stock.
	 */
	@Test
	public void getMinTemp() throws StockException {
		apple = new Item("apple", 1, 2, 3, 4, 5);
		banana = new Item("banana", 6, 7, 8, 9, 10);
		fruits.modifyQuantity(apple, 1);
		fruits.modifyQuantity(banana, 1);
		
		assertEquals(5, fruits.getMinTemp(), DELTA);
	}
	
	
	/**
	 * Cannot get minimum required temperature of purely dry Items.
	 */
	@Test (expected = StockException.class)
	public void getTempNotControlled() throws StockException {
		fruits.modifyQuantity(apple, 1);
		fruits.modifyQuantity(banana, 1);
		
		fruits.getMinTemp();
	}
	
	
	/**
	 * Iterates over Items in Stock to print names (Coldest -> Warmest, alphabetically)
	 */
	@Test
	public void iterateItemsColdestFirst() throws StockException {
		String fruitNames = "";
		//Two cold items, different temps
		apple = new Item("apple", 1, 2, 3, 4, 5);
		banana = new Item("banana", 6, 7, 8, 9, 10);
		//Two dry items
		Item carrot = new Item("carrot", 11, 12, 13, 14);
		Item durian = new Item("durian", 16, 17, 18, 19);		

		//Items added in different order to expected output
		fruits.modifyQuantity(banana, 1);
		fruits.modifyQuantity(durian, 1);
		fruits.modifyQuantity(carrot, 1);		
		fruits.modifyQuantity(apple, 1);

		for(Item fruitType : fruits) {
			fruitNames += fruitType.getName();
		}
		
		assertEquals("applebananacarrotdurian", fruitNames);
	}
	
	
	/**
	 * Removes desired number of Items from one Stock and places in another.
	 * Lowest temperature Items removed first.
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
		
		//fruitBasket should contain the 6 coldest Items from fruits
		//That is, 5 apples and 1 banana
		//fruits should contain 4 bananas
		assertEquals(6, fruitBasket.getTotalQuantity());
		assertEquals(4, fruits.getTotalQuantity());
		assertEquals(10, fruits.getMinTemp(), DELTA);
	}
	
}
