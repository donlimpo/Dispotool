
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DispoMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Houston...");
		ArrayList<String[]> Basisdaten =  new ArrayList<String[]>(); 
		ArrayList<String> Inhalt = new ArrayList<String>();
		
		/* hier beginnt das Laden*/
		 String csvFile = "BCG_Basis_Netz_20190315-1.csv";
		
	        String line = "";
	        String cvsSplitBy = ";";

	        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String[] Kante = line.split(cvsSplitBy);
	                Inhalt.add(line);
	                System.out.println("Kante [von= " + Kante[0] + " => Nach=" + Kante[1] + "] Dauer: "+ Kante[4]);
	                System.out.println(Kante[1].toString());
	                Arrays.deepToString(Basisdaten);
	                Basisdaten.add(Kante);

	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        System.out.println("Hier geben wir eine Liste aus");
	        
	        System.out.println(Arrays.toString(Basisdaten[1]));
	       
	}
}

