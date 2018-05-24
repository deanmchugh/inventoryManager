package delivery;

import java.util.ArrayList;
import java.util.Iterator;

import objects.Item;
import objects.Stock;

/**
 * Stores a collection of Truck objects in an iterable format.
 * @author Tim
 */
public class Manifest implements Iterable<Truck> {

	private ArrayList<Truck> truckList;
	
	
	/**
	 * Constructor for Manifest, initializes arraylist to store Trucks.
	 * @author Tim
	 */
	public Manifest() {
		truckList = new ArrayList<Truck>();
	}
	
	
	/**
	 * Adds a Truck to the manifest.
	 * @param truck Truck object to be added to the manifest.
	 * @author Tim
	 */
	public void addTruck(Truck truck) {
		truckList.add(truck);
	}

	
	/**
	 * Method to allow a for each loop for Trucks within the Manifest. Returns the iterator of the ArrayList storing Trucks.
	 * @return Iterator for next Truck in Manifest.
	 * @author Tim
	 */
	@Override
	public Iterator<Truck> iterator() {
		return truckList.iterator();
	}
	
	public double getTotalCost() {
		double cost = 0;
		for (Truck truck : truckList) {
			Stock contents = truck.getCargo();
			for (Item item : contents) {
				cost += item.getCost() * contents.getQuantity(item);
			}
			cost += truck.getCost();
		}
		//TODO: Round cost to 2 decimal places?
		return cost;
	}
	
}
