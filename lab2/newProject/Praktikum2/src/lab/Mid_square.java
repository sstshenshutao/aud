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
public class Mid_square implements HashFunction {

	/**
	 * 
	 */
	public Mid_square() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see lab.HashFunction#getHash(frame.Entry, int)
	 */
	@Override
	public int getHash(Entry x, int k) {
		char[] c = x.getKey().substring(0, 5).toCharArray();
		String val = "";
		for(int i=0; i<c.length; i++) {
			val+=Integer.toString((int)c[i]);
		} 
		BigInteger tmpInt = new BigInteger(val);
		tmpInt = tmpInt.pow(2);
		String n = tmpInt.toString();
		int length = getlength(k-1);
		int ret = Integer.parseInt(n.substring(n.length()-9-length, n.length()-9));
		return (ret>=k)? ret%k : ret;
	}
	private int getlength(int k) {
		int count = 0;
		int num = k;
		while(num > 0){
			num=num / 10;
			count++;
		}
		return count;
	}
}
