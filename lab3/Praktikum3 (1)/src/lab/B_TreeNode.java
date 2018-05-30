package lab;

import frame.Entry;

/*
 * Implements a node of a B-Tree
 *
 * Make sure that you have tested all the given test cases
 * given on the homepage before you submit your solution.
 *
 */



public class B_TreeNode {
	private int n;
	private Entry[] key;
	private B_TreeNode[] c;
	private boolean leaf;
    /**
	* The constructor
	* 
	* @param t minimum degree of the B-tree
      *
	*/

    public B_TreeNode(int t) {
        /**
         * Add your code here
    	   */
    		key = new Entry[2*t-1];
    		c = new B_TreeNode[2*t];
    		n = 0;
    		leaf = true;
    }
	/**
	 * @return the n
	 */
	public int getN() {
		return n;
	}
	/**
	 * @return the key
	 */
	public Entry getKey(int index) {
		return key[index];
	}
	/**
	 * @return the c
	 */
	public B_TreeNode getC(int index) {
		return c[index];
	}
	/**
	 * @return the leaf
	 */
	public boolean isLeaf() {
		return leaf;
	}
	/**
	 * @param n the n to set
	 */
	public void setN(int n) {
		this.n = n;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(int index, Entry entry) {
			this.key[index]= entry;
	}
	/**
	 * @param c the c to set
	 */
	public void setC(int index, B_TreeNode c) {
		
			this.c[index]= c;
	}
		
	/**
	 * @param leaf the leaf to set
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
    


}