package delivery;

import java.util.ArrayList;
import java.util.Iterator;

import objects.*;
import exceptions.*;

/**
 * Stores a collection of Truck objects in an iterable format.
 * @author Tim
 */
public class Manifest implements Iterable<Truck> {

	private ArrayList<Truck> truckList;
	
	
	/**
	 * Constructor for Manifest, initializes arraylist to store Trucks.
	 */
	public Manifest() {
		truckList = new ArrayList<Truck>();
	}
	
	
	/**
	 * Additional constructor for Manifest. Takes a Stock object, and divides this up into Trucks.
	 * @param totalOrder Stock object containing Items and quantity of Items ordered.
	 * @throws DeliveryException Re-throws exceptions thrown by methods used within.
	 * @throws StockException Re-throws exceptions thrown by methods used within.
	 */
	public Manifest(Stock totalOrder) throws DeliveryException, StockException {
		truckList = new ArrayList<Truck>();
		Truck newTruck;
		int numItemsInTruck;
		
		//Keep adding trucks until order is completed
		while (totalOrder.getTotalQuantity() > 0) {
			if (totalOrder.needsTempControl()){
				//Ensure truck is not over-filled
				numItemsInTruck = Math.min(800, totalOrder.getTotalQuantity()); //TODO: Remove magic number
				newTruck = new RefrigeratedTruck(totalOrder.take(numItemsInTruck));
			} else {
				numItemsInTruck = Math.min(1000, totalOrder.getTotalQuantity()); //TODO: Remove magic number
				newTruck = new OrdinaryTruck(totalOrder.take(numItemsInTruck));
			}
			this.addTruck(newTruck);
		}	
	}
	
	
	/**
	 * Adds a Truck to the manifest.
	 * @param truck Truck object to be added to the manifest.
	 */
	public void addTruck(Truck truck) {
		truckList.add(truck);
	}

	
	/**
	 * Method to allow a for each loop for Trucks within the Manifest. Returns the iterator of the ArrayList storing Trucks.
	 * @return Iterator for next Truck in Manifest.
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
		return cost;
	}
	
}
