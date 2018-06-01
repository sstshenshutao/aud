package lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import frame.Entry;

/*
 * Implements a Hash-Table structure as introduced in the 
 * lecture to store the information read by the RFID 
 * readers in the library.
 *	
 * Make sure that you have tested all the given test cases
 * given on the homepage before you submit your solution.
 *
 */

public class HashTable {
	private Entry[] hashEntry;
	public HashFunction hashFunction;
	public CollisionResolution collisionResolution;
	private int capacity;
	private String[] insertSequence;
	private int actualUsed = 0;

	/**
	 * The constructor
	 * 
	 * @param initialCapacity
	 *            represents the initial size of the Hash Table.
	 * @param hashFunction
	 *            can have the following values: division folding mid_square
	 * @param collisionResolution
	 *            can have the following values: linear_probing quadratic_probing
	 * 
	 *            The Hash-Table itself should be implemented as an array of entries
	 *            (Entry[] in Java) and no other implementation will be accepted.
	 *            When the load factor exceeds 75%, the capacity of the Hash-Table
	 *            should be increased as described in the method rehash below. We
	 *            assume a bucket factor of 1.
	 */
	public HashTable(int k, String hashFunction, String collisionResolution) {
		/**
		 * Add your code here
		 */
//		try {
//			Class<?> hfClazz = Class.forName("lab."+hashFunction.substring(0, 1).toUpperCase()+hashFunction.substring(1));
//			this.hashFunction = (HashFunction) hfClazz.getDeclaredConstructor().newInstance();
//			Class<?> crClazz = Class.forName("lab."+collisionResolution.substring(0, 1).toUpperCase()+collisionResolution.substring(1));
//			this.collisionResolution = (CollisionResolution) crClazz.getDeclaredConstructor().newInstance();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		switch(hashFunction) {
			case "division": this.hashFunction = new Division(); break;
			case "folding": this.hashFunction = new Folding(); break;
			case "mid_square": this.hashFunction = new Mid_square(); break;
		}
		
		switch(collisionResolution) {
			case "linear_probing": this.collisionResolution = new Linear_probing(); break;
			case "quadratic_probing": this.collisionResolution = new Quadratic_probing(); break;
		}
		
