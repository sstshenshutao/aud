/**
 * 
 */
package lab;

import frame.Entry;

/**
 * @author Shutao Shen
 *
 */
public class Quadratic_probing implements CollisionResolution {

	/**
	 * 
	 */
	public Quadratic_probing() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see lab.CollisionResolution#getNext(frame.Entry, int, int, int)
	 */
	@Override
	public int getNext(Entry x, int h0, int i, int k) {
		// TODO Auto-generated method stub
		int ret = ((int)(h0 - (Math.ceil(Math.pow(i/2.0, 2))) * Math.pow(-1, i))) % k;
		return (ret<0)? ret+k : ret ;
	}
	
//	public static void main(String[] args) {
//		for(int i=0;i<10;i++) {
//			int ret = ((int)(5 - (Math.ceil(Math.pow((double)i/2, 2))) * Math.pow(-1, i))) % 10;
//			System.out.println((ret<0)? ret+10 : ret);  
//		}
//	}

}
