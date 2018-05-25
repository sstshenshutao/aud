/**
 * 
 */
package lab;

import frame.Entry;

/**
 * @author Shutao Shen
 *
 */
public class Linear_probing implements CollisionResolution {

	/**
	 * 
	 */
	public Linear_probing() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see lab.CollisionResolution#getNext(frame.Entry, int, int)
	 */
	@Override
	public int getNext(Entry x, int h0, int i, int k) {
		// TODO Auto-generated method stub
		return (h0+i)%k;
	}

}
