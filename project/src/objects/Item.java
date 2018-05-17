package objects;

public class Item {
	
	private String name;
	private int cost;
	private int price;
	private int reorderPoint;
	private int reorderAmount;
	private int temp;
	
	public void item(String name, int cost, int price, int reorderPoint, int reorderAmount, int temp) {
		this.name = name;
		this.cost = cost;
		this.price = price;
		this.reorderPoint = reorderPoint;
		this.reorderAmount = reorderAmount;
		this.temp = temp;
	}
	
	public void item(String name, int cost, int price, int reorderPoint, int reorderAmount) {
		this.name = name;
		this.cost = cost;
		this.price = price;
		this.reorderPoint = reorderPoint;
		this.reorderAmount = reorderAmount;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getReorderPoint() {
		return reorderPoint;
	}
	
	public int getReorderAmount() {
		return reorderAmount;
	}
	
	public int getTemp() {
		return temp;
	}

}
