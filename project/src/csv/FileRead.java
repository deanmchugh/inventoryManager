package csv;

import java.io.*;
import java.util.List;

import objects.*;
import delivery.*;
import exceptions.CSVFormatException;
import exceptions.DeliveryException;

/**
 * Class containing functions related to writing data to a .csv file
 * @author Tim
 */
public class FileRead {

		private static final String FILE_PATH = "../Sample-Files/";
		private static final String VALUE_SEPERATOR = ",";
		private static final int LENGTH_DRY_ITEM = 4;
		private static final int LENGTH_COLD_ITEM = 5;
		private static final String TRUCK_PREFIX = ">";
		
		private static Stock itemList;
		private static BufferedReader reader;
		private static int salesLogNum = 0;

		
		/**
		 * Read item_properties.csv, creates Items from each line of file, and fills a Stock object with Items.
		 * Each item will have an arbitrary quantity of 1.
		 * The returned Stock object is also stored within a private static variable accessible to all methods in this class.
		 * @author Tim
		 * @return Stock object containing 1 of every Item in the item_properties.csv file.
		 * @throws CSVFormatException 
		 */
		public static Stock readProperties() throws CSVFormatException {
			String fileName = FILE_PATH + "item_properties.csv";
			itemList = new Stock();
			
			try {
				reader = new BufferedReader(new FileReader(fileName));
				
				String line = reader.readLine();
				while (line != null) {
					itemList.modifyQuantity(createItem(line), 1);
					line = reader.readLine();
				}
				
			} catch (FileNotFoundException e) {
				throw new CSVFormatException("File at\n" + fileName + "\nNot found!");
			} catch (IOException e){
				throw new CSVFormatException("Error reading\n" + fileName);
			}		
			closeReader();
	
			return itemList;
		}
		
		
		/**
		 * Reads the manifest.csv file, and creates a Manifest from this data.
		 * The Trucks contained in the Manifest each contain Stock with Items specified in the manifest file.
		 * Additional object parameters such as Manifest pricing and Truck temperatures have not been calculated.
		 * @author Tim
		 * @return A Manifest object containing a number of Trucks as specified by the manifest file.
		 * @throws CSVFormatException Exception thrown when the FileReader has trouble opening, closing, or reading the desired file.
		 */
		public static Manifest readManifest() throws CSVFormatException {
			String fileName = FILE_PATH + "manifest.csv";
			Manifest manifest = new Manifest();
			Truck currentTruck;
			Stock currentStock;
			String currentTruckType = "";
			
			try {
				reader = new BufferedReader(new FileReader(fileName));
				String line = reader.readLine();
				//Reads to end of file, either creating new Trucks or adding Items to Stock as necessary
				//Every time a new Truck is reached in the file, the previous truck is created with Stock up to that line
				//Created trucks are immediately added to manifest
				while (line != null) {
					
					if (line.contains(TRUCK_PREFIX)) {
						if (currentTruckType != "") { //Cannot create first Truck until currentStock filled
							manifest.addTruck(createTruck(currentTruckType, currentStock));
						}
						//Reset stock and truck type for new truck
						currentTruckType = line.substring(1);
						currentStock = new Stock();
					} else { //Line contains Item details
						String itemName = line.split(VALUE_SEPERATOR)[0];
						int itemQuantity = Integer.parseInt(line.split(VALUE_SEPERATOR)[1]);
						Item item = getItem(itemName);
						currentStock.modifyQuantity(item, itemQuantity);
					}
					
					line = reader.readLine();
				}
				
				//End of file reached, add final truck to manifest
				manifest.addTruck(createTruck(currentTruckType, currentStock));
				
			} catch (FileNotFoundException e) {
				throw new CSVFormatException("File at \n" + fileName + "/nNot found!");
			} catch (IOException e) {
				throw new CSVFormatException("Error reading\n" + fileName);
			}
			
			closeReader();
			return manifest;
		}
		
		
		/**
		 * Reads sales logs from file into Stock object and increments counter used in filename
		 * @throws CSVFormatException Exception caused by file not being found, or error in reading/closing file.
		 * @return Stock object containing 
		 * @author Tim
		 */
		public static Stock readSalesLog() throws CSVFormatException {
			String fileName = FILE_PATH + "sales_log_" + Integer.toString(salesLogNum) + ".csv";
			String itemName;
			int itemQuantity;
			Item item;
			Stock salesData = new Stock();
			
			try {
				reader = new BufferedReader(new FileReader(fileName));
				
				String line = reader.readLine();
				while (line != null) {
					itemName = line.split(VALUE_SEPERATOR)[0];
					itemQuantity = Integer.parseInt(line.split(VALUE_SEPERATOR)[1]);
					item = getItem(itemName);
					salesData.modifyQuantity(item, itemQuantity);
					
					line = reader.readLine();
				}
			} catch (FileNotFoundException e) {
				throw new CSVFormatException("File at\n" + fileName + "\nNot found!");
			} catch (IOException e){
				throw new CSVFormatException("Error reading\n" + fileName);
			}
			
			closeReader();
			salesLogNum++;
			return salesData;
		}
		
		
		/**
		 * Creates an Item from properties saved to a single line of a CSV File, constructing a cold or dry item as necessary.
		 * @author Tim
		 * @param line String containing entire contents of one line of a CSV file, including commas, but without newline characters
		 * @return An Item correlating to the properties contained within line. May be either temperature-controlled or dry Item.
		 * @throws CSVFormatException Exception caused by incorrect number of properties required to create Item.
		 */
		private static Item createItem(String line) throws CSVFormatException {
			int numProperties = line.split(VALUE_SEPERATOR).length;
			String name = line.split(VALUE_SEPERATOR)[0];
			
			//Size properties array appropriately for type of Item, and populate with Integer values parsed from line
			int[] properties = new int[numProperties - 1];
			for (int propertyNum = 1; propertyNum < numProperties; propertyNum++) {
				String property = line.split(VALUE_SEPERATOR)[propertyNum + 1];
				properties[propertyNum - 1] = Integer.parseInt(property);
			}
			
			//Dry and cold Items use different constructors depending on number of properties.
			//If there is not an expected number of properties, an Item cannot be created and the CSV file must be incorrect.
			if (numProperties == LENGTH_DRY_ITEM) {
				return new Item(name, properties[0], properties[1], properties[2], properties[3]);
			} else if (numProperties == LENGTH_COLD_ITEM) {
				return new Item(name, properties[0], properties[1], properties[2], properties[3], properties[4]);
			} else {
				throw new CSVFormatException("Incorrect number of item properties!");
			}
		}
		
		
		/**
		 * Method to attempt to close open file cleanly, used by all public methods.
		 * @author Tim
		 * @throws CSVFormatException Exception occurs when program fails to close file correctly.
		 */
		private static void closeReader() throws CSVFormatException {
			try {
				reader.close();
			} catch (IOException e) {
				throw new CSVFormatException("Error closing file!");
			}
		}
		
		
		/**
		 * Searches through the stock generated from the item properties file to locate an Item object with a desired name.
		 * @author Tim
		 * @param itemName String containing the desired item's name.
		 * @return Item with corresponding name to input String, null if no corresponding Item exists in Stock.
		 */
		private static Item getItem(String itemName){
			for (Item item : itemList) {
				if (item.getName() == itemName) {
					return item;
				}
			}
			return null;
		}
		
		
		/**
		 * Private helper method to create either a RefrigeratedTruck or OrdinaryTruck depending on input string.
		 * @param truckType String containing either "Ordinary" or "Refrigerated".
		 * @param contents Stock object to reside in Truck.
		 * @return RefrigeratedTruck or OrdinaryTruck with contents inside.
		 * @throws DeliveryException Exception thrown when type of truck not recognised.
		 * @author Tim
		 */
		private static Truck createTruck(String truckType, Stock contents) throws DeliveryException{
			Truck newTruck;
			if (truckType == "Ordinary"){ //Stock generated, place in truck, add to manifest
				newTruck = new OrdinaryTruck(contents);
			} else if (truckType == "Refrigerated"){
				newTruck = new RefrigeratedTruck(contents);
			} else {
				throw new DeliveryException("Manifest contains unknown type of truck!");
			}
			return newTruck;
		}
		
}
