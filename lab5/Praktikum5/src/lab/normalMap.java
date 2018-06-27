package lab;

import java.util.List;

public interface normalMap {
	// audMap can be AdjazenzlistMap or AdjazenzmatrixMap
	public void addVertex(Vertex v) ;
	public void addEdge(Edge v) ;
	public List<Vertex> getVertices() ;
	public List<Vertex> copyVertices() ;
	public List<Vertex> getAdj(Vertex v);
	public List<Edge> getEdges();
	public Vertex getVertice(Vertex v);
	public Edge getEdge(Vertex u, Vertex v);
}
