import java.util.Comparator;

import algos.dijkstra.model.Vertex;

public class Fahrten implements Comparable<Fahrten> {
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
	
    @Override public int compareTo(Fahrten fahrt2) {
    	return this.dauerint < fahrt2.dauerint ? -1
    			: this.dauerint > fahrt2.dauerint ? 1
    			:0;
    			/*Quellen:
    			 * https://stackoverflow.com/questions/9307751/override-compareto-and-sort-using-two-strings
    			 *https://stackoverflow.com/questions/14178539/can-i-use-compareto-to-sort-integer-and-double-values
    			 *oder am besten hier:
    			 *http://openbook.rheinwerk-verlag.de/javainsel9/javainsel_09_001.htm
    			 */
      }
	
}
