package GUI;

import javax.swing.SwingUtilities;

import csv.*;
import delivery.*;
import exceptions.*;
import objects.*;

/**
 * The main entry point to the store assistant program.
 * @author Dean McHugh
 *
 */
public class EntryPoint {
	
	public static Store shopFront;
	static Stock itemList;
	static Manifest shippingOrder;
	static boolean initalOrder = true;
	
	public static void main(String[] args) throws CSVFormatException, StockException, DeliveryException {
		shopFront = Store.getInstance();
		shopFront.setName("Local Store");
		SwingUtilities.invokeLater(new GUI("StoreHelper"));
	}
	
	/**
	 * Method to import the properties of the items to be stored.
	 * @throws CSVFormatException if csv is not formated or missing.
	 * @throws StockException if items are reduced below 0.
	 */
	public static void importItemProperties() throws CSVFormatException, StockException {
		itemList = FileRead.readProperties();
	}
	
	/**
	 * Method to export the current manifest from the store. gets item list if first time then
	 * the store order list each time after that.
	 * @throws DeliveryException if truck is negative value.
	 * @throws StockException if stock is negative value.
	 * @throws CSVFormatException if csv is not formated or missing.
	 */
	public static void exportManifest() throws DeliveryException, StockException, CSVFormatException {
		if(initalOrder) {
			initalOrder = false;
			shippingOrder = new Manifest(itemList);
			FileWrite.writeManifest(shippingOrder);
		} else {
			shippingOrder = new Manifest(shopFront.getOrderList());
			FileWrite.writeManifest(shippingOrder);
		}
	}
	
	/**
	 * Method to import the current manifest if items into the store.
	 * @throws DeliveryException if truck is negative value.
	 * @throws StockException if stock is negative value.
	 * @throws CSVFormatException if csv is not formated or missing.
	 */
	public static void importManifest() throws CSVFormatException, DeliveryException, StockException {
		shippingOrder = FileRead.readManifest();
		for (Truck truck : shippingOrder) {
			Stock contents = truck.getCargo();
			for (Item item : contents) {
				shopFront.addToInventory(item, contents.getQuantity(item));
			}
		}
		shopFront.reduceCapital(shippingOrder.getTotalCost());
		shopFront.resetOrderList();
	}
	
	/**
	 * Method to import the current store sales logs
	 * @throws StockException if the stock is reduced to negative.
	 */
	public static void importSales() throws StockException {
		Stock sales = null;
		try {
			sales = FileRead.readSalesLog();
		} catch (CSVFormatException fileException) {
			//End program as next sales log does not seem to exist
		}
		
		for (Item itemSold : sales) {
			shopFront.sellItem(itemSold, sales.getQuantity(itemSold));
		}
	}
}
