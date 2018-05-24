package objects;

import exceptions.StockException;

/**
 * Class that creates an instance of a store 
 * @author Dean McHugh
 *
 */
public class Store {

	private static double capital = 100000.00;
	private static String name;
	private static Stock<Item> inventory;
	private Stock<Item> reorderList;

	private static Store myObj;
	
	/**
	 * Private store constructor that will only create one version of it at a time
	 * @param capital
	 * @param name
	 * @param inventory
	 */
	private Store(double capital, String name, Stock<Item> inventory) {
		Store.capital = capital;
		Store.name = name;
		Store.inventory = inventory; 
	}	
	
	public static Store getInstance() {
		if(myObj == null) {
			myObj = new Store(capital, name, inventory);
		}
		return myObj;
	}
	
	/**
	 * function to get the current capital dollars of the store
	 * @return dollar capital of store
	 */
	public int getCapitalDollars() {
		int dollars = (int) capital;
		return dollars;
	}
	
	/**
	 * function to get the current capital cents of the store
	 * @return cents capital of store
	 */
	public int getCapitalCents() {
		int cents = (int) (capital - getCapitalDollars());
		return cents;
	}
	
	/**
	 * function to set the name of the current store 
	 * @param name
	 */
	public void setName(String name) {
		Store.name = name;
	}
	
	/**
	 * function to get the current name of the store
	 * @return the current store name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * function to add items to the store inventory 
	 * and reduce current store capital
	 * @param item
	 * @param amount
	 * @throws StockException
	 */
	public void addToInventory(Item item, int amount) throws StockException {
		Store.inventory.add(item, amount);
		Store.capital -= item.getCost();
	}
	
	/**
	 * function to remove items from the store inventory
	 * then reduce current store capital and reorder items
	 * @param item
	 * @param amount
	 * @throws StockException
	 */
	public void sellItem(Item item, int amount) throws StockException {
		Store.inventory.remove(item, amount);
		Store.capital += item.getSellPrice();
		orderItem(item);
	}
	
	/**
	 * function to get the current quantity of an item in the store
	 * @param item
	 * @return item quantity
	 */
	public int getQuantity(Item item) {
		return inventory.getQuantity(item);
	}
	
	/**
	 * function to reorder items if they go below reorder point and 
	 * add to reorder list
	 * @param item
	 * @throws StockException
	 */
	public void orderItem(Item item) throws StockException {
		int difference = item.getReorderAmount() - getQuantity(item);
		if(getQuantity(item) <= item.getReorderPoint())
			addToInventory(item, difference);
			reorderList.add(item, difference);
	}
	
	/**
	 * function to get current reorder list 
	 * @return reorder list
	 */
	public Stock<Item> getOrderList() {
		return reorderList;
	}
	
	/**
	 * function to return current inventory of store
	 * @return store inventory
	 */
	public Stock<Item> getInventory() {
		return inventory;
	}

}
