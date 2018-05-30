package lab;

import java.util.ArrayList;
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
    		key = new ArrayList<>();
    		c = new ArrayList<>();
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
		return this.key.get(index);
	}
	/**
	 * @return the keys
	 */
	public List<Entry> getAllKey() {
		return this.key;
	}
	/**
	 * @return the keys
	 */
	public List<B_TreeNode> getAllc() {
		return this.c;
	}
	/**
	 * @return the c
	 */
	public B_TreeNode getC(int index) {
		return this.c.get(index);
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
			if (index<this.key.size()) {
				this.key.set(index, entry);
			}else if(index==this.key.size()){
				this.key.add(entry);
			}else {
				System.out.println("setkey index wrong!!!");
			}
	}
	/**
	 * @param c the c to set
	 */
	public void setC(int index, B_TreeNode c) {
		if (index<this.c.size()) {
			this.c.set(index, c);
		}else if(index== this.c.size()) {
			this.c.add(c);
		}else {
			System.out.println("setC index wrong!!!");
		}
	}
		
	/**
	 * @param leaf the leaf to set
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
    


}