package GUI;

import csv.*;
import delivery.*;
import exceptions.*;
import objects.*;

public class main {
	
	public static void main(String[] args) throws CSVFormatException, StockException, DeliveryException {
		logicLoopTest();
	}

	//Not to be implemented as a straight method like this. Each chunk of code should be executed on a button press
	public static void logicLoopTest() throws CSVFormatException, StockException, DeliveryException {
		//GUI STARTS
		Store shopFront = Store.getInstance();
		shopFront.setName("Local Store");
		System.out.println(shopFront.getCapital());
		
		//BUTTON PRESSED "READ ITEM PROPERTIES"
		Stock itemList = FileRead.readProperties();
		
		//BUTTON PRESSED EXPORT MANIFEST
		Manifest shippingOrder = new Manifest(itemList);
		FileWrite.writeManifest(shippingOrder);
		
		
		while(true) {
			//BUTTON PRESSED IMPORT MANIFEST
			//Read manifest, load shop with contents, reduce store capital, reset order list
			shippingOrder = FileRead.readManifest();
			for (Truck truck : shippingOrder) {
				Stock contents = truck.getCargo();
				for (Item item : contents) {
					shopFront.addToInventory(item, contents.getQuantity(item));
				}
			}
			shopFront.reduceCapital(shippingOrder.getTotalCost());
			shopFront.resetOrderList();
			
			System.out.println(shopFront.getCapital());
			
			//BUTTON PRESSED IMPORT SALES LOG
			//Load sales log, change store quantities, add necessary items to order list (Automatically handled by store class)
			Stock sales;
			try {
				sales = FileRead.readSalesLog();
			} catch (CSVFormatException fileException) {
				//End program as next sales log does not seem to exist
				break;
			}
			
			for (Item itemSold : sales) {
				shopFront.sellItem(itemSold, sales.getQuantity(itemSold));
			}
			
			
			//BUTTON PRESSED EXPORT MANIFEST
			//Write new manifest from shop's order list
			shippingOrder = new Manifest(shopFront.getOrderList());
			FileWrite.writeManifest(shippingOrder);
		}
		System.out.println("No more sales logs!");
	}

}
