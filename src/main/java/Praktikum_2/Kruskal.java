package Praktikum_2;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.graphicGraph.stylesheet.Color;
import java.util.*;
import java.util.stream.Collectors;

public class Kruskal {
    public static void kruskal(Graph graph) {
        // Kanten in eine Liste umwandeln und nach Gewicht sortieren
        List<Edge> edges = graph.edges()
                .sorted(Comparator.comparingInt(e -> e.getAttribute("weight", Integer.class)))
                .collect(Collectors.toList());

        // Union-Find Struktur initialisieren
        UnionFind uf = new UnionFind();

        // Anzeigen lassen
        for (Edge edge : edges) {
            Node node1 = edge.getNode0();
            Node node2 = edge.getNode1();

            // Union aufrufen, damit keine Zyklen entstehen
            if (uf.union(node1, node2)) {
                edge.setAttribute("mst", true);
                edge.setAttribute("ui.class", "mst");
            }
        }
        // Kantengewichtssumme berechnen lassen
        int totalWeight = Kruskal.calculateTotalWeight(graph);
        graph.setAttribute("totalWeight", totalWeight);
    }

    static int calculateTotalWeight(Graph mst) {
        int totalWeight = 0;

        // Die Edges, die gefärbt sind in Liste packen
        List<Edge> edges = mst.edges().filter(edge -> edge.hasAttribute("mst")).collect(Collectors.toList());

        // Weight ausrechnen
        for (Edge edge : edges) {
            totalWeight += edge.getAttribute("weight", Integer.class);
        }
        return totalWeight;
    }
}
