
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import algos.dijkstra.model.Vertex;
import algos.dijkstra.engine.DijkstraAlgorithm;
import algos.dijkstra.model.Edge;
import algos.dijkstra.model.Graph;

public class DispoMain {
	private static ArrayList<Kanten> ALKanten = new ArrayList<Kanten>();
	private static ArrayList<Vertex> bhfVertex = new ArrayList<Vertex>();
	private static ArrayList<Edge> verbindungEdges = new ArrayList<Edge>();
	
	public ArrayList<Edge> bereitschaftenEdges= new ArrayList<Edge>();

/*
 * Quelle: https://examples.javacodegeeks.com/core-java/java-8-stream-collectors-groupingby-example/
 */
public static	Map<String, List<Kanten>> groupByKnoten() {
	
	System.out.println(ALKanten.stream().collect(Collectors.groupingBy(Kanten::getVon)));

	// Alt: return ALKanten.stream().collect(Collectors.sortedGroupingBy(Kanten::getVon));
	return ALKanten.stream().collect(sortedGroupingBy(Kanten::getVon));

}

/* Hilfsunkton zum sortieren der Liste
 * 
 * Quelle: http://www.rationaljava.com/2015/01/java8-lambdas-tip-collect.html 
 */
public static <T, K extends Comparable<K>> Collector<T, ?, TreeMap<K, List<T>>>  sortedGroupingBy(Function<T, K> function) {
     return Collectors.groupingBy(function, 
        TreeMap::new, Collectors.toList());
}
public static void PfadFindenStrings(String vonString, String nachString) {
    
    // wir fahren mal von MH nach TS
    Vertex TempVonVertex = bhfVertex.stream()
			   .filter(Bahnhof -> vonString.equals(Bahnhof.getId()))
			   .findAny()
			   .orElse(null);
    System.out.println(TempVonVertex);
    
    Vertex TempNach  = bhfVertex.stream()
			   .filter(Bahnhof -> nachString.equals(Bahnhof.getId()))
			   .findAny()
			   .orElse(null);
     Graph graph = new Graph(bhfVertex, verbindungEdges);

     DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
     dijkstra.execute(TempVonVertex); // von hier geht es los
     LinkedList<Vertex> path = dijkstra.getPath(TempNach);  // und hier steht der Zielknoten drin

//       assertNotNull(path);
//      assertTrue(path.size() > 0);

     for (Vertex vertex : path) {
         System.out.println(vertex);
     }
     
     /*
      * Jetzt als nächstes nochmal reingehen und die Dauer rausholen und zusätzlich angeben.
      */
     System.out.println(path);
     
      int k=0 ;
      int iRunSum =0;
     for (k=0; k< path.size()-1; k++) {
     	Vertex tempAktuellVonVertex = path.get(k);
     	Vertex tempAktuellNachVertex = path.get(k+1);
     	 Edge TempKante = verbindungEdges.stream()
     			 .filter(Ausgang -> tempAktuellVonVertex.equals(Ausgang.getSource()))
     			 .filter(Ausgang -> tempAktuellNachVertex.equals(Ausgang.getDestination()))
	    			 .findAny()
	    			  .orElse(null);
     	 iRunSum = iRunSum+ TempKante.getWeight();	        	
     	System.out.println("Von: "+ path.get(k) + " Nach: "+ path.get(k+1) + " Dauer: "+ TempKante.getWeight() + " Runsum: "+iRunSum);
     }      

	
}

public static void main(String[] args) {
	// TODO Auto-generated method stub
	System.out.println("Houston...");

	ArrayList<Kanten> Verbindung = new ArrayList<Kanten>(); 
	
	/* hier beginnt das Laden*/
	String csvFile = "BCG_Basis_Netz_20190315-1.csv";
	int iZaehler = 0;
    String line = "";
    String cvsSplitBy = ";";
    
    //List<Edge> Edge =new ArrayList<Edge>();
    
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

        while ((line = br.readLine()) != null) {
            // use comma as separator
            String[] Kante = line.split(cvsSplitBy);
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
               /* Achtung: die erste Zeile enthält einen Namen und kann daher einen Fehler werfen.
                 * hier baue ich noch rein, das das Ausland erstmal nicht berücksichtigt wird.
                 */
                char Tester = "X".charAt(0);
                if((Kante[0].charAt(0) != Tester) & (Kante[1].charAt(0) != Tester)) {
                	Verbindung.add(TempKante);
                }
                
            }
            iZaehler++;
           }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Es wurden "+  iZaehler + " Zeilen geladen");
        ALKanten = Verbindung;
       
        /*
         * Hier muß noch dasErgebnis von groupingByKnoten in eine Liste konvertiert werden:
         * Quelle: https://stackoverflow.com/questions/8892360/convert-set-to-list-without-creating-new-list
         */
        
