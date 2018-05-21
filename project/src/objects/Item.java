package objects;

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
	private int temp; 
	
	/**
	 * Constructor to instantiate an item with a temp
	 * @param name
	 * @param cost
	 * @param price
	 * @param reorderPoint
	 * @param reorderAmount
	 * @param temp
	 */
	public Item(String name, int cost, int price, int reorderPoint, int reorderAmount, int temp) {
		setName(name);
		setCost(cost);
		setPrice(price);
		setReorderPoint(reorderPoint);
		setReorderAmount(reorderAmount);
		setTemp(temp);
	}

	/**
	 * Constructor to instantiate an item witout temp
	 * @param name
	 * @param cost
	 * @param price
	 * @param reorderPoint
	 * @param reorderAmount
	 */
	public Item(String name, int cost, int price, int reorderPoint, int reorderAmount) {
		setName(name);
		setCost(cost);
		setPrice(price);
		setReorderPoint(reorderPoint);
		setReorderAmount(reorderAmount);
	}
	
	private void setTemp(int temp) StockException {
		if(temp <= 0) {
			throw new StockException("Temp must be higher then 0");
		}
		this.temp = temp; 
	}

	private void setReorderAmount(int reorderAmount) throws StockException {
		if(reorderAmount <= 0) {
			throw new StockException("Reorder amount must be higher then 0");
		}
		this.reorderAmount = reorderAmount;
	}

	private void setReorderPoint(int reorderPoint) throws StockException {
		if(reorderPoint <= 0) {
			throw new StockException("Reorder point must be higher then 0");
		}
		this.reorderPoint = reorderPoint;
	}

	private void setPrice(int price) throws  StockException {
		if(price <= 0) {
			throw new StockException("Price must be higher then 0");
		}
		this.price = price;
	}

	private void setCost(int cost) throws StockException {
		if(cost <= 0) {
			throw new StockException("Cost must be higher then 0");
		}
		this.cost = cost;		
	}

	private void setName(String name) throws StockException{
		if(name == null) {
			throw new StockException("Item needs name");
		}
		this.name = name;
		
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
