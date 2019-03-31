import algos.dijkstra.model.Vertex;

public class Fahrten implements Comparable<Fahrten>{
	String idString;
	Vertex vonVertex;
	Vertex nachVertex;
	int dauerint;

	public Fahrten(String idString,
			Vertex vonVertex,
			Vertex nachVertex,
			int dauerint) {
		this.idString = idString;
		this.vonVertex = vonVertex;
		this.nachVertex = nachVertex;
		this.dauerint = dauerint;
	}
	
	public String getID() {
		return idString;
		
	}
	
	public int getFahrDauer() {
		return dauerint;
	}

	@Override
    public int compareTo(Fahrten fahrt1, Fahrten fahrt2) {
		return  fahrt1.dauerint.compareTo(fahrt2.dauerint);
    }
	
}
