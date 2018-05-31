/**
 * 
 */
package lab;

import frame.Entry;

/**
 * @author Shutao Shen
 *
 */
public interface HashFunction {

	/**
	 * @param entry x
	 * @param k capacity
	 * @return the address of the h(x)
	 */
	public int getHash(Entry x, int k) ;
}
