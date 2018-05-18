package tests;

import org.junit.*;

import exceptions.CSVFormatException;

import java.io.*;
import java.util.*;

public class ManifestTest {
	
	/**
	 * Creates basic csv file
	 * @throws IOException
	 */
	@Test
	public void testManifest() throws IOException {
		Writer writer = new StringWriter();
		createManifest.writeLine(writer, Arrays.asList(“a”, “b”, “c”, “d”));
		String s = writer.toString();
		System.out.println(“.”+s+”.”);
		assert(s.equals(“a,b,c,d”) );
	}
	
	/**
	 * Data for CSV File not formated.
	 * @throws CSVFormatException
	 */
	@Test (expected = CSVFormatException.class)
	public void testCDVFormating() throws CSVFormatException {
		Writer writer = new StringWriter();
		createManifest.writeLine(writer, Arrays.asList(a, b, c, d));
	}
	
	/**
	 * Path for new CSV file not found. 
	 * @throws FileNotFoundException
	 */
	@Test (expected = FileNotFoundException.class)
	public void testFailedManifestPath() throws FileNotFoundException{
		FileWriter writer = new FileWriter("nill");
		createManifest.writeLine(writer, Arrays.asList(“a”, “b”, “c”, “d”));
	}
}
