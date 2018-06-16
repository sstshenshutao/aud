package lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


/**
 * The class Navigation finds the shortest (and/or) path between points on a map
 * using the Dijkstra algorithm
 */
public class Navigation {
	/**
	 * Return codes: -1 if the source is not on the map -2 if the destination is
	 * not on the map -3 if both source and destination points are not on the
	 * map -4 if no path can be found between source and destination
	 */

	public static final int SOURCE_NOT_FOUND = -1;
	public static final int DESTINATION_NOT_FOUND = -2;
	public static final int SOURCE_DESTINATION_NOT_FOUND = -3;
	public static final int NO_PATH = -4;
	
	audMap myMap;
	/**
	 * The constructor takes a filename as input, it reads that file and fill
	 * the nodes and edges Lists with corresponding node and edge objects
	 * 
	 * @param filename
	 *            name of the file containing the input map
	 */
	public Navigation(String filename) {
		//TODO Add you code here
		myMap = new AdjazenzlistMap();
		readFiletoMap(filename);

	}
	private void readFiletoMap(String filename) {
		//this I/O Operation is referred from lab 1.
		FileReader fr;
		int a=0; 
		try {
			fr = new FileReader(filename);
			BufferedReader in = new BufferedReader(fr);
			String line;	
			while ((line = in.readLine()) != null) {
				System.out.println(a++);
				AudMapElement l = parselineDigraph(line);
				if(l.eleType==2) {
					Edge e = new AdjazenzlistEdge();
					e.add(l.vFrom, l.vTo);
					this.myMap.addEdge(e);
					this.myMap.addDistance(e, l.distance);
					this.myMap.addSpeedLimit(e, l.speed);
				}else if(l.eleType == 1) {
					Vertex v = new Vertex(l.vFrom);
					this.myMap.addVertex(v);
					this.myMap.addWaitTime(v, l.waitTime);
				}

			}
			in.close();
			fr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private class AudMapElement {
		int eleType; //0:nothing, 1:v , 2:e
		Double distance;
		Double waitTime;
		Double speed;
		String vFrom;
		String vTo;
		/**
		 * @param eleType
		 * @param distance
		 * @param waitTime
		 */
		public AudMapElement(int eleType) {
			this.eleType = eleType;
		}
	}
	private AudMapElement parselineDigraph(String line) {
		int eleType = (line.contains("->"))?2:(line.contains(";"))?1:0;
		AudMapElement ret=null;
		if (eleType == 2) {
			int index = line.indexOf('-');
			System.out.println("->index:"+index);
			String firstV = line.substring(0, index).trim();
			System.out.println("firstV:"+firstV);
			int index2 = line.indexOf('[');
			String secondV = line.substring(index+2, index2).trim();
			System.out.println("decondV:"+secondV);
			index = line.indexOf('"');
			index2 = line.indexOf('"',index+1);
			String [] ds= line.substring(index+1, index2).trim().split(",");
			Double distance =Double.parseDouble(ds[0].trim());
			Double speed =Double.parseDouble(ds[1].trim());
			ret =new AudMapElement(eleType);
			ret.distance= distance;
			ret.speed= speed;
			ret.vFrom=firstV;
			ret.vTo=secondV;
					
		}else if(eleType == 1) {
			int index = line.indexOf('[');
			String firstV = line.substring(0, index).trim();
			index = line.indexOf('"');
			int index2 = line.indexOf('"',index+1);
			String [] vw= line.substring(index+1, index2).trim().split(",");
			Double waitTime= Double.parseDouble(vw[1].trim());
			ret =new AudMapElement(eleType);
			ret.vFrom=firstV;
			ret.waitTime=waitTime;
		}else {
			ret =new AudMapElement(eleType);
		}
		return ret;
	}

	/**
	 * This methods finds the shortest route (distance) between points A and B
	 * on the map given in the constructor.
	 * 
	 * If a route is found the return value is an object of type
	 * ArrayList<String>, where every element is a String representing one line
	 * in the map. The output map is identical to the input map, apart from that
	 * all edges on the shortest route are marked "bold". It is also possible to
	 * output a map where all shortest paths starting in A are marked bold.
	 * 
	 * The order of the edges as they appear in the output may differ from the
	 * input.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destinaton
	 * @return returns a map as described above if A or B is not on the map or
	 *         if there is no path between them the original map is to be
	 *         returned.
	 */
	public ArrayList<String> findShortestRoute(String A, String B) {
		//TODO  Add you code here
		
		return new ArrayList<>(); // dummy, replace
	}

	/**
	 * This methods finds the fastest route (in time) between points A and B on
	 * the map given in the constructor.
	 * 
	 * If a route is found the return value is an object of type
	 * ArrayList<String>, where every element is a String representing one line
	 * in the map. The output map is identical to the input map, apart from that
	 * all edges on the shortest route are marked "bold". It is also possible to
	 * output a map where all shortest paths starting in A are marked bold.
	 * 
	 * The order of the edges as they appear in the output may differ from the
	 * input.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destinaton
	 * @return returns a map as described above if A or B is not on the map or
	 *         if there is no path between them the original map is to be
	 *         returned.
	 */
	public ArrayList<String> findFastestRoute(String A, String B) {
		//TODO Add you code here
		
		return new ArrayList<>(); // dummy, replace
	}

	/**
	 * Finds the shortest distance in kilometers between A and B using the
	 * Dijkstra algorithm.
	 * 
	 * @param A
	 *            the start point A
	 * @param B
	 *            the destination point B
	 * @return the shortest distance in kilometers rounded upwards.
	 *         SOURCE_NOT_FOUND if point A is not on the map
	 *         DESTINATION_NOT_FOUND if point B is not on the map
	 *         SOURCE_DESTINATION_NOT_FOUND if point A and point B are not on
	 *         the map NO_PATH if no path can be found between point A and point
	 *         B
	 */
	public int findShortestDistance(String A, String B) {
		//TODO Add you code here
		int sd = 0; 

		return sd;
	}

	/**
	 * Find the fastest route between A and B using the dijkstra algorithm.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destination
	 * @return the fastest time in minutes rounded upwards. SOURCE_NOT_FOUND if
	 *         point A is not on the map DESTINATION_NOT_FOUND if point B is not
	 *         on the map SOURCE_DESTINATION_NOT_FOUND if point A and point B
	 *         are not on the map NO_PATH if no path can be found between point
	 *         A and point B
	 */
	public int findFastestTime(String pointA, String pointB) {
		//TODO Add you code here
		int ft = 0;

		return ft;
	}
	public void printMyMap(){
		this.myMap.toString();
	}
	public static void main(String[] args) {
		Navigation n = new Navigation("TestFile1");
		n.printMyMap();
	}
}
