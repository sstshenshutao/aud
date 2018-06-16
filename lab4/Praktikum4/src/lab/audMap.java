package lab;

public interface audMap {
	// audMap can be AdjazenzlistMap or AdjazenzmatrixMap
	public void addVertex(Vertex v) ;
	public void addEdge(Edge v) ;
	public void addWaitTime(Vertex v, Double waittime) ;
	public void addDistance(Edge e, Double distance) ;
	public void addSpeedLimit(Edge e, Double speed) ;
	
}
