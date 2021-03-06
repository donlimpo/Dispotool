import java.util.ArrayList;
import java.util.List;

import algos.dijkstra.model.Edge;
import algos.dijkstra.model.Vertex;

public class Standorte {
	final private ArrayList<Vertex> standorteVertexs;
	
	
	public Standorte(ArrayList<Vertex> standorteVertexs) {
		this.standorteVertexs = standorteVertexs;
		
	}
	
	public ArrayList<Vertex> getStandorte() {
		return standorteVertexs;
		
	}


public static List<Vertex> BereitschaftenFuellen(ArrayList<Vertex> AlleStandorte) {
	List<String> standorteList = new ArrayList<String>();
	List<Vertex> standortVertexs = new ArrayList<Vertex>();
	
	standorteList.add("MH");
	standorteList.add("TS");
	standorteList.add("FF");
	standorteList.add("AA");
	standorteList.add("BL");
	standorteList.add("HH");
	standorteList.add("KK");
	
	standorteList.forEach((temp) -> {
	 Vertex tempStandortVertex = AlleStandorte.stream()
			.filter(Bahnhof -> Bahnhof.getName().equals(temp.toString()))
			.findAny()
			.orElse(null);
	 standortVertexs.add(tempStandortVertex);
	});
	return standortVertexs;
}
}
