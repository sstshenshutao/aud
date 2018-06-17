package lab;

import java.util.HashMap;
import java.util.List;

public interface audMap {
	// audMap can be AdjazenzlistMap or AdjazenzmatrixMap
	public void addVertex(Vertex v) ;
	public void addEdge(Edge v) ;
	public void addWaitTime(Vertex v, Double waittime) ;
	public void addDistance(Edge e, Double distance) ;
	public void addSpeedLimit(Edge e, Double speed) ;
	public List<Vertex> getVertices() ;
	public List<Vertex> copyVertices() ;
	public List<Vertex> getAdj(Vertex v);
	public List<Edge> getEdges();
	public HashMap<Vertex, Double> getWaitTime() ;
	public HashMap<Edge, Double> getDistance() ;
	public HashMap<Edge, Double> getSpeedLimit() ;
	public Vertex getVertice(Vertex v);
	public Edge getEdge(Vertex u, Vertex v);
}