		this.hashEntry = new Entry[k]; 
		this.capacity = k;
		initInsertSequence(k);
	}
	private void initInsertSequence(int k) {
		this.insertSequence = new String[k];
	}
	/**
	 * This method takes as input the name of a file containing a sequence of
	 * entries that should be inserted into the Hash-Table in the order they appear
	 * in the file. You cannot make any assumptions on the order of the entries nor
	 * is it allowed to change the order given in the file. You can assume that the
	 * file is located in the same directory as the executable program. The input
	 * file is similar to the input file for lab 1. The return value is the number
	 * of entries successfully inserted into the Hash-Table.
	 * 
	 * @param filename
	 *            name of the file containing the entries
	 * @return returns the number of entries successfully inserted in the
	 *         Hash-Table.
	 */
	public int loadFromFile(String filename) {
		/**
		 * Add your code here
		 */
		//this I/O Operation is referred from lab 1.
		FileReader fr;
		try {
			fr = new FileReader(filename);
			BufferedReader in = new BufferedReader(fr);
			String line;	
			while ((line = in.readLine()) != null) {
				String[] ls = line.split(";");
				insert(new Entry(ls[0], ls[1], ls[2]));
//				//调试用
//				printTree();
			}
			in.close();
			fr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.actualUsed;
	}

	/**
	 * This method inserts the entry insertEntry into the Hash-Table. Note that you
	 * have to deal with collisions if you want to insert an entry into a slot which
	 * is not empty. This method returns true if the insertion of the entry
	 * insertEntry is successful and false if the key of this entry already exists
	 * in the Hash-Table (the existing key/value pair is left unchanged).
	 * 
	 * @param insertEntry
	 *            entry to insert into the Hash-table
	 * @return returns true if the entry insertEntry is successfully inserted false
	 *         if the entry already exists in the Hash-Table
	 */
	public boolean insert(Entry insertEntry) {
		/**
		 * Add your code here
		 */
		
		//if exist:!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		if(Arrays.stream(this.hashEntry).anyMatch(e -> ((e!=null && e.compareTo(insertEntry) == 0)? true : false))) 
			return false;
		
		//insert with/out collisions
		int address = this.hashFunction.getHash(insertEntry, this.capacity);
		
//		System.out.println("insert:" +insertEntry.getKey()+"|address:"+address+ "|"+
//				this.hashFunction.getClass().toString()+"|capacity:"+this.capacity );
		if (this.hashEntry[address] == null || this.hashEntry[address].isDeleted() == true) {
			this.hashEntry[address] = insertEntry;
			actualUsed++;
		}else {
			//collisions!!!
			String record = Integer.toString(address);
			ArrayList<Integer> lastTern = new ArrayList<>();
			lastTern.add(address);
			for(int i=1; true; i++) {
				//use collisionfunction to get the next address.
				int addressc = this.collisionResolution.getNext(insertEntry, address, i, this.capacity);
				
//				System.out.println("insertCollision:" +insertEntry.getKey()+"|address:"+addressc+ "|"+
//						this.collisionResolution.getClass().toString()+"|capacity:"+this.capacity +"|record:"+record );
				
//				if (lastTern.contains(addressc)) continue;
				if (this.hashEntry[addressc] == null || this.hashEntry[addressc].isDeleted() == true) {
					this.hashEntry[addressc] = insertEntry;
					this.insertSequence[addressc] = record; 
					actualUsed++;
					break;
				}else {
					record += "," + addressc;
					lastTern.add(addressc);
				}
			}
		}
		//rehash
		if (this.actualUsed > this.capacity * 0.75) {
			rehash();
		}
		return true;
	}

	/**
	 * This method deletes the entry from the Hash-Table, having deleteKey as key
	 * This method returns the entry, having deleteKey as key if the deletion is
	 * successful and null if the key deleteKey is not found in the Hash-Table.
	 * 
	 * @param deleteKey
	 *            key of the entry to delete from the Hash-Table
	 * @return returns the deleted entry if the deletion ends successfully null if
	 *         the entry is not found in the Hash-Table
	 */
	public Entry delete(String deleteKey) {
		/**
		 * Add your code here
		 */
		Entry del = find(deleteKey);
		del.markDeleted();
//		actualUsed--;
		return del;
	}

	/**
	 * This method searches in the Hash-Table for the entry with key searchKey. It
	 * returns the entry, having searchKey as key if such an entry is found, null
	 * otherwise.
	 * 
	 * @param searchKey
	 *            key of the entry to find in the Hash-table
	 * @return returns the entry having searchKey as key if such an entry exists
	 *         null if the entry is not found in the Hash-Table
	 */
	public Entry find(String searchKey) {
		/**
		 * Add your code here
		 */
		List<?> a= Arrays.stream(this.hashEntry).
			filter((Entry e) -> (e!=null && e.getKey().equals(searchKey) && !e.isDeleted())).
				collect(Collectors.toList())	;
		return (a.size()==0)?null: (Entry)a.get(0);
	}

	/**
	 * This method returns a ArrayList<String> containing the output Hash-Table. The
	 * output should be directly interpretable dot code. Each item in the ArrayList
	 * corresponds to one line of the output Hash-Table. The nodes of the output
	 * Hash-Table should contain the keys of the entries and also the data.
	 * 
	 * @return returns the output Hash-Table in directly interpretable dot code
	 */
	public ArrayList<String> getHashTable() {
		/**
		 * Add your code here
		 */
		ArrayList<String> table= new ArrayList<>();
		table.add(new String("digraph {"));
		table.add(new String("splines=true;"));
		table.add(new String("nodesep=.01;"));
		table.add(new String("rankdir=LR;"));
		table.add(new String("node[fontsize=8,shape=record,height=.1];"));
		String line6= new String("ht[fontsize=12,label=\"");
		for(int i=0; i< this.capacity;i++) {
			line6+= ((i==this.capacity-1)? ("<f"+i+">"+i+"\"];") : ("<f"+i+">"+i+"|"));
		}
		table.add(line6);
		int nodeNum = 1;
		for(int i=0; i< this.capacity;i++) {
			if (this.hashEntry[i] != null && !this.hashEntry[i].isDeleted()) {
				if(this.insertSequence[i]!= null) {
					table.add("node"+ nodeNum +"[label=\"{<l>"+this.hashEntry[i].getKey()+"|"+
							this.hashEntry[i].getData()+"|"+this.insertSequence[i] + "}\"];");
				}else {
					table.add("node"+ nodeNum +"[label=\"{<l>"+this.hashEntry[i].getKey()+"|"+
							this.hashEntry[i].getData() + "}\"];");
				}
				nodeNum++;
			}
		}
		int j=1;
		for(int i=0; i< this.capacity;i++) {
			if (this.hashEntry[i] != null && !this.hashEntry[i].isDeleted()) {
				table.add("ht:f"+i+"->node"+j+":l;");
				j++;
			}
				
		}
		
		table.add(new String("}"));
	
		return table;
	}

	/**
	 * This method increases the capacity of the Hash-Table and reorganizes it, in
	 * order to accommodate and access its entries more efficiently. This method is
	 * called automatically when the load factor exceeds 75%. To increase the size
	 * of the Hash-Table, you multiply the actual capacity by 10 and search for the
	 * closest primary number less than the result of this multiplication. For
	 * example if the actual capacity of the Hash-Table is 101, the capacity will be
	 * increased to 1009, which is the closest primary number less than (101*10).
	 */
	private void rehash() {
		/**
		 * Add your code here
		 */
		int pz = getPzLessthan(this.capacity * 10);
		Entry[] tmp = new Entry[pz];
		String[] tmpSeq = new String[pz];
		Entry[] oldHashEntry = this.hashEntry;
		int oldCapacity=this.capacity;
		this.hashEntry = tmp;
		this.insertSequence = tmpSeq;
		this.capacity= this.hashEntry.length;
		this.actualUsed=0;
		for(int i=0; i<oldCapacity; i++) {
			if (oldHashEntry[i] == null || oldHashEntry[i].isDeleted()) {
			}else {
				insert(oldHashEntry[i]);
			}		
		}
		
	}
	private int getPzLessthan(int a) {
		List<Integer> primzahl = new ArrayList<>();  
		primzahl.add(2);
		for(int i=3; i<a ;i++) {
            int tmp = (int)Math.sqrt(i) + 1;  
            for(int j=2; j<=tmp; j++) {
                if(i%j == 0) 	break; 
                if(j == tmp)		primzahl.add(i);
            }
		}
        return primzahl.get(primzahl.size()-1);
	}
	
	//调试用
	private int tsa=0;
	public void printTree() {
    	FileWriter fw=null;
		try {
			fw = new FileWriter("tstree_"+tsa);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	PrintWriter wr= new PrintWriter(fw);
    	getHashTable().forEach(x->wr.println(x));
    	tsa++;
    	wr.close();
    	try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String commands = "/usr/local/Cellar/graphviz/2.40.1/bin/dot -Tpng -o test"+tsa+".png "+"tstree_"+tsa;
    	try {
			Runtime.getRuntime().exec (commands);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	
	private static ArrayList<String> getTestData() {
		ArrayList<String> testData = new ArrayList<>();
		String keyPrefix = "ABCDEA";
		for (char c1 = 'A'; c1 != 'Z'; c1++) {
			for (char c2 = 'A'; c2 != 'Z'; c2++) {
				for (char c3 = 'A'; c3 != 'C'; c3++) {
					testData.add(keyPrefix + c1 + c2 + c3);
				}
			}
		}
		return testData;
	}
	public static void main(String[] args) {
		
		String testkey = "ABCDEAJQA";
		String KeyAtHomePosition = "ABCDEACLB";
		HashTable table = new HashTable(10, "mid_square", "quadratic_probing");
		List <String >testData=getTestData();
		for (String s : testData) {
			Entry e = new Entry();
			e.setKey(s);
			e.setData("ok");
			table.insert(e);
		}
		ArrayList<String> dot = table.getHashTable();
		dot.forEach(x->System.out.println(x));
//		ABCDEAJQA
		
		
	}

}
