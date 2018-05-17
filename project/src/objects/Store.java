package objects;

public class Store {

	private static int capital;
	private static String name;
	private static Stock inventory;

	private static Store myObj;
	
	private Store(int capital, String name, Stock inventory) {
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
	
	public int getCapital() {
		return capital;
	}
	
	public String getName() {
		return name;
	}
	
	public Stock getStoke() {
		return inventory;
	}
}
