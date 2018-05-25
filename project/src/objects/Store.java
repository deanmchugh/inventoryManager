package objects;

import objects.Item;
import exceptions.StockException;

/**
 * Class to create an instance of a store with arguments name, capital, inventory and order list.
 * @author Dean McHugh
 *
 */
public class Store {

	private final double STARTING_CAPITAL = 100000d;

	private static Store instance = null;
	private double capital;
	private String name;
	private Stock inventory;
	private Stock orderList;
	
	/**
	 * Method that calls the reset method when a new instance of store is created.
	 */
	private Store(){
		reset();
	}

	/**
	 * Method to create a new instance of store if one dosen't exist already.
	 * @return the shop instance.
	 */
	public static Store getInstance(){
		if (instance == null){
			instance = new Store();
		}
		return instance;
	}
	
	/**
	 * Method to reset the store capital, name, inventory and order list to default parameters.
	 */
	public void reset(){
		capital = STARTING_CAPITAL;
		name = "";
		inventory = new Stock();
		orderList = new Stock();
	}
	
	/**
	 * Method to get and return the current double capital of shop instance.
	 * @return the current shop capital.
	 */
	public double getCapital(){
		//Rounds to two decimal places for display, but still holds amount at full resolution.
		return Math.round(capital*100d)/100d;
	}

	/**
	 * Method to set the name of the current shop instance.
	 * @param newName, the name you want the store to become.
	 */
	public void setName(String newName){
		name = newName;
	}

	/**
	 * Method to get and return the current name of the store.
	 * @return the current store name.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Method to get and return the current stock inventory of store.
	 * @return the current store inventory. 
	 */
	public Stock getInventory(){
		return inventory;
	}
	
	/**
	 * Method to get and return the current int quantity of an item in the store inventory.
	 * @param item, the item that you want to get the quantity of.
	 * @return the current item quantity.
	 * @throws StockException if item is not in the current store inventory.
	 */
	public int getQuantity(Item item) throws StockException {
		return inventory.getQuantity(item);
	}

	/**
	 * Method to add an item to the current store inventory.
	 * @param item, the item you want to add to the inventory.
	 * @param quantity, the amount of the item you want to add.
	 * @throws StockException if the quantity is a negative number.
	 */
	public void addToInventory(Item item, int quantity) throws StockException {
		if (quantity < 0){
			throw new StockException("Can't increase quantity by a negative number");		
		}		
		inventory.modifyQuantity(item, quantity);
	}

	/**
	 * Method to sell an item in the current store inventory.
	 * Adds price of fruit to capital of store.
	 * @param item, the item that is being sold.
	 * @param quantity, the amount if the item being sold.
	 * @throws StockException if item is not in the store inventory, a negative quantity or more then the store has.
	 */
	public void sellItem(Item item, int quantity) throws StockException {
		//Check if Item can be sold.
		if (!inventory.itemInStock(item)) {
			throw new StockException("Cannot sell an Item not in the Store Inventory");
		} else if (quantity < 0){
			throw new StockException("Can't sell negative quantities of Items");
		} else if (quantity > inventory.getQuantity(item)){
			throw new StockException("Can't sell more Items than Store has in Inventory");
		}
		
		//Remove quantity of item from store and increase capital.
		inventory.modifyQuantity(item, -quantity);
		capital += (double)(quantity * item.getSellPrice());

		//Re-order Items as necessary
		if (inventory.getQuantity(item) <= item.getReorderPoint()){
			orderList.modifyQuantity(item, item.getReorderAmount());
		}
	}

	/**
	 * Method to reduce the capital of the store instance after selling or reordering stock.
	 * @param cost, the amount to be taken from the current store capital.
	 * @throws StockException if the amount reduces the store capital to bellow 0.
	 */
	public void reduceCapital(double cost) throws StockException {
		if (capital - cost < 0){
			throw new StockException("Cannot reduce Store capital past 0");
		}
		capital -= cost;
	}
	
	/**
	 * Method to get and return the current store order list.
	 * @return the current store order list.
	 */
	public Stock getOrderList() {
		return orderList;
	}
	
	/**
	 * Method to reset the current order list to a new stock object. 
	 */
	public void resetOrderList() {
		orderList = new Stock();
	}
}