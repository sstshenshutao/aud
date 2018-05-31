/**
 * 
 */
package lab;

import java.math.BigInteger;
import frame.Entry;

/**
 * @author Shutao Shen
 *
 */
public class Division implements HashFunction {

	/**
	 * 
	 */
	public Division() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see lab.HashFunction#getHash(frame.Entry)
	 */
	@Override
	public int getHash(Entry x, int k) {
		char[] c = x.getKey().substring(0, 5).toCharArray();
		String val = "";
		for(int i=0; i<c.length; i++) {
			val+=Integer.toString((int)c[i]);
		}
		BigInteger tmpInt = new BigInteger(val);
		return tmpInt.remainder(new BigInteger(Integer.toString(k))).intValue();
	}

}
