package lab;

import java.util.List;

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
	private List<Entry> key;
	private List<B_TreeNode> c;
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
		return key.get(index);
	}
	/**
	 * @return the c
	 */
	public B_TreeNode getC(int index) {
		return c.get(index);
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
		if (index < n ) {
			this.key.set(index, entry);
		}else {
			for(int i = n; i<index; i++) {
				this.key.add(null);
			}
			this.key.add(entry);
		}
	}
	/**
	 * @param c the c to set
	 */
	public void setC(int index, B_TreeNode c) {
		if (index < n ) {
			this.c.set(index, c);
		}else {
			for(int i = n; i<index; i++) {
				this.c.add(null);
			}
			this.c.add(c);
		}
		
	}
	/**
	 * @param leaf the leaf to set
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
    


}