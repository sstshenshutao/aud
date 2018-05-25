package lab;

/**
 * 
 * This class represents one entry of the list that has to be sorted.
 * 
 */
public class SortingItem {

	// DO NOT modify
	public String BookSerialNumber;
	public String ReaderID;
	public String Status;

	// DO NOT modify
	public SortingItem() {

	}

	// DO NOT modify
	public SortingItem(SortingItem otherItem) {
		this.BookSerialNumber = otherItem.BookSerialNumber;
		this.ReaderID = otherItem.ReaderID;
		this.Status = otherItem.Status;
	}

	// You may add additional methods here
	/** like comparator, compare this SortingItem with another
	 * @param otherItem
	 * @return the comparator of two SortingItems
	 */
	public int compareTo(SortingItem otherItem) {
		int comparator = this.BookSerialNumber.compareTo(otherItem.BookSerialNumber);
		if (comparator == 0) {
			return this.ReaderID.compareTo(otherItem.ReaderID);
		}
		return comparator;
	}
}
