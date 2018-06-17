package lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AdjazenzlistMap implements audMap {
	private List<Vertex> vertices; // Collection V = {v1,v2,v3}
	private HashMap<Vertex, Double> waitTime; //the function waitTime: V->Double
	private HashMap<Edge, Double> distance;  //the function distance(weight): E->Double 
	private HashMap<Edge, Double> speedLimit;  //the function speedLimit: E->Double
	private List<LinkedList<Vertex>> linkedVertices;
	public AdjazenzlistMap() {
		// TODO Auto-generated constructor stub
		vertices = new ArrayList<>();
		waitTime = new HashMap<>();
		distance = new HashMap<>();
		speedLimit = new HashMap<>();
		linkedVertices= new ArrayList<>();
	}
	
	@Override
	public void addVertex(Vertex v) {
		this.vertices.add(v);
	}
	@Override
	public void addEdge(Edge v) {
//		this.edges.add(v);
		for(LinkedList<Vertex> lv: linkedVertices) {
			if(lv.getFirst().equals(v.getFrom())) {
				// to -> lv
				lv.add(v.getTo());
				return;
			}
		}
		LinkedList<Vertex> lv = new LinkedList<>();
		lv.addFirst(v.getFrom());
		lv.add(v.getTo());
		linkedVertices.add(lv);
	}
	@Override
	public void addWaitTime(Vertex v, Double waittime) {
		this.waitTime.put(v, waittime);
	}
	@Override
	public void addDistance(Edge e, Double distance) {
		this.distance.put(e, distance);
	}
	@Override
	public void addSpeedLimit(Edge e, Double speed) {
		this.speedLimit.put(e, speed);
	}
	@Override
	public List<Vertex> getVertices() {
		return vertices;
	}
	@Override
	public List<Edge> getEdges() {
		List<Edge> le = new ArrayList<>();
		for(LinkedList<Vertex> lv: linkedVertices) {
			Vertex from = lv.getFirst();
			for(Vertex v : lv) {
				if(!v.equals(from)) le.add(new Edge(from, v));
			}
		}
		return le;
	}
	@Override
	public HashMap<Vertex, Double> getWaitTime() {
		return waitTime;
	}
	@Override
	public HashMap<Edge, Double> getDistance() {
		return distance;
	}
	@Override
	public HashMap<Edge, Double> getSpeedLimit() {
		return speedLimit;
	}

	public String toString(){
		System.out.println("vertices:");
		this.vertices.forEach(x->System.out.println(x));
		System.out.println("edges:");
		this.getEdges().forEach(x->System.out.println(x));
		System.out.println("waitTime:");
		this.waitTime.forEach((x,y)->System.out.println(x.toString()+y.toString()));
		System.out.println("distance:");
		this.distance.forEach((x,y)->System.out.println(x.toString()+y.toString()));
		System.out.println("speed:");
		this.speedLimit.forEach((x,y)->System.out.println(x.toString()+y.toString()));
		return null;
	}

	@Override
	public Vertex getVertice(Vertex v) {
		// TODO Auto-generated method stub
		for(Vertex vertex: this.vertices) {
			if (vertex.equals(v)) return vertex;
		}
		return null;
	}

	@Override
	public Edge getEdge(Vertex u, Vertex v) {
		// TODO Auto-generated method stub
		for(LinkedList<Vertex> lv: linkedVertices) {
			if(lv.getFirst().equals(u)) {
				// to -> lv
				return lv.contains(v)?new Edge(u, v):null;
			}
		}
		return null;
	}

	@Override
	public List<Vertex> copyVertices() {
		// TODO Auto-generated method stub
		List<Vertex> cpVertices= new ArrayList<>();
		for(Vertex v: this.vertices) {
			cpVertices.add(v);
		}
		return cpVertices;
	}

	@Override
	public List<Vertex> getAdj(Vertex v) {
		// TODO Auto-generated method stub
		for(LinkedList<Vertex> lv: linkedVertices) {
			if(lv.getFirst().equals(v)) {
				List<Vertex> le = new ArrayList<>();
				Vertex from = v;
				for(Vertex ve : lv) {
					if(!ve.equals(from)) le.add(ve);
				}
				return le;
			}
		}
		return null;
	}
}
