package Praktikum_2;

import org.graphstream.graph.*;
import java.util.*;

import static Praktikum_2.Kruskal.calculateTotalWeight;

public class Prim {

    static Random rand = new Random();
    public static void prim(Graph graph) {
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> (int) e.getAttribute("weight")));

        // Markiere alle Knoten als nicht besucht
        for (Node node : graph) {
            node.setAttribute("mst", false);
        }

        // Wähle einen zufälligen Startknoten
        Node start = graph.getNode(rand.nextInt(graph.getNodeCount()));
        start.setAttribute("mst", true);

        // Füge alle Kanten des Startknotens zur PriorityQueue hinzu
        pq.addAll(start.edges().toList());

        while (!pq.isEmpty()) {
            Edge minEdge = pq.poll();
            Node node1 = minEdge.getNode0();
            Node node2 = minEdge.getNode1();

            // Finde den Knoten, der noch nicht im MST ist
            Node newNode = null;
            if (!(boolean) node1.getAttribute("mst") && (boolean) node2.getAttribute("mst")) {
                newNode = node1;
            } else if ((boolean) node1.getAttribute("mst") && !(boolean) node2.getAttribute("mst")) {
                newNode = node2;
            }

            // Wenn wir keinen neuen Knoten gefunden haben, fahren wir fort
            if (newNode == null) continue;

            // Füge die Kante und den neuen Knoten zum MST hinzu
            newNode.setAttribute("mst", true);
            minEdge.setAttribute("mst", true);
            minEdge.setAttribute("ui.class", "mst");

            // Füge alle Kanten des neuen Knotens, die zu Knoten führen, die noch nicht im MST sind, zur PriorityQueue hinzu
            for (Edge edge : newNode.edges().toList()) {
                Node oppositeNode = edge.getOpposite(newNode);
                if (!(boolean) oppositeNode.getAttribute("mst")) {
                    pq.add(edge);
                }
            }
        }

        // Berechne das totale Gewicht des MST
        int totalWeight = calculateTotalWeight(graph);
        graph.setAttribute("totalWeight", totalWeight);
    }
}
