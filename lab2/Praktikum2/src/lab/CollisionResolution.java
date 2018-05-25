package lab;

import frame.Entry;

/**
 * @author Shutao Shen
 *
 */
public interface CollisionResolution {



	/**
	 * @param x Entry x
	 * @param h0 from hashFunction
	 * @param i counter
	 * @param k capacity
	 * @return
	 */
	public int getNext(Entry x, int h0, int i, int k);
}
