package lab;

public class Vertex {
	private String name;
	private Vertex pi;
	private String color;
	
	public Vertex(String name) {
		// TODO Auto-generated constructor stub
		this.name= name;
		this.pi= null;
		this.color = "white";
	}
	
	public Vertex(String name ,String color) {
		// TODO Auto-generated constructor stub
		this.name= name;
		this.pi= null;
		this.color = color;
	}
	
	public Vertex(String name ,String color, Vertex pi) {
		// TODO Auto-generated constructor stub
		this.name= name;
		this.pi= pi;
		this.color = color;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the pi
	 */
	public Vertex getPi() {
		return pi;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param pi the pi to set
	 */
	public void setPi(Vertex pi) {
		this.pi = pi;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "name:"+this.name+"|pi:"+this.pi+"|color:"+this.color;
	}
}
