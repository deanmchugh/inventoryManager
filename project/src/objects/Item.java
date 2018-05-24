package objects;

import java.lang.reflect.Constructor;

import exceptions.StockException;

/**
 * Class to build an Item
 * @author Dean McHugh
 *
 */
public class Item {
	
	private String name;
	private int cost;
	private int price;
	private int reorderPoint;
	private int reorderAmount;
	private int temp = 0;
	
	/**
	 * Constructor to instantiate an item
	 * @param name
	 * @param cost
	 * @param price
	 * @param reorderPoint
	 * @param reorderAmount
	 * @param temp
	 */
	public Item(String name, int cost, int price, int reorderPoint, int reorderAmount, int temp) throws StockException{
		if(cost<=0||price<=0||reorderPoint<=0||reorderAmount<=0)
			throw new StockException();
		this.name = name;
		this.cost = cost;
		this.price = price;
		this.reorderPoint = reorderPoint;
		this.reorderAmount = reorderAmount;
		this.temp = temp;
	}	
	
	/**
	 * getter function to get item name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * getter function to get item cost
	 * @return cost
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * getter function to get item sell price
	 * @return price
	 */
	public int getSellPrice() {
		return price;
	}
	
	/**
	 * getter function to get item reorder point
	 * @return reorderPoint
	 */
	public int getReorderPoint() {
		return reorderPoint;
	}
	
	/**
	 * getter function to get item reorder amount
	 * @return reorderAmount
	 */
	public int getReorderAmount() {
		return reorderAmount;
	}
	
	/**
	 * getter function to get item temp
	 * @return temp
	 */
	public int getTemp() {
		return temp;
	}
}
