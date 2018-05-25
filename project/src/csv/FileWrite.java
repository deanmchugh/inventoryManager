package csv;

import java.io.FileWriter;

import exceptions.CSVFormatException;
import objects.*;
import delivery.*;

/**
 * Class containing functions related to writing data to a .csv file.
 * For the purpose of this project, only a single file is written to, so the file path is pre-defined.
 * @author Tim
 */
public class FileWrite {
	
	private static final String FILE_NAME = System.getProperty("user.dir") + "/Test-Files/manifest.csv";
	private static final String NEW_TRUCK_SYM = ">";
	private static final String NEW_LINE_SYM = "\n";
	private static final String VALUE_SEPERATOR = ",";
	
	private static FileWriter writer;
	
	/**
	 * Writes shipping Manifest to .csv file, overwriting existing file.
	 * The type of each Truck in the Manifest is written to a separate line, each followed by its Contents.
	 * @param shippingManifest Manifest object containing each Truck assigned to deliver Stock to the Store.
	 * @throws CSVFormatException Exception caused by errors in writing or closing file.
	 */
	public static void writeManifest(Manifest shippingManifest) throws CSVFormatException {
		try {
			writer = new FileWriter(FILE_NAME);
			
			for (Truck truck : shippingManifest) {
				writer.append(NEW_TRUCK_SYM + truck.getType() + NEW_LINE_SYM);
				writeContents(truck.getCargo());
			}
		} catch (Exception e) {
			throw new CSVFormatException("Error occured writing Manifest!");
		}
		
		try {
			writer.close();
		} catch (Exception e) {
			throw new CSVFormatException("Error occured closing Manifest file!");
		}
	}
	

	/**
	 * For each Item in a Truck, writes the Item name and quantity to a new line of the manifest .csv file.
	 * @param contents A Stock object containing Items and quantity of Items aboard a single truck.
	 * @throws CSVFormatException Thrown when an error occurs writing a line.
	 */
	private static void writeContents(Stock contents) throws CSVFormatException {
		try {
			for (Item item : contents) {
				writer.append(item.getName() + VALUE_SEPERATOR + 
						Integer.toString(contents.getQuantity(item)) + NEW_LINE_SYM);
			} 
		} catch (Exception e) {
			throw new CSVFormatException("Could not write an Item to Manifest file!");
		}
	}
	
}
