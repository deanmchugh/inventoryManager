package csv;

import java.io.*;

import objects.*;
import exceptions.CSVFormatException;

/**
 * Class containing functions related to writing data to a .csv file
 * @author Tim
 */
public class FileRead {

		private static final String filePath = "../Sample-Files/";
		private static String VALUE_SEPERATOR = ",";
		private static int LENGTH_DRY_ITEM = 4;
		private static int LENGTH_COLD_ITEM = 5;
		
		private static BufferedReader reader;
		private static int salesLogNum = 0;

		
		/**
		 * Read item_properties.csv, creates Items from each line of file, and fills a Stock object with Items.
		 * Each item will have an arbitrary quantity of 1.
		 * @author Tim
		 * @return 
		 * @throws CSVFormatException 
		 */
		public static Stock readProperties() throws CSVFormatException {
			String fileName = filePath + "item_properties.csv";
			Stock itemList = new Stock();
			
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
				throw new CSVFormatException("Error reading file!");
			}
			
			try {
				reader.close();
			} catch (IOException e) {
				throw new CSVFormatException("Error closing file!");
			}
		}
		
		
		/**
		 * 
		 */
		public static void readManifest() {
			String fileName = filePath + "manifest.csv";
			
			
		}
		
		
		/**
		 * Reads sales logs from file into Stock object and increments counter used in filename
		 * @throws CSVFormatException Exception caused by file not being found, or error in reading/closing file.
		 * @return Stock object containing 
		 * @author Tim
		 */
		public static Stock readSalesLog() throws CSVFormatException {
			String fileName = filePath + "sales_log_" + Integer.toString(salesLogNum) + ".csv";
			String itemName;
			int itemQuantity;
			Stock salesData = new Stock();
			
			try {
				reader = new BufferedReader(new FileReader(fileName));
				
				String line = reader.readLine();
				while (line != null) {
					itemName = line.split(VALUE_SEPERATOR)[0];
					itemQuantity = Integer.parseInt(line.split(VALUE_SEPERATOR)[1]);
					
	
					line = reader.readLine();
				}
			} catch (FileNotFoundException e) {
				throw new CSVFormatException("File at\n" + fileName + "\nNot found!");
			} catch (IOException e){
				throw new CSVFormatException("Error reading file!");
			}
			try {
				reader.close();
			} catch (IOException e) {
				throw new CSVFormatException("Error closing file!");
			}
			
			salesLogNum++;
			return salesData;
		}
		
		/**
		 * Creates an Item from properties saved to a single line of a CSV File, constructing a cold or dry item as necessary.
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
			} else if (numProperties = LENGTH_COLD_ITEM) {
				return new Item(name, properties[0], properties[1], properties[2], properties[3], properties[4]);
			} else {
				throw new CSVFormatException();
			}
		}
		
}
