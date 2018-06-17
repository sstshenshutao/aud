package lab;

public class Edge {
	private Vertex to;
	private Vertex from;
	public Edge(Vertex from, Vertex to) {
		// TODO Auto-generated constructor stub
		this.from= from;
		this.to=  to;
	}
	public Edge(String from, String to) {
		// TODO Auto-generated constructor stub
		add(from, to);
	}
	
	public void add(String from, String to) {
		// TODO Auto-generated method stub
		this.from=new Vertex(from);
		this.to = new Vertex(to);
	}

	/**
	 * @return the to
	 */
	
	public Vertex getTo() {
		return to;
	}

	/**
	 * @return the from
	 */
	
	public Vertex getFrom() {
		return from;
	}
	@Override
	public boolean equals(Object anObject) {
		// TODO Auto-generated method stub
		if (this == anObject) {
            return true;
        }
        if (anObject instanceof Edge) {
        	Edge aEdge = (Edge)anObject;
        	return this.from.equals(aEdge.getFrom())&&this.to.equals(aEdge.getTo());
        }
        return false;
		
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
        int result = 17;  
        result = result * 31 + from.hashCode() + to.hashCode();  
        return result;  
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer ret= new StringBuffer();
		ret.append(this.from.toString());
			ret.append("->").append(this.to.toString());
		return ret.toString();
	}
}
