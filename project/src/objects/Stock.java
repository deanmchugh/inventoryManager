package objects;

import java.util.Set;
import exceptions.StockException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Constructs a collection on Items
 * @author Dean McHugh
 *
 * @param <Item>
 */
@SuppressWarnings("hiding")
public class Stock<Item> {
	
	private Map<Item, Integer> contents;
	
	public Stock() {
		contents = new HashMap<Item, Integer>();
	}
	
	/**
	 * Function to return the total amount of one Item in the collection
	 * @param element
	 * @return quantity of elements
	 */
	public int getQuantity(Item item) {
		if (contents.containsKey(item))
			return contents.get(item);
		else
			return 0;
	}
	
	public boolean itemInStock(Item item) {
		if (contents.containsKey(item))
			return true;
		else 
			return false;
	}
	
	/**
	 * Function to add a set amount of Items to the Stock collection 
	 * @param item
	 * @param quantity
	 * @throws StockException
	 */
	public void add(Item item, int quantity) throws StockException {
		if (quantity<0)
			throw new StockException();
		else if (contents.containsKey(item))
			contents.put(item, quantity + contents.get(item));
		else 
			contents.put(item, quantity);
	}
	
	/**
	 * Function to remove a set amount of Items from the Stock collection
	 * @param element
	 * @param quantity
	 * @throws StockException
	 */
	public void remove(Item item, int quantity) throws StockException {
		if (quantity<0 || getQuantity(item)<quantity)
			throw new StockException();
		else
			contents.put(item, getQuantity(item)-quantity);
	}
	
	/**
	 * Function to iterate through the Stock collection
	 * @return iterator of Stock collection
	 */
	public Iterator<Item> iterator() {
		return new StockIterator<Item>(contents.entrySet().iterator());
	}
	
	public Set<Item> toSet() {
		return contents.keySet();
	}
	
	/**
	 * Function to return the size of the collection of Items
	 * @return total of items
	 */
	public int getTotalQuantity() {
		int total = 0;
		for (Item item : toSet())
			total += getQuantity(item);
		return total;
	}
	
	/**
	 * Checks the temp of an item to see if it requires a temp control
	 * @param item
	 * @return true if item has a temp
	 */
	public boolean needsTempControl() {
		Iterator<Item> itr = iterator();
		while (itr.hasNext()) {
			Item item = itr.next();
			if(item.getTemp() != 0)
				return true;
			else
				return false;
		}
	}
	
	public int getMinTemp() {
		int minTemp = 0;
		Iterator<Item> itr = iterator();
		while (itr.hasNext()) {
			Item item = itr.next();
			if(item.getTemp() > minTemp)
				minTemp = item.getTemp();
		}
		return minTemp;
	}
	
	public void take(int amount) {
		
	}
}
