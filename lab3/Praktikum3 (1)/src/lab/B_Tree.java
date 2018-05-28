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
	private int tiaoshi1;
	
	
	
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
				//调试用
				getInorderTraversal().forEach(x->System.out.println(x));;
				
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
    		B_TreeNode r = this.root;
    		if (r.getN() == 2*this.t-1) {
    			B_TreeNode s= new B_TreeNode(t);
    			this.root = s;
    			s.setLeaf(false);
    			s.setN(0);
    			s.setC(0, r);
    			splitChild(s,0);
    			//调试用
    			System.out.println("s.n:"+r.getN());
    			insertNonFull(s,insertEntry);
    		}else {
    			//调试用
    			System.out.println("r.n:"+r.getN());
    			insertNonFull(r,insertEntry);
    		}
	    	System.out.println(tiaoshi1++);
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
	    		//调试用
	    		System.out.println("dasdasdasd:"+i);
	    		System.out.println(x.getN());
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
		for(int j = x.getN()-1; j>i; j--) {
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
    	return new Entry();
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
    	return new Entry();
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
 
    private ArrayList<String> getB_TreeNode(ArrayList<String> nodeline, List <B_TreeNode> node, int index) {
    		String nodeName=	"root";
    		String line = writeNode(node.get(0),nodeName);//write root
	    	nodeline.add(line);
	    	ArrayList<String> relation = new ArrayList<>();
	    	
	    	ArrayList<String> nodeNames= new ArrayList<>();
	    	nodeNames.add(nodeName);
	    	while(node.size()!=0) {
	    		if(node.get(0).isLeaf()) break;
	    		ArrayList<B_TreeNode> tmpnode= new ArrayList<>();
	    		ArrayList<String> tmpNames = new ArrayList<>();
	    		for(int j=0; j<node.size();j++) {
	    			nodeName = nodeNames.get(j);
	    			for (int i=0; i<=node.get(j).getN();i++) {
	    				if(!node.get(j).getC(i).isLeaf()) {
	    					tmpnode.add(node.get(j).getC(i));
	    					tmpNames.add("node"+String.valueOf(index));}
			    		relation.add(nodeName+":f"+2*i+"->node"+index+";");
			    		nodeline.add(writeNode(node.get(j).getC(i), "node"+String.valueOf(index)));
			    		index++;
			    	}
	    		}
	    		node = tmpnode;
	    		nodeNames= tmpNames;
	    	}
    	return relation;
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
    List<B_TreeNode> nodelist = new ArrayList<>();
    nodelist.add(this.root);
    ArrayList<String> relation =getB_TreeNode( ret, nodelist, 2);
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
    	return 0;
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
    	return 0;
    }
    
    
    
    public static void main(String[] args) {
    		B_Tree b = new B_Tree(2);
		b.constructB_TreeFromFile("testmy.txt");
		ArrayList<String> out = b.getB_Tree();
		out.forEach(x->System.out.println(x));
	}
}