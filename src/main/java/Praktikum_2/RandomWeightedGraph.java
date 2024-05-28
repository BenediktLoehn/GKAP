package Praktikum_2;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class RandomWeightedGraph {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        // Methode mit Beispielwerten aufrufen
        Graph graph = getGraph(10,20);

        runKruskal(graph);
        runPrim(graph);
    }

    public static void runKruskal(Graph graph) {
        // Zeit vor der Ausführung des Kruskal-Algorithmus erfassen
        long startTime = System.currentTimeMillis();

        // Kruskal-Algorithmus anwenden
        Kruskal.kruskal(graph);

        // Zeit nach der Ausführung des Kruskal-Algorithmus erfassen
        long endTime = System.currentTimeMillis();

        // Laufzeit berechnen
        long ms = (endTime - startTime);

        // Laufzeit ausgeben
        System.out.println("Laufzeit des Kruskal-Algorithmus: " + ms/1000d + " Sekunden");

        // Kantengewichtsumme ausgeben
        System.out.println("Kantengewichtssumme des minimalen Spannbaums: " + graph.getAttribute("totalWeight", Integer.class));

        // Ergebnis anzeigen
        graph.display();
    }

    public static void runPrim(Graph graph) {
        // Zeit vor der Ausführung des Prim-Algorithmus erfassen
        long startTime = System.currentTimeMillis();

        // Prim-Algorithmus anwenden
        Prim.prim(graph);

        // Zeit nach der Ausführung des Prim-Algorithmus erfassen
        long endTime = System.currentTimeMillis();

        // Laufzeit berechnen
        long ms = (endTime - startTime);

        // Laufzeit ausgeben
        System.out.println("Laufzeit des Prim-Algorithmus: " + ms/1000d + " Sekunden");

        // Kantengewichtsumme ausgeben
        System.out.println("Kantengewichtssumme des minimalen Spannbaums: " + graph.getAttribute("totalWeight", Integer.class));

        // Ergebnis anzeigen
        graph.display();
    }

    // für css
    public static String loadString(String name) {

        try {
            URI resource = Objects.requireNonNull(RandomWeightedGraph.class.getClassLoader().getResource(name), String.format("resource with name '%s' not found", name)).toURI();
            return Files.readString(Path.of(resource));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Graph getGraph(int nodes, int edges) {
        // Graph initialisieren
        Graph graph = new MultiGraph("Random Weighted Graph");
        graph.setAttribute("ui.stylesheet", loadString("main.css"));

        // Knoten hinzufügen
        List<Node> nodesList = new ArrayList<>(nodes);
        for (int i = 0; i < nodes; i++) {
            nodesList.add(graph.addNode(String.valueOf(i)));
        }

        // Zufällige Kanten und Gewichte hinzufügen
        addRandomEdges(graph, nodesList, edges);
        return graph;
    }

    private static void addRandomEdges(Graph graph, List<Node> nodes, int numEdges) {
        Random random = new Random();

        // Edges setzen
        if (nodes.size() > 1) {
            for (int i = 0; i < numEdges; i++) {
                // Shuffeln für randomness
                Collections.shuffle(nodes);
                Edge edge = graph.addEdge(String.valueOf(i), nodes.get(0), nodes.get(1));
                // Random weight setzen
                int weight = random.nextInt(100)+1;
                edge.setAttribute("ui.label", weight);
                edge.setAttribute("weight", weight);
            }
        }

    }
}
