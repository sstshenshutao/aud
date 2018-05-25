package lab;

import frame.SortArray;

/**
 * Abstract superclass for the Quicksort algorithm.
 * 
 * @author NAJI
 */
public abstract class QuickSort {

	// DO NOT modify this method
	public abstract void Quicksort(SortArray records, int left, int right);

	// You may add additional methods here
	protected int partition(SortArray records, int p, int r) {
		SortingItem x = records.getElementAt(r);
		int i = p - 1;
		
		for (int j=p; j < r; j++) {
			SortingItem Aj = records.getElementAt(j);
			int comparator = Aj.compareTo(x);
			if ( comparator <= 0 ) {
				i++;
				exchange(records, i, j, Aj);
			}
		}
		exchange(records, i+1, r, x);
		return i+1;
	}
	
//	protected int partition(SortArray records, int p, int r) {
//		SortingItem x = records.getElementAt(p);
//		int i = r + 1;
//		
//		for (int j = r; j > p; j--) {
//			SortingItem Aj = records.getElementAt(j);
//			int comparator = Aj.compareTo(x);
//			if ( comparator >= 0 ) {
//				i--;
//				exchange(records, i, j, Aj);
//			}
//		}
//		exchange(records, i-1, r, x);
//		return i-1;
//	}
	
	
	protected void exchange(SortArray records, int indexi, int indexj, SortingItem Aj ) {
		//set i to j;
		records.setElementAt(indexj, records.getElementAt(indexi));
		//set j to i;
		records.setElementAt(indexi, Aj);
	}

}
