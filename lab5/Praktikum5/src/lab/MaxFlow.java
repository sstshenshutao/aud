package lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * MaxFlow.java
 */

public class MaxFlow {
	private FlowMap map;
	private FlowMap rMap;

	/**
	 * Return codes: -1 no source on the map -2 no destination on the map -3 if both
	 * source and destination points are not on the map -4 if no path can be found
	 * between source and destination MAXINT if sources identical to destinations
	 */
	public static final int NO_SOURCE_FOUND = -1;
	public static final int NO_DESTINATION_FOUND = -2;
	public static final int NO_SOURCE_DESTINATION_FOUND = -3;
	public static final int NO_PATH = -4;
	public static final int SOURCES_SAME_AS_DESTINATIONS = Integer.MAX_VALUE;

	/**
	 * The constructor, setting the name of the file to parse.
	 * 
	 * @param filename
	 *            the absolute or relative path and filename of the file
	 */
	public MaxFlow(final String filename) {
		// TODO Add you code here
		readFiletoMap(filename);
	}

	private void readFiletoMap(String filename) {
		// this I/O Operation is referred from lab 1.
		FileReader fr;
		// int a=0;
		try {
			fr = new FileReader(filename);
			BufferedReader in = new BufferedReader(fr);
			String line;
			this.map = new FlowMap(new Flow());
			while ((line = in.readLine()) != null) {
				// System.out.println(a++);
				AudMapElement l = parselineDigraph(line);
				if (l.eleType == 2) {
					Edge e = new Edge(l.vFrom, l.vTo);
					this.map.addEdge(e);
					this.map.addVertex(e.getFrom());
					this.map.addVertex(e.getTo());
					this.map.setC(e, l.c);
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
		int eleType; // 0:nothing, 1:v , 2:e
		Integer c;
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
		int eleType = (line.contains("->")) ? 2 : (line.contains(";")) ? 1 : 0;
		AudMapElement ret = null;
		if (eleType == 2) {
			int index = line.indexOf('-');
			// System.out.println("->index:"+index);
			String firstV = line.substring(0, index).trim();
			// System.out.println("firstV:"+firstV);
			int index2 = line.indexOf('[');
			String secondV = line.substring(index + 2, index2).trim();
			// System.out.println("decondV:"+secondV);
			index = line.indexOf('"');
			index2 = line.indexOf('"', index + 1);
			Integer c = Integer.parseInt(line.substring(index + 1, index2).trim());
			ret = new AudMapElement(eleType);
			ret.c = c;
			ret.vFrom = firstV;
			ret.vTo = secondV;
		} else {
			ret = new AudMapElement(eleType);
		}
		return ret;
	}

	/**
	 * Calculates the maximum number of cars able to travel the graph specified in
	 * filename.
	 *
	 * @param sources
	 *            a list of all source nodes
	 * @param destinations
	 *            a list of all destination nodes
	 * @return the maximum number of cars able to travel the graph, NO_SOURCE_FOUND
	 *         if no source is on the map NO_DESTINATION_FOUND if no destination is
	 *         on the map NO_SOURCE_DESTINATION_FOUND if both - no source and no
	 *         destination - are not on the map NO_PATH if no path can be found
	 *         SOURCES_SAME_AS_DESTINATIONS if sources == destinations
	 */
	public final int findMaxFlow(final String[] sources, final String[] destinations) {
		// TODO Add you code here
		FlowMapDecorator decoratedMap = new FlowMapDecorator(this.map, sources, destinations);
		return fordFulkerson(decoratedMap, decoratedMap.getSuperS(), decoratedMap.getSuperD());
	}

	private ArrayList<Edge> cutSuperNode(ArrayList<Edge> path) {
		if (path == null)
			return path;
		if (path.get(0).getTo().equals(new Vertex("SuperD"))) {
			// cut destination
			path.remove(0);
		}
		if (path.get(path.size() - 1).getFrom().equals(new Vertex("SuperS"))) {
			// cut destination
			path.remove(path.size() - 1);
		}
		return path;
	}


	private int fordFulkerson(FlowMapDecorator decoratedMap, Vertex s, Vertex t) {
		// init f
		int stream = 0;
		for (Edge e : this.map.getEdges()) {
			this.map.setF(e, 0);
		}
		ArrayList<Edge> p = null;
		while ((p = cutSuperNode(bfs(getRMap(decoratedMap), s, t))) != null) {
			p.stream().forEach(x -> System.out.println(x));
			Integer cp = (int) p.stream().mapToDouble(
					x -> this.map.getEdges().contains(x) ? this.map.getC(x) -this.map.getF(x) : this.map.getC(x.getTo(), x.getFrom()))
					.min().getAsDouble();
			System.out.println("cp:" + cp);
			stream += cp;
			for (Edge e : p) {
				if (this.map.getEdges().contains(e)) {
					this.map.setF(e, this.map.getF(e) + cp);
				} else {
					this.map.setF(e.getTo(), e.getFrom(), this.map.getF(e.getTo(), e.getFrom()) - cp);
				}
			}
		}
		return stream;
	}

	private FlowMap getRMap(FlowMapDecorator decoratedMap) {
		// get the residual network
		this.rMap = new FlowMap(new Flow());
		for (Edge e : decoratedMap.getEdges()) {
			if (decoratedMap.getF(e) < decoratedMap.getC(e)) {
				rMap.addEdge(e);
				rMap.addVertex(e.getFrom());
				rMap.addVertex(e.getTo());
			}
			if (decoratedMap.getF(e) > 0) {
				rMap.addEdge(new Edge(e.getTo(), e.getFrom()));
				rMap.addVertex(e.getFrom());
				rMap.addVertex(e.getTo());
			}
		}
		// System.out.println("---------------dotcode--------------");
		// toDotCode(rMap,decoratedMap).forEach(x->System.out.println(x));;
		// System.out.println("---------------end dotcode--------------");
		return this.rMap;
	}

	private ArrayList<String> toDotCode(FlowMap oMap) {
		ArrayList<String> ret = new ArrayList<>();
		ret.add("digraph {");
		for (Edge e : oMap.getEdges()) {
			StringBuffer sb = new StringBuffer();
			sb.append(e.getFrom().getName());
			sb.append(" -> ");
			sb.append(e.getTo().getName());
			sb.append(" [label=\"");
			sb.append(oMap.getC(e));
			sb.append("-");
			sb.append(oMap.getF(e));
			sb.append("\"]");

			sb.append((oMap.getC(e) > oMap.getF(e)) ? "[style=bold];" : ";");
			ret.add(sb.toString());
		}
		ret.add("}");
		return ret;
	}

	private final String white = "white";
	private final String gray = "gray";
	private final String black = "black";

	private ArrayList<Edge> bfs(FlowMap rMap, Vertex s, Vertex t) {
		// TODO Auto-generated method stub
		ArrayList<Edge> ret = null;
		// bfs
		for (Vertex v : rMap.getVertices()) {
			v.setColor(white);
			v.setD(Double.MAX_VALUE);
			v.setPi(null);
		}
		rMap.getVertice(s).setColor(gray);
		rMap.getVertice(s).setD(0.0);
		rMap.getVertice(s).setPi(null);
		ArrayList<Vertex> q = new ArrayList<>();
		q.add(rMap.getVertice(s));
		while (!q.isEmpty()) {
			Vertex u = q.remove(q.size() - 1);
			if (rMap.getAdj(u) == null)
				continue;
			for (Vertex v : rMap.getAdj(u)) {
				if (v.equals(t)) {
					v.setPi(u);
					ret = new ArrayList<>();
					while (!v.equals(s)) {
						ret.add(new Edge(v.getPi(), v));
						v = v.getPi();
					}
					break;
				} else if (v.getColor().equals(white)) {
					v.setColor(gray);
					v.setD(u.getD() + 1);
					v.setPi(u);
					q.add(v);
				}
			}
			u.setColor(black);
		}

		// if not exist return null
		return ret;
	}

	/**
	 * Calculates the graph showing the maxFlow.
	 *
	 * @param sources
	 *            a list of all source nodes
	 * @param destinations
	 *            a list of all destination nodes
	 * @return a ArrayList of Strings as specified in the task in dot code
	 */
	public final ArrayList<String> findResidualNetwork(final String[] sources, final String[] destinations) {
		this.findMaxFlow(sources, destinations);
		return toDotCode(this.map);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.map.toString();
	}

	public static void main(String[] args) {
		MaxFlow m = new MaxFlow("Iksburg1");
		// System.out.println(m);
		m.findResidualNetwork(new String[] { "A" }, new String[] { "F" }).forEach(x -> System.out.println(x));
	}
}