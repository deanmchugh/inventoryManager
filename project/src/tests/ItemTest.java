package tests;

import static org.junit.Assert.*;
import org.junit.*;

import exceptions.StockException;
import objects.Item;

/**
 * A series of tests to assess the capability of the Item class.
 * The Item class must have two constructors, one for dry items, and one for temperature-controlled items.
 * @author Tim
 */
public class ItemTest {
	
	private final double DELTA = 0.01d;
	
	private Item apple;
	private Item banana;	

	
	/**
	 *Creates both a dry and a temperature-controlled Item for use
	 * in tests. Acts as a pseudo-test for Item constructors.
	 */
	@Before
	public void createDryItem() throws StockException{
		apple = new Item("apple", 1, 2, 3, 4);
		banana = new Item("banana", 6, 7, 8, 9, 10);
	}
	

	/**
	 * Getting purchase cost of Item
	 */
	@Test
	public void getCost() throws StockException {
		assertEquals(1, apple.getCost(), DELTA);
	}
	

	/**
	 * Can't construct Item with zero cost
	 */
	@Test (expected = StockException.class)
	public void ZeroCost() throws StockException {
		apple = new Item("apple", 0, 2, 3, 4);
	}
	

	/**
	 * Can't construct Item with negative cost
	 */
	@Test (expected = StockException.class)
	public void NegativeCost() throws StockException {
		apple = new Item("apple", -1, 2, 3, 4);
	}
	

	/**
	 * Can't construct Item with zero sell price
	 */
	@Test (expected = StockException.class)
	public void ZeroSellPrice() throws StockException {
		apple = new Item("apple", 1, 0, 3, 4);
	}
	

	/**
	 * Can't construct Item with negative sell price
	 */
	@Test (expected = StockException.class)
	public void NegativeSellPrice() throws StockException {
		apple = new Item("apple", 1, -2, 3, 4);
	}
	

	/**
	 * Can't construct Item with negative re-order point
	 */
	@Test (expected = StockException.class)
	public void NegativeReorderPoint() throws StockException {
		apple = new Item("apple", 1, 2, -3, 4);
	}
	

	/**
	 * Can't construct Item with zero re-order amount
	 */
	@Test (expected = StockException.class)
	public void ZeroReorderAmount() throws StockException {
		apple = new Item("apple", 1, 2, 3, 0);
	}
	

	/**
	 * Can't construct Item with negative re-order amount
	 */
	@Test (expected = StockException.class)
	public void NegativeReorderAmount() throws StockException {
		apple = new Item("apple", 1, 2, 3, -4);
	}
	
	
	/**
	 * Getting name
	 */
	@Test
	public void getName() throws StockException {
		assertEquals("apple", apple.getName());
	}
	
	/**
	 * Getting sell price
	 */
	@Test
	public void getPrice() throws StockException {
		assertEquals(2, apple.getSellPrice(), DELTA);
	}
	
	/**
	 * Getting re-order point
	 */
	@Test
	public void getReorderPoint() throws StockException {
		assertEquals(3, apple.getReorderPoint());
	}
	
	/**
	 * Getting re-order amount
	 */
	@Test
	public void getReorderAmount() throws StockException {
		assertEquals(4, apple.getReorderAmount());
	}
	

	/**
	 * Checking whether Item is temperature controlled
	 */
	@Test
	public void testTemperatureDry() throws StockException {
		assertFalse(apple.isTempControlled());
	}
	

	/**
	 * Checking whether Item is temperature controlled
	 */
	@Test
	public void testTemperatureCold() throws StockException {
		assertTrue(banana.isTempControlled());
	}


	/**
	 * Getting maximum temperature
	 */
	@Test
	public void getTemp() throws StockException {
		assertEquals(10, banana.getTemp(), DELTA);
	}
	

	/**
	 * Can't get maximum temperature of a dry item
	 */
	@Test (expected = StockException.class)
	public void noTemp() throws StockException {
		apple.getTemp();
	}
	
	
	/**
	 * Compares dry items, should compare by name alphabetically
	 */
	@Test
	public void compareItemsBothDry() throws StockException{
		banana = new Item("banana", 6, 7, 8, 9);

		assertTrue(apple.compareTo(banana) < 0);
		assertTrue(banana.compareTo(apple) > 0);	
	}


	/**
	 * Compares dry items with identical names
	 */
	@Test
	public void compareItemsSameNameBothDry() throws StockException{
		banana = new Item("apple", 6, 7, 8, 9);

		assertTrue(apple.compareTo(banana) == 0);	
	}



	/**
	 * Compares same temperature items with identical names
	 */
	@Test
	public void compareItemsSameNameSameTemp() throws StockException{
		apple = new Item("banana", 1, 2, 3, 4, 10);

		assertTrue(apple.compareTo(banana) == 0);	
	}


	/**
	 * Compares dry item with temp controlled item, temp controlled item should come first
	 */
	@Test
	public void compareItemsOneDryOneCold(){
		assertTrue(apple.compareTo(banana) > 0);
		assertTrue(banana.compareTo(apple) < 0);	
	}


	/**
	 * Compares cold items with different temperatures
	 */
	@Test
	public void compareItemsBothCold() throws StockException{
		apple = new Item("apple", 1, 2, 3, 4, 5);

		assertTrue(apple.compareTo(banana) < 0);
		assertTrue(banana.compareTo(apple) > 0);	
	}

}
