package tests;

import static org.junit.Assert.*;
import org.junit.*;

import exceptions.StockException;
import objects.*;

/**
 * A series of tests to assess the capability of the Store class.
 * Store contains a Stock object, which in turn is a collection of Items.
 * Item and Stock classes must be complete for these tests to be successful.
 * @author Tim
 */
public class StoreTest {

	private final double DELTA = 0.01d;
	private final static double APPLE_COST = 1.2;
	private final static double APPLE_SELLPRICE = 2.3;
	private final static int APPLE_REORDERPOINT = 3;
	private final static int APPLE_REORDERAMOUNT = 4;
	private final static double STARTING_BALANCE = 100000d;
	
	private static Store store;
	private static Item apple;
	private static Stock nextOrder;
	

	/**
	 * Sets up objects before any tests run.
	 */
	@BeforeClass
	public static void getStore() throws StockException {
		store = Store.getInstance();
		apple = new Item("apple", APPLE_COST, APPLE_SELLPRICE, APPLE_REORDERPOINT, APPLE_REORDERAMOUNT);
	}

	
	/**
	 * Calls method to reset store to starting values.
	 * Implicit test of reset() method, although this method should not be used
	 * except for testing. This is needed due to the singleton nature of the Store
	 * class and the unknown run order of JUnit tests.
	 */
	@Before
	public void resetStore(){
		store.reset();
		nextOrder = new Stock();
	}
	

	/**
	 * Gets starting capital of store.
	 */
	@Test
	public void startingCapital() {
		assertEquals(STARTING_BALANCE, store.getCapital(), DELTA);
	}
	
	
	/**
	 * Gives name to store.
	 */
	@Test
	public void nameStore() {
		store.setName("Market");
		assertEquals("Market", store.getName());
	}

	
	/**
	 * Checks inventory upon creation is an empty stock object.
	 */
	@Test
	public void startingInventory() {
		assertEquals(0, store.getInventory().getTotalQuantity());
	}
	

	/**
	 * Add Item to store.
	 */
	@Test
	public void addItem() throws StockException {
		store.addToInventory(apple, 10);
		assertEquals(10, store.getQuantity(apple));
	}



	/**
	 * Add extra quantity to existing Item.
	 */
	@Test
	public void increaseItemQuantity() throws StockException {
		store.addToInventory(apple, 10);
		store.addToInventory(apple, 15);
		assertEquals(25, store.getQuantity(apple));
	}	

	
	/**
	 * Cannot increase an Item's quantity by a negative value.
	 */
	@Test (expected = StockException.class)
	public void increaseQuantityByNegative() throws StockException {
		store.addToInventory(apple, -1);
	}


	/**
	 * Sells an item, quantity in inventory should be reduced.
	 */
	@Test
	public void sellItemReduceQauntity() throws StockException {
		//When there are 3 or fewer apples, 4 will be ordered
		store.addToInventory(apple, 10);
		store.sellItem(apple, 1);
		
		assertEquals(9, store.getQuantity(apple));
	}
	
	
	/**
	 * Attempts to sell more of an Item than Store contains.
	 */
	@Test (expected = StockException.class)
	public void sellTooManyItems() throws StockException {
		store.addToInventory(apple, 10);
		store.sellItem(apple, 11);
	}


	/**
	 * Attempts to sell negative amount of an Item.
	 */
	@Test (expected = StockException.class)
	public void sellNegativeQuantity() throws StockException {
		store.addToInventory(apple, 10);
		store.sellItem(apple, -1);
	}


	/**
	 * Cannot sell Item not in Store.
	 */
	@Test (expected = StockException.class)
	public void sellWrongItem() throws StockException {
		Item banana = new Item("banana", 6, 7, 8, 9);
		store.sellItem(banana, 1);
	}
	

	/**
	 * Sells an Item, capital should increase.
	 */
	@Test
	public void sellItemIncreaseCapital() throws StockException {
		store.addToInventory(apple, 10);
		store.sellItem(apple, 1);
		assertEquals(STARTING_BALANCE + APPLE_SELLPRICE, store.getCapital(), DELTA);
	}

	
	/**
	 *Sells an item to reorder point, Item should be added to next order.
	 */
	@Test
	public void sellBelowReorderPoint() throws StockException {
		store.addToInventory(apple, 10);
		store.sellItem(apple, 7);
		nextOrder.modifyQuantity(apple, 4);
		assertEquals(nextOrder.getTotalQuantity(), store.getOrderList().getTotalQuantity());
	}
	

	/**
	 * Given a dollar value, reduce capital by this amount.
	 */	
	@Test
	public void reduceCapital() throws StockException {
		store.reduceCapital(40000d);
		assertEquals(60000d, store.getCapital(), DELTA);
	}


	/**
	 * Cannot reduce capital past zero.
	 */	
	@Test (expected = StockException.class)
	public void reduceCapitalToNegative() throws StockException {
		store.reduceCapital(2 * STARTING_BALANCE);
	}

}