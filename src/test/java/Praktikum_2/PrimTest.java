package Praktikum_2;

import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;
import static Praktikum_2.Kruskal.calculateTotalWeight;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimTest {

    @Test
    public void positivePrim() {
        Graph graph = RandomWeightedGraph.getGraph(50, 100);
        Prim.prim(graph);
        int totalWeight = graph.getAttribute("totalWeight", Integer.class);
        assertEquals(totalWeight, calculateTotalWeight(graph));
    }

    @Test
    public void zeroPrim() {
        Graph graph = RandomWeightedGraph.getGraph(50, 0);
        Prim.prim(graph);
        assertEquals(0, calculateTotalWeight(graph));
    }

    @Test
    public void testRandomGraph() {
        // Random generieren
        Graph graph = RandomWeightedGraph.getGraph(1000, 4000);
        // Prim Referenz zum Testen
        org.graphstream.algorithm.Prim primReference = new org.graphstream.algorithm.Prim();
        primReference.init(graph);
        primReference.compute();
        int referenceTotalWeight = (int) primReference.getTreeWeight();

        // Eigene Implementierung
        Prim.prim(graph);

        int totalWeight = graph.getAttribute("totalWeight", Integer.class);
        assertEquals(referenceTotalWeight, totalWeight);
    }
}
