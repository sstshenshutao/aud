package lab;

import frame.SortArray;

public class QuickSortA extends QuickSort {

	/**
	 * Quicksort algorithm implementation to sort a SorrtArray by choosing the
	 * pivot as the first (leftmost) element in the list
	 * 
	 * @param records
	 *            - list of elements to be sorted as a SortArray
	 * @param left
	 *            - the index of the left bound for the algorithm
	 * @param right
	 *            - the index of the right bound for the algorithm
	 * @return Returns the sorted list as SortArray
	 */
	@Override
	public void Quicksort(SortArray records, int left, int right) {
		// TODO
		// implement the Quicksort A algorithm to sort the records
		// (choose the pivot as the first (leftmost) element in the list)
		if (left < right) {
			int q = lmpPartition(records, left, right);
			Quicksort(records, left, q - 1);
			Quicksort(records, q + 1, right);
		}
	}
	// You may add additional methods here
	private int lmpPartition(SortArray records, int p, int r) {
		// (choose the pivot as the first (leftmost) element in the list)
		super.exchange(records, r, p, records.getElementAt(p));
		return super.partition(records, p, r);
	}
	

}
