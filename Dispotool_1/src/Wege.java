import java.util.ArrayList;
import java.util.Date;

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
	public String idString;
	public Vertex vonVertex;
	public Vertex nachVertex;
	public int abDate;
	public int anDate;
	public int intDauer;
	public int intcumDauer;
	
	public Wege(String idString, 
			Vertex vonVertex,
			Vertex nachVertex,
			int abDate,
			int anDate,
			int intDauer,
			int intcumDauer) {
		this.idString = idString;
		this.vonVertex = vonVertex;
		this.nachVertex =nachVertex;
		this.abDate =abDate;
		this.anDate = anDate;
		this.intDauer = intDauer;
		this.intcumDauer = intcumDauer;
		}

	public String getIdString() {
		return idString;
	}
		
	public void ReisezeitStandorteEinsatzort(Vertex Standort) {
		
	}
	

}
