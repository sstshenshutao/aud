package lab;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import frame.*;

/*
 * Implements a B-Tree structure as introduced in the 
 * lecture to store the information read by the RFID 
 * readers in the library.
 *
 * Make sure that you have tested all the given test cases
 * given on the homepage before you submit your solution.
 *
 */



public class B_Tree {
	private B_TreeNode tree;
	private B_TreeNode root;
	private int t;
	
	//--------------调试用---------------------
	
	
	
	//--------------调试用---------------------
    /**
	* The constructor
	* 
	* @param t minimum degree of the B-tree.
      * 			t can not be changed once a new B Tree object is created.
      *
	*/

    public B_Tree(int t) {
        /**
         * Add your code here
    	   */
    		this.t = t;
    		init(t);
    }

    private void init(int t) {
    		this.tree = new B_TreeNode(t);
    		this.root = this.tree;
    }
    /**
	 * This method takes as input the name of a file containing a sequence of
       * entries that should be inserted to the B-Tree in the order they appear in
       * the file. You cannot make any assumptions on the order of the entries nor
       * is it allowed to change the order given in the file. You can assume that the
       * file is located in the same directory as the executable program. The input
       * file is similar to the input file for lab 1. The return value is the number of
       * entries successfully inserted in the B-Tree.
	 * 
	 * @param filename name of the file containing the entries
	 * @return returns the number of entries successfully inserted in the B-Tree.
	 */

    
    public int constructB_TreeFromFile (String filename) {
        /**
         * Add your code here
    	   */
    		//this I/O Operation is referred from lab 1.
		FileReader fr;
		int insertSucc = 0;
		try {
			fr = new FileReader(filename);
			BufferedReader in = new BufferedReader(fr);
			String line;	
			while ((line = in.readLine()) != null) {
				String[] ls = line.split(";");
				if(insert(new Entry(ls[0], ls[1], ls[2]))) insertSucc++;
				
			}
			in.close();
			fr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return insertSucc;
    }
    
    /**
	 * This method inserts the entry insertEntry into the B-Tree. Note that you
       * have to deal with overflows if you want to insert an entry into a leaf which
       * already contains 2t - 1 entries. This method returns true if the insertion
       * of the entry insertEntry is successful and false if the key of this entry
       * already exists in the B-Tree.
       *
	 * @param insertEntry entry to insert into the B-Tree
	 * @return returns true if the entry insertEntry is successfully inserted
       *                 false if the entry already exists in the B-Tree
	 */

    
    public boolean insert(Entry insertEntry) {
        /**
         * Add your code here
    	   */
    		if (getInorderTraversal().stream().anyMatch(x->(x.compareTo(insertEntry)==0))) return false;
    		B_TreeNode r = this.root;
    		if (r.getN() == 2*this.t-1) {
    			B_TreeNode s= new B_TreeNode(t);
    			this.root = s;
    			s.setLeaf(false);
    			s.setN(0);
    			s.setC(0, r);
    			splitChild(s,0);
    			insertNonFull(s,insertEntry);
    		}else {
    			insertNonFull(r,insertEntry);
    		}
    		return true;
    }
    
    private void insertNonFull(B_TreeNode x, Entry k) {
		// TODO Auto-generated method stub
	    	int i = x.getN()-1;
	    	if (x.isLeaf()) {
	    		while(i>=0 && k.compareTo(x.getKey(i))<0) {
	    			x.setKey(i+1, x.getKey(i));
	    			i--;
	    		}
	    		x.setKey(i+1, k);
	    		x.setN(x.getN()+1);
	    	}else {
	    		while(i>=0 && k.compareTo(x.getKey(i))<0) {
	    			i--;
	    		}
	    		i++;
	    		if(x.getC(i).getN()==2*this.t-1) {
	    			splitChild(x, i);
	    			if(k.compareTo(x.getKey(i))>0) {
	    				i++;
	    			}
	    		}
	    		insertNonFull(x.getC(i), k);
	    	}
		
	}

	private void splitChild(B_TreeNode x, int i) {
		// TODO Auto-generated method stub
		B_TreeNode z= new B_TreeNode(t);
		B_TreeNode y= x.getC(i);
		z.setLeaf(y.isLeaf());
		z.setN(t-1);
		for(int j=0; j<t-1; j++) {
			z.setKey(j, y.getKey(j+t));
		}
		if(!y.isLeaf()) {
			for(int j=0;j<t;j++) {
				z.setC(j, y.getC(j+t));
			}
		}
		y.setN(t-1);
		for(int j = x.getN(); j>i; j--) {
			x.setC(j+1,x.getC(j));
		}
		x.setC(i+1, z);
		for (int j=x.getN()-1;j>i-1;j--) {
			x.setKey(j+1, x.getKey(j));
		}
		x.setKey(i, y.getKey(t-1));
		x.setN(x.getN() +1);
	}

	/**
	 * This method deletes the entry from the B-Tree structure, having deleteKey
       * as key. In this method you have to distinguish between two cases:
       *     1. The entry, having deleteKey as key, is located in an internal node.
       *     2. The entry, having deleteKey as key, is located in a leaf.
       * This method returns the entry, having deleteKey as key if the deletion is
       * successful and null if the key deleteKey is not found in any entry of the
       * B-Tree.
       *
	 * @param deleteKey key of the entry to delete from the B-Tree
	 * @return returns the deleted entry if the deletion ends successfully       
       *                 null if the entry is not found in the B-Tree
	 */
    
       
    public Entry delete(String deleteKey) {
        /**
         * Add your code here
    	   */ 
    		return deleteNode(this.root, deleteKey);
    }
    private Entry deleteNode(B_TreeNode x, String key) {
    		if(!this.getInorderTraversal().stream().anyMatch(s->s.getKey().equals(key))) return null; 
    		if(contain(x, key)) {
    			//1.2.
    			if(x.isLeaf()) {
    				//1.
    				//delete key from x
    				int ki=0;
    				while(ki<x.getN() && x.getKey(ki).getKey().compareTo(key)!=0) {
    					ki++;
    				}
    				Entry delEntry = x.getKey(ki);
    				while(ki<x.getN()-1) {
    					x.setKey(ki, x.getKey(ki+1));
    				}
    				x.setN(x.getN()-1);
    				//delete finished
    				return delEntry;
    			}else {
    				int ci2=0;
    				while(ci2<x.getN() && x.getKey(ci2).getKey().compareTo(key)!=0) {
    					ci2++;
    				}
    				B_TreeNode prev = x.getC(ci2);
    				B_TreeNode succ = x.getC(ci2+1);		
    				Entry delEntry=x.getKey(ci2);
    				//2.a
    				if(prev.getN()>=t) {
    					x.setKey(ci2, prev.getKey(prev.getN()-1));
    					deleteNode(prev, prev.getKey(prev.getN()-1).getKey());
    					return delEntry;
    				}
    				//2.b
    				else if(succ.getN()>=t) {
    					x.setKey(ci2, succ.getKey(0));
    					deleteNode(succ, succ.getKey(0).getKey());
    					return delEntry;
    				}
    				//2.c
    				else {
    					Entry ki = x.getKey(ci2);
    					//key forward
    					for(int i=ci2; i<x.getN()-1;i++) {
    						x.setKey(i, x.getKey(i+1));
    					}
    					//c forward
    					for(int i=ci2+1; i<x.getN();i++) {
    						x.setC(i, x.getC(i+1));
    					}
    					//x. n-1
    					x.setN(x.getN()-1);
    					//(ki und succ) into prev 
    					prev.setKey(t-1, ki);
    					for(int i=0;i<t-1;i++) {
    						prev.setKey(t+i, succ.getKey(i));
    						prev.setC(t+i, succ.getC(i));
    					}
    					prev.setC(2*t-1, succ.getC(t-1));
    					//prev n ->2t-1
    					prev.setN(2*t-1);
    					//x.ci2 -> prev
    					x.setC(ci2, prev);
    					return deleteNode(prev, key);
    				}
    			}
    		}else {
    			//3
    			int ci3=0;
			while(ci3<x.getN() && x.getKey(ci3).getKey().compareTo(key)<0) {
				ci3++;
			}
			B_TreeNode ckNode = x.getC(ci3);
			if(ckNode.getN()==t-1) {
				//need 3a 3b
				//3a
				int[] brothers =  getBrothers(ci3, x.getN());
				boolean fall3a=false;
				for(int i: brothers) {
					if(x.getC(i).getN()>=t) {
						fall3a = true;
						if (i>ci3) {
							//succ first ele
							rightRotate(ci3,x);
						}else {
							//prev last ele
							leftRotate(ci3, x);
						}
						if(fall3a) break;
					}
				}
				ckNode = x.getC(ci3);
				//3b
				if (!fall3a) {
					if(ci3==0) {
						//zuo
						rmerge(ci3,x);	
						ckNode = x.getC(ci3);
					}else {
						merge(ci3,x);		
						ckNode = x.getC(ci3-1);
					}
					
				}
				//cknode -> new passed node 
				
			}
			return deleteNode(ckNode,key);
    		}
    		
    		
    }
    private void rmerge(int ci3, B_TreeNode x) {
		// TODO Auto-generated method stub
		B_TreeNode left= x.getC(ci3);
		B_TreeNode right= x.getC(ci3+1);
			Entry kNode =x.getKey(ci3);
		//xkey forward
		for(int i=ci3; i<x.getN()-1;i++) {
			x.setKey(i, x.getKey(i+1));
		}
		//xc forward
		for(int i=ci3+1; i<x.getN();i++) {
			x.setC(i, x.getC(i+1));
		}
		//x. n-1
		x.setN(x.getN()-1);
		//left und right and knode merge
		left.setKey(t-1, kNode);
		for(int i=0;i<t-1;i++) {
			left.setKey(t+i, right.getKey(i));
			left.setC(t+i, right.getC(i));
		}
		left.setC(2*t-1, right.getC(t-1));
		//-------
		x.setC(ci3, left);
	}
	private void merge(int ci3, B_TreeNode x) {
		// TODO Auto-generated method stub
		B_TreeNode left= x.getC(ci3-1);
		B_TreeNode right= x.getC(ci3);
			Entry kNode =x.getKey(ci3-1);
		//xkey forward
		for(int i=ci3-1; i<x.getN()-1;i++) {
			x.setKey(i, x.getKey(i+1));
		}
		//xc forward
		for(int i=ci3; i<x.getN();i++) {
			x.setC(i, x.getC(i+1));
		}
		//x. n-1
		x.setN(x.getN()-1);
		//left und right and knode merge
		left.setKey(t-1, kNode);
		for(int i=0;i<t-1;i++) {
			left.setKey(t+i, right.getKey(i));
			left.setC(t+i, right.getC(i));
		}
		left.setC(2*t-1, right.getC(t-1));
		//-------
		x.setC(ci3-1, left);
	}

	private void rightRotate(int ci3, B_TreeNode x) {
		// TODO Auto-generated method stub
		B_TreeNode left = x.getC(ci3);
		B_TreeNode right =x.getC(ci3+1);
		Entry y = x.getKey(ci3); //y
		Entry firstKeyRight= right.getKey(0); // x
		//left:
		left.setKey(left.getN(), y);
		left.setC(left.getN() + 1, right.getC(0));
		left.setN(left.getN()+1);
		//middle:
		x.setKey(ci3, firstKeyRight);
		//right:
		for(int i=0;i<right.getN()-1;i++) {
			right.setKey(i, right.getKey(i+1));
			right.setC(i, right.getC(i+1));
		}
		right.setC(right.getN()-1, right.getC(right.getN()));
		right.setN(right.getN()-1);
	}
	private void leftRotate(int ci3, B_TreeNode x) {
		// TODO Auto-generated method stub
		B_TreeNode left = x.getC(ci3-1);
		B_TreeNode right =x.getC(ci3);
		Entry y = x.getKey(ci3-1); //y
		Entry lastKeyLeft= left.getKey(left.getN()-1); // x
		//right:
		for(int i=0;i<right.getN()-1;i++) {
			right.setKey(i+1, right.getKey(i));
			right.setC(i+1, right.getC(i));
		}
		right.setC(right.getN(), right.getC(right.getN()-1));
		right.setKey(0, y);
		right.setC(0, left.getC(left.getN()));
		right.setN(right.getN()+1);
		//middle:
		x.setKey(ci3-1, lastKeyLeft);
		//left:
		left.setN(left.getN()-1);
	}

	private int[] getBrothers(int index, int max){
		if (index==0) {
			return new int[] {1};}
		else if (index== max-1) {
			int[] a= new int[1];
			a[0]=max-2;
			return  a;}
		else {
			int[] a= new int[2];
			a[0]=index-1;
			a[1]=index+1;
			return a;
		}
		
	}
    private boolean contain(B_TreeNode node, String key) {
    		boolean ret =false;
    		for(int i=0; i<node.getN();i++)
    		{
    			if (node.getKey(i).getKey().compareTo(key)==0) {
    				ret = true;
    			}
    		}
    		return ret;
    }
    /**
	 * This method searches in the B-Tree for the entry with key searchKey. It
       * returns the entry, having searchKey as key if such an entry is found, null
       * otherwise.
       *
	 * @param searchKey key of the entry to find in the B-Tree
	 * @return returns the entry having searchKey as key if such an entry exists
       *                 null if the entry is not found in the B-Tree
	 */

    
    public Entry find(String searchKey) {
        /**
         * Add your code here
    	   */
    	return searchBnode(this.root, searchKey);
    }
    private Entry searchBnode(B_TreeNode node, String key) {
    		int i=0;
    		while(i<node.getN() && key.compareTo(node.getKey(i).getKey())>0) {
    			i++;
    		}
    		if (i<node.getN() && key.compareTo(node.getKey(i).getKey())==0) {
    			return node.getKey(i);
    		}else if (node.isLeaf()) {
    			return null;
    		}else {
    			return searchBnode(node.getC(i),key);
    		}
    	
    }
    
    
    private String writeNode(B_TreeNode node, String nodeName) {
	    	String linen= new String(nodeName+"[label=\"");
	    	for (int i=0; i<node.getN();i++) {
	    		linen+= ("<f"+(i*2)+">*"+
	    				"|<f"+(i*2+1)+">" + node.getKey(i).getKey()+"|");
	    	}
	    	linen+=("<f"+(node.getN()*2)+">*\"];");
	    return linen;
    }
    private int index=0;
    private ArrayList<String> getB_TreeNode(ArrayList<String> a, B_TreeNode node) {
		String nodeName;	
		if (index ==1 ) {
			nodeName="root";}
		else {
			nodeName="node"+String.valueOf(index);
		}
		a.add(writeNode(node, nodeName));
		ArrayList<String> r= new ArrayList<>();
		ArrayList<String> k= new ArrayList<>();
		index++;
		if(!node.isLeaf()) {
			for(int i=0;i<=node.getN();i++) {
				r.add(nodeName+":f"+2*i+"->node"+index+";");
				k.addAll(getB_TreeNode(a,node.getC(i)));
			}
			r.addAll(k);
		}
    		return r;
    	
    	
//    	String nodeName=	"root";
//    		String line = writeNode(node.get(0),nodeName);//write root
//	    	nodeline.add(line);
//	    	ArrayList<String> relation = new ArrayList<>();
//	    	
//	    	ArrayList<String> nodeNames= new ArrayList<>();
//	    	nodeNames.add(nodeName);
//	    	while(node.size()!=0) {
//	    		if(node.get(0).isLeaf()) break;
//	    		ArrayList<B_TreeNode> tmpnode= new ArrayList<>();
//	    		ArrayList<String> tmpNames = new ArrayList<>();
//	    		for(int j=0; j<node.size();j++) {
//	    			nodeName = nodeNames.get(j);
//	    			for (int i=0; i<=node.get(j).getN();i++) {
//	    				if(!node.get(j).getC(i).isLeaf()) {
//	    					tmpnode.add(node.get(j).getC(i));
//	    					tmpNames.add("node"+String.valueOf(index));}
//			    		relation.add(nodeName+":f"+2*i+"->node"+index+";");
//			    		nodeline.add(writeNode(node.get(j).getC(i), "node"+String.valueOf(index)));
//			    		index++;
//			    	}
//	    		}
//	    		node = tmpnode;
//	    		nodeNames= tmpNames;
//	    	}
//    	return relation;
    }
    /**
	 * This method returns a ArrayList<String> containing the output B-Tree.
       * The output should be directly interpretable dot code.
       * Each item in the ArrayList corresponds to one line of the output
       * tree. The nodes of the output tree should only
       * contain the keys of the entries and not the data.
       *
	 * @return returns the output B-Tree in directly interpretable dot code
	 */
        
    public ArrayList<String> getB_Tree() {
        /**
         * Add your code here
    	   */
    	ArrayList<String> ret = new ArrayList<>();
    	ret.add("digraph{");
    	ret.add("node[shape=record];");
    this.index=1;
    ArrayList<String> relation =getB_TreeNode( ret, this.root);
    	ret.addAll(relation);
    ret.add("}");
    	return ret;
    }

    /**
	 * This method returns the height of the B-Tree
       * If the B-Tree is empty this method should return 0.
       *
	 * @return returns the height of the B-Tree
	 */


    public int getB_TreeHeight() {
        /**
         * Add your code here
    	   */
    		B_TreeNode node = this.root;
    		int h=0;
    		while(!node.isLeaf()) {
    			node = node.getC(0);
    			h++;
    		}
    		return h;
    }

    /**
	 * This method traverses the B-Tree in inorder and adds each entry to a
       * ArrayList<Entry>. The returned ArrayList contains the entries of the B-Tree
       * in ascending order.
       *
	 * @return returns the entries stored in the B-Tree in ascending order
	 */

    
    public ArrayList<Entry> getInorderTraversal() {
        /**
         * Add your code here
    	   */
    		
    		return walk(this.root);
    }

    private ArrayList<Entry> walk(B_TreeNode node){
	    	if	(node.isLeaf()) {
	    		ArrayList<Entry> ret = new ArrayList<>();
	    		for(int i=0; i<node.getN();i++) {
				ret.add(node.getKey(i));
			}
	    		return ret;
	    	}else {
	    		ArrayList<Entry> ret = new ArrayList<>();
	    		for(int i=0; i<node.getN();i++) {
	    			ret.addAll(walk(node.getC(i)));
				ret.add(node.getKey(i));
			}
	    		ret.addAll(walk(node.getC(node.getN())));
	    		return ret;
	    	}
    }
    /**
	 * This method returns the number of entries in the B-Tree (not the number
       * of nodes).
       *
       *
	 * @return returns the size of the B-Tree, i.e., the number of entries stored in the B-Tree
	 */

    
    public int getB_TreeSize() {
        /**
         * Add your code here
    	   */
    	return getsize(this.root);
    }
    private int getsize(B_TreeNode node) {
    		if(node.isLeaf()) {
    			return node.getN();
    		}else {
    			int a=0;
    			for(int i=0;i<=node.getN();i++) {
    				a+=getsize(node.getC(i));
    			}
    			a+=node.getN();
    			return a;
    		}
    }
    
    
    public static void main(String[] args) {
    		B_Tree b = new B_Tree(2);
		b.constructB_TreeFromFile("TestFile1.txt");
		b.getInorderTraversal().forEach(x->System.out.println(x));
//		b.delete("L2Z7499YH");
//		b.delete("FMF1QTZ0Q");
//		b.delete("L2Z74TZ0Q");
		b.getB_Tree().forEach(x->System.out.println(x));;
	}
}