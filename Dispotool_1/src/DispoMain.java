
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
public static ArrayList<Wege> PfadFindenStrings(String vonString, String nachString) {
	if (vonString.equals(nachString)) {
		return null;
	}
    
	ArrayList<Wege> tempReiseWeg = new ArrayList<Wege>();
    // wir fahren mal von MH nach TS
    Vertex TempVonVertex = BhfFindenVertex(vonString);
    Vertex TempNach  = BhfFindenVertex(nachString);
     Graph graph = new Graph(bhfVertex, verbindungEdges);

     DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
     dijkstra.execute(TempVonVertex); // von hier geht es los
     LinkedList<Vertex> path =  dijkstra.getPath(TempNach);  // und hier steht der Zielknoten drin
//       assertNotNull(path);
//      assertTrue(path.size() > 0);
 /*    System.out.println(path);
*/     
      int k=0 ;
      int iRunSum =0;
     for (k=0; k< path.size()-1; k++) {
    	 //könnte man eigentlich auch umstellen, da es sich bei path um eine linked list handelt.
     	Vertex tempAktuellVonVertex = path.get(k);
     	Vertex tempAktuellNachVertex = path.get(k+1);
     	 Edge TempKante = verbindungEdges.stream()
     			 .filter(Ausgang -> tempAktuellVonVertex.equals(Ausgang.getSource()))
     			 .filter(Ausgang -> tempAktuellNachVertex.equals(Ausgang.getDestination()))
	    			 .findAny()
	    			  .orElse(null);
     	 iRunSum = iRunSum+ TempKante.getWeight();	        	
     	System.out.println("Von: "+ path.get(k) + " Nach: "+ path.get(k+1) + " Dauer: "+ TempKante.getWeight() + " Runsum: "+iRunSum);

     	Wege tempWeg = new Wege(vonString + "-" + nachString, 
     			path.get(k),
     			path.get(k+1),
     			0,
     			0,
     			TempKante.getWeight(),
     			iRunSum
     			);
     	tempWeg.vonVertex= path.get(k);
     	
     	tempReiseWeg.add(tempWeg);
     }
 	System.out.println("Fahrtdauer aus DijkstraAlgorithm :"+ dijkstra.getDuration(path));
return tempReiseWeg;
	
}

public static int Fahrtdauer(List<Vertex> pfadList ) {
	int intDauer=0;
    int k=0 ;
   for (k=0; k< pfadList.size()-1; k++) {
   	Vertex tempAktuellVonVertex = pfadList.get(k);
   	Vertex tempAktuellNachVertex = pfadList.get(k+1);
   	 Edge TempKante = verbindungEdges.stream()
   			 .filter(Ausgang -> tempAktuellVonVertex.equals(Ausgang.getSource()))
   			 .filter(Ausgang -> tempAktuellNachVertex.equals(Ausgang.getDestination()))
   			 .findAny()
   			  .orElse(null);
   	intDauer = intDauer+ TempKante.getWeight();	        	
   	System.out.println("Runsum: "+intDauer);
   }   
	return intDauer;
}

public static void ReiserouteDrucken(List<Vertex> pfadList) {
    
    int k=0 ;
    int iRunSum =0;
   for (k=0; k< pfadList.size()-1; k++) {
   	Vertex tempAktuellVonVertex = pfadList.get(k);
   	Vertex tempAktuellNachVertex = pfadList.get(k+1);
   	 Edge TempKante = verbindungEdges.stream()
   			 .filter(Ausgang -> tempAktuellVonVertex.equals(Ausgang.getSource()))
   			 .filter(Ausgang -> tempAktuellNachVertex.equals(Ausgang.getDestination()))
   			 .findAny()
   			  .orElse(null);
   	 iRunSum = iRunSum+ TempKante.getWeight();	        	
   	System.out.println("Von: "+ pfadList.get(k) + " Nach: "+ pfadList.get(k+1) + " Dauer: "+ TempKante.getWeight() + " Runsum: "+iRunSum);
   }      


	
}

