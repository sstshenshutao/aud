package lab;

import java.util.List;

public interface Edge {
	public void add(String from, String to);
	public List<String> getAdj();
	public String getFrom() ;
}
