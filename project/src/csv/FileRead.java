package csv;

import java.io.*;

import objects.Stock;
import exceptions.CSVFormatException;

/**
 * Class containing functions related to writing data to a .csv file
 * @author Tim
 */
public class FileRead {

		private static final String filePath = "../Sample-Files/";
		private static String NEW_LINE_SYM = "\n";
		private static String VALUE_SEPERATOR = ",";
		
		private static BufferedReader reader;
		private static int salesLogNum = 0;

		
		/**
		 * 
		 */
		public static void readProperties() {
			String fileName = filePath + "item_properties.csv";
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
		
}
