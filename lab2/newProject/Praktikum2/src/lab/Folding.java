/**
 * 
 */
package lab;

import frame.Entry;

/**
 * @author Shutao Shen
 *
 */
public class Folding implements HashFunction {

	/**
	 * 
	 */
	public Folding() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see lab.HashFunction#getHash(frame.Entry, int)
	 */
	@Override
	public int getHash(Entry x, int k) {
		//k=100
		int length = getlength(k-1);
		//length=2
		char[] c = x.getKey().substring(0, 5).toCharArray();
		String s="";
		for (int i=0; i<c.length;i++ ) {
			s+=Integer.toString((int)c[i]);
		}
		if (s.length() % length != 0) {
			String zero="";
			for(int i=0;i<length - (s.length() % length);i++) {
				zero+="0";
			}
			s = zero+s;
		}
		//split
//		System.out.print(x.getKey()+"|AScii:"+s);
		int num1=0;
		for(int i=s.length()/length; i>0; i--) {
//			System.out.print("|"+((i%2==(s.length()/length)%2)?
//					new StringBuilder(s.substring(length*(i-1), length*i)).reverse().toString():
//						s.substring(length*(i-1), length*i)));
			
			num1+=Integer.parseInt((i%2==(s.length()/length)%2)?
					new StringBuilder(s.substring(length*(i-1), length*i)).reverse().toString():
						s.substring(length*(i-1), length*i));
		}
		
		int xxx = 10;
		for(int i=1;i<length;i++) {
			xxx*=10;
		}
		
		if (getlength(num1)>length) {
			
			num1 = num1 % xxx;
		}
//		System.out.print("|"+xxx);
//		System.out.print("\n");
		return (num1>=k)? num1%k : num1;
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
//	006 639 850 059 100 503 311 236
//	043 121 245 101 859 643 161
//	161+643+958+101+542+121+340 866
//	632+311+305+100+950+850+936+6 090
}