public static Vertex BhfFindenVertex(String suchString) {
	
	 Vertex TempVertex = bhfVertex.stream()
			   .filter(Bahnhof -> suchString.equals(Bahnhof.getId()))
			   .findAny()
			   .orElse(null);
	return TempVertex;
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
       
       TempKnoten.forEach((temp) -> {
    	   Vertex TempBhf = new Vertex(temp.toString(), temp.toString());
    	   bhfVertex.add(TempBhf);
    	//   System.out.println(temp);
       });
       
        /*
         * Jetzt müssen wir nochmal durch die Kanten gehen und die dazugehörigen Edges auch anlegen 
         */
       
       System.out.println(" Anzahl Zeilen in Verbindung:" + Verbindung.size());

       System.out.println(" Anzahl Zeilen in Bhfs:" + bhfVertex.size());
       /*
        * Quelle: https://www.baeldung.com/find-list-element-java
        */
       Verbindung.forEach((temp) -> {
    	   //System.out.println(temp.Von +" ; "+ temp.Nach +" ; " + temp.Dauer);
    	   Vertex TempVonVertex = bhfVertex.stream()
    			   .filter(Bahnhof -> temp.Von.equals(Bahnhof.getId()))
    			   .findAny()
    			   .orElse(null);
    	   
    	   Vertex TempNachVertex = bhfVertex.stream()
    			   .filter(Bahnhof -> temp.Nach.equals(Bahnhof.getId()))
    			   .findAny()
    			   .orElse(null);
    	   
    	  // Hier ist noch abzufangen, das ich evtl. ins Leere gegriffen habe
    	  try {
	    	   Edge TempEdge = new Edge(TempVonVertex.getName()+" - "+ TempNachVertex.getName(),
	    			   TempVonVertex, 
	    			   TempNachVertex, 
	    			   temp.getDauer());
	    	   verbindungEdges.add(TempEdge);
    	   }
    	  catch (NullPointerException e) {
    		 // System.out.println("Hier wäre etwas schiefgegangen!");
    	  }
   });
       System.out.println("Wir haben erfolgreich die Kanten befüllt");
       System.out.println("Es sind "+ verbindungEdges.size() + " Verbindungen." ) ;
       
       
       // wir fahren mal von MH nach TS
       Vertex TempVonVertex = BhfFindenVertex("MH");
       Vertex TempNachVertex  = BhfFindenVertex("TS");

       Graph graph = new Graph(bhfVertex, verbindungEdges);

        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(TempVonVertex); // von hier geht es los
        LinkedList<Vertex> path = dijkstra.getPath(TempNachVertex);  // und hier steht der Zielknoten drin

 //       assertNotNull(path);
  //      assertTrue(path.size() > 0);

 //      for (Vertex vertex : path) {
 //           System.out.println(vertex);
 //       }
        
        /*
         * Jetzt als nächstes nochmal reingehen und die Dauer rausholen und zusätzlich angeben.
         */
        System.out.println("Fahrtdauer: "+ Fahrtdauer(path));
        System.out.println("Fahrtdauer aus DijkstraAlgorithm :"+ dijkstra.getDuration(path));
 
        ReiserouteDrucken(path);
   //     System.out.println(Standorte.BereitschaftenFuellen(bhfVertex));  
        List<Vertex> StandortListe = Standorte.BereitschaftenFuellen(bhfVertex);
 
/*
 * Ansatz:
 * 1) für jeden Standort die Zeit zum 1.Abfahrtsort berechnen
 * 2) sortieren und den mit der kürzesten Anfahrt nehmen
 * 3) der fährt dann so lange, bis er gerade noch zurückkomtt
 * 4) mit der verbleibenden Strecke das gleiche nochmal
 */
        //Vorbereitung:
        ArrayList<Fahrten> zugangArrayList = new ArrayList<Fahrten>();
        //1) ALs Ü-Fahrt nehmen wir TempVonVertex => TempNachVertex
        for(Vertex abgangVertex:StandortListe) {
        	dijkstra = new DijkstraAlgorithm(graph);
            dijkstra.execute(abgangVertex); // von hier geht es los
            path = dijkstra.getPath(TempVonVertex);  // und hier steht der Zielknoten drin. Das Ergebnis ist eine Linked List des Weges
            Fahrten tempFahrten = new Fahrten( abgangVertex.toString() +"=>"+ TempVonVertex.toString(),
            		abgangVertex,
            		TempVonVertex,
            		dijkstra.getDuration(path));
            zugangArrayList.add(tempFahrten);
            System.out.println(tempFahrten.getID()+ tempFahrten.getFahrDauer());
        }
        
        
        ArrayList<Wege> Anfahrten = new ArrayList<Wege>();
        
 /*       for (Vertex Abgangsort : StandortListe ) {
        	System.out.println(Abgangsort.getName());
        	if(!Abgangsort.getName().toString().equals("KK")) {
        		System.out.println("Wir suchen jetz:"+ Abgangsort.getName()+ " -> KK");
        		Anfahrten.addAll(PfadFindenStrings(Abgangsort.getName(), "KK"));
        	}
        	//System.out.println("Fahrtdauer aus DijkstraAlgorithm :"+ dijkstra.getDuration(BhfFindenVertex("KK")));
        }
        // Für den Versuch ist die Überführungsfahrt von TS nach HH
        
        ArrayList<ArrayList<Integer>> anfahrtmatrixArrayList = new ArrayList<ArrayList<Integer>>(); 
  */    
        
        //1) Liste der Knoten für die Ü-Fahrt erstellen
         TempVonVertex = BhfFindenVertex("TS");
         TempNachVertex  = BhfFindenVertex("HH");

        // graph =new Graph(bhfVertex, verbindungEdges);

         dijkstra = new DijkstraAlgorithm(graph);
         dijkstra.execute(TempVonVertex); // von hier geht es los
         path = dijkstra.getPath(TempNachVertex);  // und hier steht der Zielknoten drin. Das Ergebnis ist eine Linked List des Weges
         
         // Array dimensionieren
         
         int[][] AnfahrtsArrayint = new int[StandortListe.size()][path.size()];
         int i =0;
         int j =0;

        for (Vertex heimatVertex : StandortListe ) {
        	j =0;
        
        	for(Vertex zielortVertex : path) {
        		System.out.println("Wir suchen jetz:"+ heimatVertex.getName()+" => "+ zielortVertex.getName());
        		if(!heimatVertex.equals(zielortVertex)) {
        			System.out.println("bin Hier");
        	         Graph tempgraph = new Graph(bhfVertex, verbindungEdges);

        	         DijkstraAlgorithm tempdijkstra = new DijkstraAlgorithm(tempgraph);
        	         tempdijkstra.execute(heimatVertex); // von hier geht es los
        	         LinkedList<Vertex> temppath = tempdijkstra.getPath(zielortVertex);  // und hier steht der Zielknoten drin. Das Ergebnis ist eine Linked List des Weges
        	         System.out.println(temppath);
        	         AnfahrtsArrayint[i][j] = tempdijkstra.getDuration(temppath);        		
	        	} else {
	        		AnfahrtsArrayint[i][j] =0;
				}
   	         System.out.println("Dauer von: "+ heimatVertex.getName()+" => "+ zielortVertex.getName()+" = "+ AnfahrtsArrayint[i][j]);

	        		j++;
	        }
        	i++;
        }
        	
        
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
 * 
 * Achtung: die Berechnung der Fahrtdauer scheint nicht zu stimmen!!
 */

