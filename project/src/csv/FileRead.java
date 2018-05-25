package csv;

import java.io.*;

import objects.*;
import delivery.*;
import exceptions.*;

/**
 * Class containing functions related to reading data from a .csv file.
 * @author Tim
 */
public class FileRead {

		private static final String FILE_PATH = System.getProperty("user.dir") + "/Test-Files/";
		private static final String VALUE_SEPERATOR = ",";
		private static final int LENGTH_DRY_ITEM = 5;
		private static final int LENGTH_COLD_ITEM = 6;
		private static final String TRUCK_PREFIX = ">";
		
		private static Stock itemList;
		private static BufferedReader reader;
		private static int salesLogNum = 0;
		
		
		/**
		 * Read item_properties.csv, creates Items from each line of file, and fills a Stock object with Items.
		 * Each item will have a quantity equal to its reorder value.
		 * The returned Stock object is also stored within a private static variable accessible to all methods in this class.
		 * @return Stock object containing every Item in the item_properties.csv file, 
		 * with a quantity equal to each Item's reorder amount.
		 * @throws CSVFormatException Thrown when error in opening or reading file occurs.
		 * @throws StockException Re-throws exceptions thrown by methods used within.
		 */
		public static Stock readProperties() throws CSVFormatException, StockException {
			String fileName = FILE_PATH + "item_properties.csv";
			itemList = new Stock();
			
			try {
				reader = new BufferedReader(new FileReader(fileName));
				
				String line = reader.readLine();
				while (line != null) {
					Item newItem = createItem(line);
					itemList.modifyQuantity(newItem, newItem.getReorderAmount());
					line = reader.readLine();
				}
				
			} catch (FileNotFoundException e) {
				throw new CSVFormatException("Properties file not found!");
			} catch (IOException e){
				throw new CSVFormatException("Error reading properties file!");
			}		
			closeReader();
	
			return itemList;
		}
		
		
		/**
		 * Reads the manifest.csv file, and creates a Manifest from this data.
		 * The Trucks contained in the Manifest each contain Stock with Items specified in the manifest file.
		 * @return A Manifest object containing a number of Trucks as specified by the manifest file.
		 * @throws CSVFormatException Exception thrown when the FileReader has trouble opening, closing, or reading the desired file.
		 * @throws DeliveryException Re-throws exceptions thrown by methods used within.
		 * @throws StockException Re-throws exceptions thrown by methods used within.
		 */
		public static Manifest readManifest() throws CSVFormatException, DeliveryException, StockException {
			String fileName = FILE_PATH + "manifest.csv";
			Manifest manifest = new Manifest();
			Stock currentStock = null;
			String currentTruckType = "";
			
			try {
				reader = new BufferedReader(new FileReader(fileName));
				String line = reader.readLine();
				//Reads to end of file, either creating new Trucks or adding Items to Stock as necessary
				//Every time a new Truck is reached in the file, the previous truck is created with Stock up to that line
				//Created trucks are immediately added to manifest
				while (line != null) {
					
					if (line.contains(TRUCK_PREFIX)) {
						if (!currentTruckType.equals("")) { //Cannot create first Truck until currentStock filled
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
				throw new CSVFormatException("Manifest file not found!");
			} catch (IOException e) {
				throw new CSVFormatException("Error reading manifest file!");
			}
			
			closeReader();
			return manifest;
		}
		
		
		/**
		 * Reads sales logs from file into Stock object and increments counter used in filename
		 * @throws CSVFormatException Exception caused by file not being found, or error in reading/closing file.
		 * @return Stock object containing 
		 * @throws StockException Re-throws exceptions thrown by methods used within.
		 */
		public static Stock readSalesLog() throws CSVFormatException, StockException {
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
				throw new CSVFormatException("Sales log " + salesLogNum + " not found!");
			} catch (IOException e){
				throw new CSVFormatException("Error reading sales log " + "salesLogNum" + "!");
			}
			
			closeReader();
			salesLogNum++;
			return salesData;
		}
		
		
		/**
		 * Creates an Item from properties saved to a single line of a CSV File, constructing a cold or dry item as necessary.
		 * @param line String containing entire contents of one line of a CSV file, including commas, but without newline characters
		 * @return An Item correlating to the properties contained within line. May be either temperature-controlled or dry Item.
		 * @throws CSVFormatException Exception caused by incorrect number of properties required to create Item.
		 * @throws StockException Re-throws exceptions thrown by methods used within.
		 */
		private static Item createItem(String line) throws CSVFormatException, StockException {
			int numProperties = line.split(VALUE_SEPERATOR).length;
			String name = line.split(VALUE_SEPERATOR)[0];
			
			//Size properties array appropriately for type of Item, and populate with Integer values parsed from line
			double[] properties = new double[numProperties - 1];
			//Start at index 1 to skip over String name at index 0
			for (int propertyNum = 1; propertyNum < numProperties; propertyNum++) {
				String property = line.split(VALUE_SEPERATOR)[propertyNum];
				properties[propertyNum - 1] = Double.parseDouble(property);
			}
			
			//Dry and cold Items use different constructors depending on number of properties.
			//If there is not an expected number of properties, an Item cannot be created and the CSV file must be incorrect.
			if (numProperties == LENGTH_DRY_ITEM) {
				return new Item(name, properties[0], properties[1], (int)properties[2], (int)properties[3]);
			} else if (numProperties == LENGTH_COLD_ITEM) {
				return new Item(name, properties[0], properties[1], (int)properties[2], (int)properties[3], properties[4]);
			} else {
				throw new CSVFormatException("Incorrect number of item properties!");
			}
		}
		
		
		/**
		 * Method to attempt to close open file cleanly, used by all public methods.
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
		 * @param itemName String containing the desired item's name.
		 * @return Item with corresponding name to input String, null if no corresponding Item exists in Stock.
		 */
		private static Item getItem(String itemName){
			for (Item item : itemList) {
				if (itemName.equals(item.getName())) {
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
		 * @throws DeliveryException Exception thrown when type of truck not recognized.
		 * @throws StockException Re-throws exceptions thrown by methods used within.
		 */
		private static Truck createTruck(String truckType, Stock contents) throws DeliveryException, StockException {
			Truck newTruck;
			if (truckType.equals("Ordinary")){ //Stock generated, place in truck, add to manifest
				newTruck = new OrdinaryTruck(contents);
			} else if (truckType.equals("Refrigerated")){
				newTruck = new RefrigeratedTruck(contents);
			} else {
				throw new DeliveryException("Manifest contains unknown type of truck!");
			}
			return newTruck;
		}
		
}
