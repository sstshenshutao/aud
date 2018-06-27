package lab;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AdjazenzlistMap implements normalMap {
	private List<Vertex> vertices; // Collection V = {v1,v2,v3}
	private List<LinkedList<Vertex>> linkedVertices;

	public AdjazenzlistMap() {
		// TODO Auto-generated constructor stub
		vertices = new ArrayList<>();
		linkedVertices = new ArrayList<>();
	}

	@Override
	public void addVertex(Vertex v) {
		if (!vertices.contains(v)) {
			this.vertices.add(v);
		}

	}

	@Override
	public void addEdge(Edge v) {
		// this.edges.add(v);
		for (LinkedList<Vertex> lv : linkedVertices) {
			if (lv.getFirst().equals(v.getFrom())) {
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
	public List<Vertex> getVertices() {
		return vertices;
	}

	@Override
	public List<Edge> getEdges() {
		List<Edge> le = new ArrayList<>();
		for (LinkedList<Vertex> lv : linkedVertices) {
			Vertex from = lv.getFirst();
			for (Vertex v : lv) {
				if (!v.equals(from))
					le.add(new Edge(from, v));
			}
		}
		return le;
	}

	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("vertices:\n");
		for (Vertex v : this.vertices) {
			ret.append(v.toString());
			ret.append("\n");
		}
		ret.append("edges:\n");
		for (Edge e : this.getEdges()) {
			ret.append(e.toString());
			ret.append("\n");
		}

		return ret.toString();
	}

	@Override
	public Vertex getVertice(Vertex v) {
		// TODO Auto-generated method stub
		for (Vertex vertex : this.vertices) {
			if (vertex.equals(v))
				return vertex;
		}
		return null;
	}

	@Override
	public Edge getEdge(Vertex u, Vertex v) {
		// TODO Auto-generated method stub
		for (LinkedList<Vertex> lv : linkedVertices) {
			if (lv.getFirst().equals(u)) {
				// to -> lv
				return lv.contains(v) ? new Edge(u, v) : null;
			}
		}
		return null;
	}

	@Override
	public List<Vertex> copyVertices() {
		// TODO Auto-generated method stub
		List<Vertex> cpVertices = new ArrayList<>();
		for (Vertex v : this.vertices) {
			cpVertices.add(v);
		}
		return cpVertices;
	}

	@Override
	public List<Vertex> getAdj(Vertex v) {
		// TODO Auto-generated method stub
		for (LinkedList<Vertex> lv : linkedVertices) {
			if (lv.getFirst().equals(v)) {
				List<Vertex> le = new ArrayList<>();
				Vertex from = v;
				for (Vertex ve : lv) {
					if (!ve.equals(from))
						le.add(ve);
				}
				return le;
			}
		}
		return null;
	}
}
