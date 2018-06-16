package lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdjazenzlistMap implements audMap {
	private List<Vertex> vertices; // Collection V = {v1,v2,v3}
	private List<Edge> edges; // Collection E = {e1,e2,e3}
	private HashMap<Vertex, Double> waitTime; //the function waitTime: V->Double
	private HashMap<Edge, Double> distance;  //the function distance(weight): E->Double 
	private HashMap<Edge, Double> speedLimit;  //the function speedLimit: E->Double
	
	public AdjazenzlistMap() {
		// TODO Auto-generated constructor stub
		vertices = new ArrayList<>();
		edges= new ArrayList<>();
		waitTime = new HashMap<>();
		distance = new HashMap<>();
		speedLimit = new HashMap<>();
	}
	
	@Override
	public void addVertex(Vertex v) {
		this.vertices.add(v);
	}
	@Override
	public void addEdge(Edge v) {
		this.edges.add(v);
		
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
	
	public String toString(){
		System.out.println("vertices:");
		this.vertices.forEach(x->System.out.println(x));
		System.out.println("edges:");
		this.edges.forEach(x->System.out.println(x));
		System.out.println("waitTime:");
		this.waitTime.forEach((x,y)->System.out.println(x.toString()+y.toString()));
		System.out.println("distance:");
		this.distance.forEach((x,y)->System.out.println(x.toString()+y.toString()));
		System.out.println("speed:");
		this.speedLimit.forEach((x,y)->System.out.println(x.toString()+y.toString()));
		return null;
	}
}
