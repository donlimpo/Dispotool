import java.util.ArrayList;

import algos.dijkstra.model.Edge;
import algos.dijkstra.model.Vertex;

public class Wege {
	/*
	 * Diese Klasse ist dazu da den Weg einer Bereitschaft abzubilden
	 * 
	 * 1) Vom Standort zum Zug
	 * 2) Auf dem Zug
	 * 3) Vom Zug zurück zum Standort
	 */
	final private String idString;
	final private ArrayList<Vertex> stationenArrayList;
	final private ArrayList<Edge> kantenArrayList;
	final private String fahrtartString;
	
	public Wege(String idString, ArrayList<Vertex> stationenArrayList, ArrayList<Edge> kantenArrayList, String fahrtartString) {
		this.idString = idString;
		this.stationenArrayList = stationenArrayList;
		this.kantenArrayList =kantenArrayList;
		this.fahrtartString = fahrtartString;
		
	}
	
	public String getIdString() {
		return idString;
	}
	
	public String getSuchfahrtString() {
		return fahrtartString;
	
	}
	
	public void ReisezeitStandorteEinsatzort(Vertex Standort) {
		
	}
	

}
