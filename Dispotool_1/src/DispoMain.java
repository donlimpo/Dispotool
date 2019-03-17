
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DispoMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Houston...");

		ArrayList<String> Inhalt = new ArrayList<String>();
		ArrayList<Kanten> Vertex = new ArrayList<Kanten>(); 
		
		/* hier beginnt das Laden*/
		 String csvFile = "BCG_Basis_Netz_20190315-1.csv";
		
	        String line = "";
	        String cvsSplitBy = ";";

	        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String[] Kante = line.split(cvsSplitBy);
	                System.out.println("Kante [von= " + Kante[0] + " => Nach=" + Kante[1] + "] Dauer: "+ Kante[4]);
	                System.out.println(Kante[1].toString());
	                Kanten TempKante = new Kanten();
	                TempKante.Von =Kante[0];
	                TempKante.Nach = Kante[1];
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

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        System.out.println("Hier geben wir eine Liste aus");
	        
	        System.out.println(Vertex.get(0).Von);
	       
	}
}