       List<String> TempKnoten = new ArrayList<String>();
       TempKnoten.addAll(groupByKnoten().keySet());
     //  System.out.println(groupByKnoten().keySet().toString());
      /*
       * Durch die Liste iterieren und Knoten anlegen
       * Quelle: https://crunchify.com/how-to-iterate-through-java-list-4-way-to-iterate-through-loop/
       */
       
       List <Vertex> Bhfs = new ArrayList<Vertex>();
       
       TempKnoten.forEach((temp) -> {
    	   Vertex TempBhf = new Vertex(temp.toString(), temp.toString());
    	   Bhfs.add(TempBhf); // kann eigentlich raus wenn ich überall Bhfs durch bhfVertex erstetze
    	   bhfVertex.add(TempBhf);
    	//   System.out.println(temp);
       });
       
        /*
         * Jetzt müssen wir nochmal durch die Kanten gehen und die dazugehörigen Edges auch anlegen 
         */
       
       List <Edge> Connect = new ArrayList<Edge>();
       System.out.println(" Anzahl Zeilen in Verbindung:" + Verbindung.size());

       System.out.println(" Anzahl Zeilen in Bhfs:" + Bhfs.size());
       /*
        * Quelle: https://www.baeldung.com/find-list-element-java
        */
       Verbindung.forEach((temp) -> {
    	   //System.out.println(temp.Von +" ; "+ temp.Nach +" ; " + temp.Dauer);
    	   Vertex TempVonVertex = Bhfs.stream()
    			   .filter(Bahnhof -> temp.Von.equals(Bahnhof.getId()))
    			   .findAny()
    			   .orElse(null);
    	   
    	   Vertex TempNachVertex = Bhfs.stream()
    			   .filter(Bahnhof -> temp.Nach.equals(Bahnhof.getId()))
    			   .findAny()
    			   .orElse(null);
    	   
    	  // Hier ist noch abzufangen, das ich evtl. ins Leere gegriffen habe
    	  try {
	    	   Edge TempEdge = new Edge(TempVonVertex.getName()+" - "+ TempNachVertex.getName(),
	    			   TempVonVertex, 
	    			   TempNachVertex, 
	    			   temp.getDauer());
	    	   Connect.add(TempEdge);  //wir bauen auf verbindungsEdge um, damit das rauskann
	    	   verbindungEdges.add(TempEdge);
    	   }
    	  catch (NullPointerException e) {
    		 // System.out.println("Hier wäre etwas schiefgegangen!");
    	  }
   });
       System.out.println("Wir haben erfolgreich die Kanten befüllt");
       System.out.println("Es sind "+ Connect.size() + " Verbindungen." ) ;
       
       
       // wir fahren mal von MH nach TS
       Vertex TempVonVertex = Bhfs.stream()
			   .filter(Bahnhof -> "MH".equals(Bahnhof.getId()))
			   .findAny()
			   .orElse(null);
       System.out.println(TempVonVertex);
       
       Vertex TempNach  = Bhfs.stream()
			   .filter(Bahnhof -> "TS".equals(Bahnhof.getId()))
			   .findAny()
			   .orElse(null);
        Graph graph = new Graph(Bhfs, Connect);

        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(TempVonVertex); // von hier geht es los
        LinkedList<Vertex> path = dijkstra.getPath(TempNach);  // und hier steht der Zielknoten drin

 //       assertNotNull(path);
  //      assertTrue(path.size() > 0);

        for (Vertex vertex : path) {
            System.out.println(vertex);
        }
        
        /*
         * Jetzt als nächstes nochmal reingehen und die Dauer rausholen und zusätzlich angeben.
         */
        System.out.println(path);
        
         int k=0 ;
         int iRunSum =0;
        for (k=0; k< path.size()-1; k++) {
        	Vertex tempAktuellVonVertex = path.get(k);
        	Vertex tempAktuellNachVertex = path.get(k+1);
        	 Edge TempKante = Connect.stream()
        			 .filter(Ausgang -> tempAktuellVonVertex.equals(Ausgang.getSource()))
        			 .filter(Ausgang -> tempAktuellNachVertex.equals(Ausgang.getDestination()))
	    			 .findAny()
	    			  .orElse(null);
        	 iRunSum = iRunSum+ TempKante.getWeight();	        	
        	System.out.println("Von: "+ path.get(k) + " Nach: "+ path.get(k+1) + " Dauer: "+ TempKante.getWeight() + " Runsum: "+iRunSum);
        }      

        System.out.println(Standorte.BereitschaftenFuellen(bhfVertex));  
        PfadFindenStrings("MH", "HH");
	}




}

/*
 * Ergänzungen / Ideenspeicher
 * 
 * Knoten mit weiteren Infos versehen wie z.B. Lat Long, Halt, Betriebshalt,...
 * 
 * Standorte der Bereitschaften hinzufügen
 * 
 * Anreise der Bereitschaften zum Startort berechnen
 */

