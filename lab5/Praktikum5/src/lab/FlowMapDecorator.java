package lab;

import java.util.ArrayList;
import java.util.List;

public class FlowMapDecorator{
	private FlowMap flowMap;
	private FlowMap superMap;
	private Vertex sS;
	private Vertex sD;

	public FlowMapDecorator(FlowMap flowMap, String[] sources, String[] destinations) {
		this.flowMap = flowMap;
		init(sources, destinations);
	}

	private void init(String[] sources, String[] destinations) {
		this.superMap = new FlowMap(new Flow());

		Vertex superS = new Vertex("SuperS");
		this.sS = superS;
		this.superMap.addVertex(superS);
		for (String s : sources) {
			Vertex secondS = new Vertex(s);
			this.superMap.addVertex(secondS);
			Edge e = new Edge(superS, secondS);
			this.superMap.addEdge(e);
			this.superMap.setC(e, Integer.MAX_VALUE);
			this.superMap.setF(e, 0);
		}

		Vertex superD = new Vertex("SuperD");
		this.sD = superD;
		this.superMap.addVertex(superD);
		for (String d : destinations) {
			Vertex secondD = new Vertex(d);
			this.superMap.addVertex(secondD);
			Edge e = new Edge(secondD, superD);
			this.superMap.addEdge(e);
			this.superMap.setC(e, Integer.MAX_VALUE);
			this.superMap.setF(e, 0);
		}

	}

	public List<Edge> getEdges() {
		List<Edge> ret = new ArrayList<>();
		ret.addAll(this.flowMap.getEdges());
		ret.addAll(this.superMap.getEdges());
		return ret;
	}

	public Integer getF(Edge e) {
		if (this.superMap.getEdges().contains(e)) {
			return this.superMap.getF(e);
		} else {
			return this.flowMap.getF(e);
		}
	}

	public Integer getC(Edge e) {
		if (this.superMap.getEdges().contains(e)) {
			return this.superMap.getC(e);
		} else {
			return this.flowMap.getC(e);
		}
	}

	public Vertex getSuperS() {
		return this.sS;
	}

	public Vertex getSuperD() {
		return this.sD;
	}

	/**
	 * @return the superMap
	 */
	public FlowMap getSuperMap() {
		return superMap;
	}
	
}
