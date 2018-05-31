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
//		UGWAQDPJJ   8571876581   
		BigInteger tmpInt = new BigInteger(val);
		tmpInt = tmpInt.pow(2);
		String n = tmpInt.toString();
		int length = getlength(k-1);
//		System.out.println(n.substring(n.length()-9-length, n.length()-9));
		int ret = Integer.parseInt(n.substring(n.length()-9-length, n.length()-9));
//		System.out.println(k);
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
//	7347706 811 9 896 249 561
	public static void main(String[] args) {
		Mid_square a = new  Mid_square();
		System.out.println(a.getHash(new Entry("ABCDE","AJQA","OK"), 9661));
	}
}
