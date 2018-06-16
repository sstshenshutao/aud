package lab;

import java.util.ArrayList;
import java.util.List;

public class AdjazenzlistEdge implements Edge {
	private List<String> adj;
	private String from;
	public AdjazenzlistEdge() {
		// TODO Auto-generated constructor stub
		adj =new ArrayList<>();
	}
	@Override
	public void add(String from, String to) {
		// TODO Auto-generated method stub
		if(this.from == null) this.from=from;
		adj.add(to);
	}

	/**
	 * @return the adj
	 */
	@Override
	public List<String> getAdj() {
		return adj;
	}

	/**
	 * @return the from
	 */
	@Override
	public String getFrom() {
		return from;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer ret= new StringBuffer();
		ret.append(this.from);
		for(String a:adj) {
			ret.append("->").append(a);
		}
		return ret.toString();
	}
}
