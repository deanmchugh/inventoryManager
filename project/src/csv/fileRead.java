package csv;

import java.io.*;

import objects.Stock;

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
		 * 
		 */
		public static Stock readSalesLog() {

		}
		
}
