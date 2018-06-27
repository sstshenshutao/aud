package lab;

import java.util.HashMap;

public class Flow {
	private HashMap<Edge, Integer> f;
	private HashMap<Edge, Integer> c;

	public Flow() {
		this.f = new HashMap<>();
		this.c = new HashMap<>();
	}



	public HashMap<Edge, Integer> getAllF() {
		return this.f;
	}

	public HashMap<Edge, Integer> getAllC() {
		return this.c;
	}
}
