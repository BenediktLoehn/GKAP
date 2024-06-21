package Praktikum_3;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.Test;
import org.graphstream.graph.Graph;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class HierholzerTest {

    private Random random = new Random();

    private boolean isEulerianCircuitValid(List<Node> circuit) {
        if (circuit == null || circuit.isEmpty()) return false;

        //all nodes have even degree?
        for (Node node : circuit) {
            if (node.getDegree() % 2 != 0) return false;
        }
        // circuit starts and ends at the same node?
        return circuit.getFirst().equals(circuit.getLast());
    }

    // Test für kleinen Eulergraphen
    @Test
    public void testSmallEulerGraph() {
        Graph graph = new SingleGraph("Small Euler Graph");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");

        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CD", "C", "D");
        graph.addEdge("AD", "A", "D");

        assertTrue(isEulerianCycle(graph, Hierholzer.hierholzer(graph)));
    }

    // Test für kleinen Graphen (kein Eulergraph)
    @Test
    public void testSmallNonEulerGraph() {
        Graph graph = new SingleGraph("Small Non-Euler Graph");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");

        assertFalse(isEulerianCycle(graph, Hierholzer.hierholzer(graph)));
    }

    // Test für kleinen Eulergraphen (nur ein Knoten)
    @Test
    public void testSingleEulerGraph() {
        Graph graph = new SingleGraph("Small Euler Graph");

        graph.addNode("A");

        assertTrue(isEulerianCycle(graph, Hierholzer.hierholzer(graph)));
    }

    // Test für kleinen Eulergraphen (nur ein Knoten mit Schlinge)
    @Test
    public void testSingleEulerGraphWithSchlinge() {
        Graph graph = new SingleGraph("Small Euler Graph");

        graph.addNode("A");

        graph.addEdge("AA", "A", "A");

        assertTrue(isEulerianCycle(graph, Hierholzer.hierholzer(graph)));
    }

    // Test für kleinen Eulergraphen (mit Mehrfachkanten)
    @Test
    public void testSingleEulerGraphWithMehr() {
        Graph graph = new MultiGraph("Small Euler Graph");

        graph.addNode("A");
        graph.addNode("B");

        graph.addEdge("AB", "A", "B");
        graph.addEdge("BA", "A", "B");

        assertTrue(isEulerianCycle(graph, Hierholzer.hierholzer(graph)));
    }

    // Test für kleineren random Graphen
    @Test
    public void testRandomEulerGraphsSmall() {
        Graph graph = RandomEulerGraph.getEulerGraph(10);
        assertTrue(isEulerianCycle(graph, Hierholzer.hierholzer(graph)));
    }

    // Test für größeren random Graphen
    @Test
    public void testRandomEulerGraphsBig() {
        Graph graph = RandomEulerGraph.getEulerGraph(100);
        assertTrue(isEulerianCycle(graph, Hierholzer.hierholzer(graph)));
    }

    // Test für große ungerichtete Graphen mit mehreren Durchläufen
    @Test
    public void testLargeEulerGraphs() {
        for (int i = 0; i < 20; i++) { // Test 20 verschiedene große Graphen
            int nodeCount = 50 + random.nextInt(50); // Graphen mit 50 bis 100 Knoten
            Graph graph = RandomEulerGraph.getEulerGraph(nodeCount);

            assertTrue(isEulerianCycle(graph, Hierholzer.hierholzer(graph)));
        }
    }

    public static boolean isEulerianCycle(Graph graph, List<Node> nodeSequence) {
        if (nodeSequence == null || nodeSequence.isEmpty()) {
            return false;
        }

        // Überprüfen, ob der Startknoten mit dem Endknoten übereinstimmt
        Node startNode = nodeSequence.get(0);
        Node endNode = nodeSequence.get(nodeSequence.size() - 1);
        if (!startNode.equals(endNode)) {
            return false;
        }

        // Kanten-Nutzung im Set tracken
        Set<Edge> usedEdges = new HashSet<>();

        for (int i = 0; i < nodeSequence.size() - 1; i++) {
            Node currentNode = nodeSequence.get(i);
            Node nextNode = nodeSequence.get(i + 1);
            Edge connectingEdge = findUnusedConnectingEdge(currentNode, nextNode, usedEdges);

            if (connectingEdge == null) {
                return false;
            }
            usedEdges.add(connectingEdge);
        }

        // Überprüfen, ob alle Kanten des Graphen genau einmal verwendet wurden
        return usedEdges.size() == getEdgeCount(graph);
    }

    // Hilfsmethode zum Finden einer ungenutzten Kante, die zwei Knoten verbindet
    private static Edge findUnusedConnectingEdge(Node from, Node to, Set<Edge> usedEdges) {
        for (Edge edge : from.edges().toList()) {
            if ((edge.getNode0().equals(from) && edge.getNode1().equals(to)) ||
                    (edge.getNode0().equals(to) && edge.getNode1().equals(from))) {
                if (!usedEdges.contains(edge)) {
                    return edge;
                }
            }
        }
        return null;
    }

    // Methode, um alle Kanten des Graphen zu zählen
    public static int getEdgeCount(Graph graph) {
        Set<Edge> uniqueEdges = new HashSet<>();
        for (Node node : graph.nodes().toList()) {
            uniqueEdges.addAll(node.edges().toList());
        }
        return uniqueEdges.size();
    }

}
