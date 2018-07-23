package objects;

import exceptions.StockException;

/**
 * Class to build an Item object with name, cost, sell price, reorder point,
 * reorder amount and temperature.
 * @author Dean McHugh
 *
 */
public class Item implements Comparable<Item> {
	
	private String name;
	private double cost;
	private double sellPrice;
	private int reorderPoint;
	private int reorderAmount;
	private Boolean tempControlled = false;
	private double temperature;
	
	/**
	 * Constructor that creates an Item object. Checks all arguments and returns stock exception 
	 * if any are bellow 0.  
	 * @param name, the String name of the Item. 
	 * @param cost, the cost to buy Item.
	 * @param sellPrice, the price to sell the Item at.
	 * @param reorderPoint, the point if reached requires the item to be ordered. 
	 * @param reorderAmount, the amount of Item to be ordered each time.
	 * @throws StockException if cost, sell price, reorder amount or reorder point are below 0.
	 */
	public Item (String name, double cost, double sellPrice, int reorderPoint, int reorderAmount) throws StockException {
		if (cost <= 0) {
			throw new StockException("Item cost must be a positive value");
		}
		if (sellPrice <= 0) {
			throw new StockException("Item must be sold for a positive price");
		}
		if (reorderPoint < 0) {
			throw new StockException("Item reorder point must be at least zero");
		}
		if (reorderAmount <= 0) {
			throw new StockException("Item reorder amount must be positive");
		}
		
		this.name = name;
		this.cost = cost;
		this.sellPrice = sellPrice;
		this. reorderPoint = reorderPoint;
		this.reorderAmount = reorderAmount;
	}
	
	/**
	 * Constructor that creates an Item object with a temperature. It then changes tempControled to true. 
	 * @param name, the String name of the Item. 
	 * @param cost, the cost to buy Item.
	 * @param sellPrice, the price to sell the Item at.
	 * @param reorderPoint, the point if reached requires the item to be ordered. 
	 * @param reorderAmount, the amount of Item to be ordered each time.
	 * @param temperature, the temp the item needs to stored at.
	 * @throws StockException if cost, sell price, reorder amount or reorder point are below 0.
	 */
	public Item (String name, double cost, double sellPrice, int reorderPoint, int reorderAmount, double temperature) throws StockException {
		this(name, cost, sellPrice, reorderPoint, reorderAmount);
		tempControlled = true;
		this.temperature = temperature;
	}
	
	/**
	 * Method to get and return the current String name of an Item object. 
	 * @return the name of an Item.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Method to get and return the current double cost of an Item object.
	 * @return the cost of an Item.
	 */
	public double getCost() {
		return this.cost;
	}
	
	/**
	 * Method to get and return sell price of Item object.
	 * @return the current price of an Item.
	 */
	public double getSellPrice() {
		return this.sellPrice;
	}
	
	/**
	 * Method to get and return the current int reorder point of an Item object.
	 * @return the reorder point of an Item. 
	 */
	public int getReorderPoint() {
		return this.reorderPoint;
	}
	
	/**
	 * Method to get and return the current int reorder amount of an Item object.
	 * @return the reorder point of an Item.
	 */
	public int getReorderAmount() {
		return this.reorderAmount;
	}
	
	/**
	 * Method to get and return the current boolean value of temp controlled Item. 
	 * @return the temp controlled item value.
	 */
	public Boolean isTempControlled() {
		return this.tempControlled;
	}
	
	/**
	 * Method to get and return the current double temp of an Item object.
	 * @return the temperature control of Item.
	 * @throws StockException if the item has no temp control.
	 */
	public double getTemp() throws StockException {
		if (this.tempControlled) {
			return this.temperature;
		} else {
			throw new StockException("Dry items do not have temperature");
		}
	}


	@Override
	public int compareTo(Item otherItem) {
		
		final int GREATER_THAN = 1;
		final int LESS_THAN = -1;
		
		if (this.tempControlled && !otherItem.isTempControlled()) {
			return LESS_THAN;
		} else if (!this.tempControlled && otherItem.isTempControlled()){
			return GREATER_THAN;
		} else {
			try {
				if (this.temperature > otherItem.getTemp()) {
					return GREATER_THAN;
				} else if (this.temperature < otherItem.getTemp()) {
					return LESS_THAN;
				}
			//N.B. As items are previously checked to have a temperature, exception caused by checking temperature of dry items will not be thrown.
			} catch (StockException exception) {}  
		}
		return this.name.compareTo(otherItem.getName());
	}
	
}