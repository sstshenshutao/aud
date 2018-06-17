package lab;

public class Vertex {
	private String name;
	private Vertex pi;
	private String color;
	private double d;
	
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

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
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
	public int hashCode() {
		// TODO Auto-generated method stub
        int result = 17;  
        result = result * 11 + this.getName().hashCode();  
        return result; 
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "name:"+this.name+"|pi:"+this.pi+"|color:"+this.color;
	}
	
	@Override
	public boolean equals(Object anObject) {
		// TODO Auto-generated method stub
		if (this == anObject) {
            return true;
        }
        if (anObject instanceof Vertex) {
        	Vertex aVertex = (Vertex)anObject;
        	return this.name.equals(aVertex.getName());
        }
        return false;
	}
}
