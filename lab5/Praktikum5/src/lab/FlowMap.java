package lab;

public class FlowMap extends AdjazenzlistMap {
	private Flow flow;

	public FlowMap(Flow flow) {
		super();
		this.flow = flow;
		init();
	}

	private void init() {

	}
//	-----------------F-----------------
	public Integer getF(Vertex u, Vertex v) {
		Edge e = super.getEdge(u, v);
		return this.getF(e);
	}

	public Integer getF(Edge e) {
		return this.flow.getAllF().get(e);
	}

	public void setF(Edge e, Integer f) {
		this.flow.getAllF().put(e, f);
	}

	public void setF(Vertex u, Vertex v, Integer f) {
		Edge e = super.getEdge(u, v);
		this.setF(e, f);
	}
//-----------------C-----------------
	public Integer getC(Edge e) {
		return this.flow.getAllC().get(e);
	}

	public Integer getC(Vertex u, Vertex v) {
		Edge e = super.getEdge(u, v);
		return this.getC(e);
	}

	public void setC(Edge e, Integer c) {
		// TODO Auto-generated method stub
		this.flow.getAllC().put(e, c);
	}

	public void setC(Vertex u, Vertex v, Integer c) {
		// TODO Auto-generated method stub
		Edge e = super.getEdge(u, v);
		this.setC(e, c);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer ret = new StringBuffer();
		ret.append(super.toString());
		ret.append("Flow:\n");
		// System.out.println(this.flow.getAllC());
		for (Edge e : this.flow.getAllC().keySet()) {
			ret.append(e.toString());
			ret.append("|Flow:");
			ret.append((this.getF(e) == null) ? "notInit" : this.getF(e).toString());
			ret.append("/");
			ret.append(this.getC(e).toString());
			ret.append("\n");
		}

		return ret.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
