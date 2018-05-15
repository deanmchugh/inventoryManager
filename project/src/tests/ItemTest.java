package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import objects.Item;
import exceptions.*;

public class ItemTest {
	
	private Item apple;
	
	/**
	 * Construction of item with no temperature control
	 */
	@Test
	public void constructItem() {
		apple = new Item("apple", 1, 2, 3, 4);
	}
	
	/**
	 * Construction of item with temperature control
	 */
	@Test
	public void constructControlledItem() {
		apple = new Item("apple", 1, 2, 3, 4, 5);
	}
	
	/**
	 * Getting purchase cost of Item
	 */
	@Test
	public void getCost() {
		assertEquals(1, apple.getCost());
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
	 * Getting sell price
	 */
	@Test
	public void getPrice() {
		apple = new Item("apple", 1, 2, 3, 4);
		assertEquals(2, apple.getSellPrice());
	}
	
	/**
	 * Getting re-order point
	 */
	@Test
	public void getReorderPoint() {
		assertEquals(3, apple.getReorderPoint());
	}
	
	/**
	 * Getting re-order amount
	 */
	@Test
	public void getReorderAmount() {
		assertEquals(4, apple.getReorderAmount());
	}
	
	/**
	 * Checking whether Item is temperature controlled
	 */
	@Test
	public void testTemperature() {
		assertFalse(apple.isTempControlled());
	}
	
	/**
	 * Getting maximum temperature
	 */
	@Test
	public void getTemp() {
		apple = new Item("apple", 1, 2, 3, 4, 5);
		assertEquals(5, apple.getTemp());
	}
	
	/**
	 * Can't get maximum temperature of a dry item
	 */
	@Test (expected = stockException.class)
	public void noTemp() throws StockException {
		apple = new Item("apple", 1, 2, 3, 4);
		assertEquals(11, apple.getTemp());
	}
	
}
