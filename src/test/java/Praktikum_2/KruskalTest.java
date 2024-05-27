package Praktikum_2;

import Praktikum_2.Kruskal;
import Praktikum_2.RandomWeightedGraph;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class KruskalTest {


    @Test
    public void positiveKruskal() throws Exception {
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
    public void noKruskal() throws Exception {
        Graph graph = new SingleGraph("");

        // Graph ohne Kantengewichtssumme
        graph.addNode("A" );
        graph.addNode("B" );
        graph.addNode("C" );
        graph.addNode("D" );
        graph.addNode("E" );

        Kruskal.kruskal(graph);

        int totalWeight = graph.getAttribute("totalWeight", Integer.class);
        assertEquals(0, totalWeight);
    }

    @Test
    public void testRandomGraph() throws Exception {
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