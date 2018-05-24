package objects;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 * An iterator for the Stock collection 
 * @author Dean McHugh
 *
 * @param <E>
 */

public class StockIterator<E> implements Iterator<E> {
	
	private Iterator<Entry<E, Integer>> collectionIterator;
	private Entry<E, Integer> currentEntry;
	private int currentCount;
	
	/**
	 * Function to initialize a StockIterator
	 * @param iterator
	 */
	public StockIterator(Iterator<Entry<E, Integer>> iterator) {
		collectionIterator = iterator;
		advanceEntry();
	}

	public boolean hasNext() {
		return currentEntry != null;
	}

	public E next() {
		E nextItem = currentEntry.getKey();
		if (currentCount == currentEntry.getValue()) {
			advanceEntry();
		} else {
			currentCount++;
		}
		return nextItem;
	}
	
	/**
	 * Function to advance current entry
	 */
	private void advanceEntry() {
		do {
			currentEntry = collectionIterator.hasNext() ? collectionIterator.next() : null;
		} while (currentEntry != null && currentEntry.getValue() <= 0);
		currentCount = 1;
	}

}
