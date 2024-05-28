package Praktikum_2;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import static Praktikum_2.Kruskal.calculateTotalWeight;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class KruskalTest {

    @Test
    public void positiveKruskal() {
        Graph graph = new MultiGraph("");

        // Graph mit Kantengewichtssumme 8
        graph.addNode("A" );
        graph.addNode("B" );
        graph.addNode("C" );
        graph.addNode("D" );
        graph.addNode("E" );
        graph.addEdge("AB", "A", "B").setAttribute("weight", 5);
        graph.addEdge("BC", "B", "C").setAttribute("weight", 1);
        graph.addEdge("CD", "C", "D").setAttribute("weight", 1);
        graph.addEdge("DB", "D", "B").setAttribute("weight", 1);
        graph.addEdge("BE", "B", "E").setAttribute("weight", 1);

        Kruskal.kruskal(graph);

        int totalWeight = graph.getAttribute("totalWeight", Integer.class);
        assertEquals(8, totalWeight);

    }

    @Test
    public void noKruskal() {
        Graph graph = RandomWeightedGraph.getGraph(50, 0);
        Kruskal.kruskal(graph);
        assertEquals(0, calculateTotalWeight(graph));
    }

    @Test
    public void testRandomGraph() {
        // Random generieren
        Graph graph = RandomWeightedGraph.getGraph(1000, 4000);
        // Kruskal Referenz zum Testen
        org.graphstream.algorithm.Kruskal reference = new org.graphstream.algorithm.Kruskal();
        reference.init(graph);
        reference.compute();
        int referenceTotalWeight = (int) reference.getTreeWeight();

        // Eigene Implementierung
        Kruskal.kruskal(graph);

        int totalWeight = graph.getAttribute("totalWeight", Integer.class);
        assertEquals(referenceTotalWeight, totalWeight);
    }
}