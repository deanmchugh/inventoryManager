package objects;

import java.util.Iterator;
import java.util.TreeMap;

import exceptions.StockException;
/**
 * A class to create a collection of items in a tree map. 
 * @author Dean McHugh
 *
 */

public class Stock implements Iterable<Item> {

	private TreeMap<Item, Integer> itemList;
	
	/**
	 * Method to create new item list collection of key Items and value integer. 
	 */
	public Stock() {
		itemList = new TreeMap<Item, Integer>();
	}
	
	/**
	 * Method to modify the current amount of an Item in the item list collection. 
	 * @param item, the item that wants to change value of.
	 * @param quantity, the amount the item value will be changed.
	 * @throws StockException if Item is reduced to below 0 and if the item added is negative. 
	 */
	public void modifyQuantity(Item item, int quantity) throws StockException {
		int currentQuantity;
		
		if (itemList.containsKey(item)) {
			currentQuantity = itemList.get(item);
			if (currentQuantity + quantity < 0) {
				throw new StockException("Cannot reduce Item's quantity bellow zero");
			} else {
				itemList.put(item, currentQuantity + quantity);
			}

		} else if (quantity < 0) {
			throw new StockException("Cannot add Item to Stock with negative quantity");
		} else {
			itemList.put(item, quantity);
		}
	}
	
	/**
	 * Method to get and return the current int quantity of an item in the item list. 
	 * @param item, the item you want the quantity of.
	 * @return the current quantity of an Item.
	 */
	public int getQuantity(Item item) {
		return itemList.get(item);
	}
	
	/**
	 * Method to get and return the current boolean value if an Item is in the item list.
	 * @param item, the item you want to check is in the list.
	 * @return true if item is in item list. false if item is absent.
	 */
	public Boolean itemInStock(Item item) {
		if (itemList.containsKey(item)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to get and return the current int total quantity of all items in the item list.
	 * @return the total quantity if items in the list. 
	 */
	public int getTotalQuantity() {
		int total = 0;
		for (int quantity : itemList.values()) {
			total += quantity;
		}
		return total;
	}
	
	/**
	 * Method to get and return the current boolean value if any Item in the item list needs temperature control.
	 * @return true if item in collection needs temperature control or false if all dry items.
	 */
	public Boolean needsTempControl() {
		for (Item item : itemList.keySet()) {
			if (item.isTempControlled() && itemList.get(item) > 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method to iterate through item list of returns the lowest current double temperature of items.
	 * @return the lowest temperature of all items in list.
	 * @throws StockException if all items in list are dry items.
	 */
	public double getMinTemp() throws StockException {
		double minTemp = 0;
		Boolean tempFound = false;
		
		if (this.needsTempControl()) {
			for (Item item : itemList.keySet()) {
				if (item.isTempControlled() && itemList.get(item) > 0) {
					if (!tempFound) {
						minTemp = item.getTemp();
						tempFound = true;
					} else if (item.getTemp() < minTemp) {
						minTemp = item.getTemp();
					}
				}
			}
		} else {
			throw new StockException("Dry goods dont have minimum temp");
		}
		
		return minTemp;
	}
	
	/**
	 * Method to take items from one collection and move it to another collection.
	 * @param quantity, the quantity of items to be transfered. 
	 * @return a new collection of items in new stock list.
	 * @throws StockException Re-throws exceptions thrown by methods used within.
	 */
	public Stock take(int quantity) throws StockException {
		Stock newStock = new Stock();
		int quantityTaken = 0;
		
		for (Item item : itemList.keySet()) {
			int currentQuantity = itemList.get(item);
			if (currentQuantity > 0) {
				if (quantity - quantityTaken <= 0) {
					break;
				} else if (currentQuantity <= quantity - quantityTaken) {
					newStock.modifyQuantity(item, currentQuantity);
					this.modifyQuantity(item, -currentQuantity);
					quantityTaken += currentQuantity;
					
				} else if (currentQuantity > quantity - quantityTaken) {
					newStock.modifyQuantity(item, quantity - quantityTaken);
					this.modifyQuantity(item, quantityTaken - quantity);
					quantityTaken += quantity - quantityTaken;
					
				}
			}
		}
		
		return newStock;
	}


	@Override
	public Iterator<Item> iterator() {
		return itemList.keySet().iterator();
	}
	
}
