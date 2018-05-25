package lab;

import frame.SortArray;

public class QuickSortB extends QuickSort {

	/**
	 * Quicksort algorithm implementation to sort a SorrtArray by choosing the
	 * pivot as the median of the elements at positions (left,middle,right)
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
		// implement the Quicksort B algorithm to sort the records
		// (choose the pivot as the median value of the elements at position
		// (left (first),middle,right(last)))
		if (left < right) {
			int q = medianPartition(records, left, right);
			Quicksort(records, left, q - 1);
			Quicksort(records, q + 1, right);
		}
	}

	// You may add additional methods here
	private int medianPartition(SortArray records, int p, int r) {
		// (choose the pivot as the median value of the elements at position
		// (left (first),middle,right(last)))
		int length = r-p+1;
		int left = p;			
		int right = r;			
		int middle = (length % 2 == 0)? p + length/2 - 1 : p + (length-1)/2 ; 
		SortingItem lItem= records.getElementAt(left);
		SortingItem rItem= records.getElementAt(right);
		SortingItem mItem= records.getElementAt(middle);
		SortingItem medianItem= null;
		int median = p;
		if (lItem.compareTo(mItem) >= 0 ) {
			if(mItem.compareTo(rItem) >= 0) {
				median = middle;
				medianItem = mItem;
			}else if (lItem.compareTo(rItem) >= 0) {
				median = right;
				medianItem = rItem;
			}else {
				median = left;
				medianItem = lItem;
			}
		}else {
			if (mItem.compareTo(rItem) <= 0) {
				median = middle;
				medianItem = mItem;
			}else if(lItem.compareTo(rItem) >= 0) {
				median = left;
				medianItem = lItem;
			}else {
				median = right;
				medianItem = rItem;
			}
		}
		super.exchange(records, r, median, medianItem);
		return super.partition(records, p, r);
	}
}
