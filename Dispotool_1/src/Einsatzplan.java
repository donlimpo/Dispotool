import java.util.ArrayList;
import algos.dijkstra.model.Vertex;
public class Einsatzplan {

	
	public String idString;
	public Vertex VertexHeimatstandort;
	public Fahrten FahrtenAnfahrt;
	public Fahrten FahrtenLastfahrt;
	public Fahrten FahrtenAbfahrt;
	public int intSchichtdauer;
	public int intDauerAnfahrt;
	public int intDauerLastfahrt;
	public int intDauerAbfahrt;
	public int intcumDauer;
	
	public static void main(String[] args) {
		
	}
	
	public Einsatzplan(String idString, 	
			 			Vertex VertexHeimatstandort,
						Fahrten FahrtenAnfahrt,
						Fahrten FahrtenLastfahrt,
						Fahrten FahrtenAbfahrt,
						int intSchichtdauer,
						int intDauerAnfahrt,
						int intDauerLastfahrt,
						int intDauerAbfahrt,
						int intcumDauer) {
		this.idString = idString;
		this.VertexHeimatstandort = VertexHeimatstandort;
		this.FahrtenAnfahrt =FahrtenAnfahrt;
		this.FahrtenLastfahrt =FahrtenLastfahrt;
		this.FahrtenAbfahrt = FahrtenAbfahrt;
		this.intSchichtdauer = intSchichtdauer;
		this.intDauerAnfahrt = intDauerAnfahrt;
		this.intDauerLastfahrt = intDauerLastfahrt;
		this.intDauerAbfahrt = intDauerAbfahrt;
		this.intcumDauer = intcumDauer;
		}
	public Einsatzplan() {
		this.idString = "idDefault";
		this.VertexHeimatstandort = null;
		this.FahrtenAnfahrt =null;
		this.FahrtenLastfahrt =null;
		this.FahrtenAbfahrt = null;
		this.intSchichtdauer = 30000;
		this.intDauerAnfahrt = 0;
		this.intDauerLastfahrt = 0;
		this.intDauerAbfahrt = 0;
		this.intcumDauer = 0;
		
	}

	public String getIdString() {
		return idString;
	}
	
		
	

}
