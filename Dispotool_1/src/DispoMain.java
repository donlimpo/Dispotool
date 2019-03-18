
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DispoMain {
	private ArrayList<Kanten> ALKanten;

/*
 * Quelle: https://examples.javacodegeeks.com/core-java/java-8-stream-collectors-groupingby-example/
 */
public	Map<String, List<Kanten>> groupByKnoten() {
	

	return ALKanten.stream().collect(Collectors.groupingBy(Kanten::getVon));

}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Houston...");

		ArrayList<Kanten> Vertex = new ArrayList<Kanten>(); 
		
		/* hier beginnt das Laden*/
		String csvFile = "BCG_Basis_Netz_20190315-1.csv";
		int iZaehler = 0;
        String line = "";
        String cvsSplitBy = ";";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] Kante = line.split(cvsSplitBy);
                System.out.println("Kante [von= " + Kante[0] + " => Nach=" + Kante[1] + "] Dauer: "+ Kante[4]);

                Kanten TempKante = new Kanten();
                TempKante.Von =Kante[0];
                TempKante.Nach = Kante[1];
                if (iZaehler >0) { /* sorgt dafür, das die erste Zeile nicht gelesen wird*/
	                try {
	                	TempKante.Dauer = Integer.parseInt(Kante[4]);
	                }
	                catch (NumberFormatException e) 
	                {
	                TempKante.Dauer =0;
	                }
	               /* Achtung: die erste Zeile enthält einen Namenud kann daher einen Fehler werfen.
	                */
	                Vertex.add(TempKante);
                }
                iZaehler++;
	           }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        System.out.println("Es wurden "+  iZaehler + " Zeilen geladen");
	        
	       // System.out.println(Vertex.get(0).Von);
	       
	}
}

