package Praktikum_3;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.junit.jupiter.api.Test;
import org.graphstream.graph.Graph;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class HierholzerTest {

    @Test
    public void positiveHierholzer() {
        Graph graph = RandomEulerGraph.getEulerGraph(20);
        List<Node> eulerianCircuit = Hierholzer.hierholzer(graph);

        assertNotNull(eulerianCircuit, "Eulerian circuit should not be null");
        assertFalse(eulerianCircuit.isEmpty(), "Eulerian circuit should not be empty");

        // Check if the circuit starts and ends at the same node
        assertEquals(eulerianCircuit.getFirst(), eulerianCircuit.getLast(), "Eulerian circuit should start and end at the same node");

        // Check if all edges were visited
        for (Edge edge : graph.edges().toList()) {
            assertTrue(edge.getAttribute("visited", Boolean.class), "All edges should be visited");
        }
    }

    @Test
    void testGraphWithOddDegreeNodes() {

        Graph graph = RandomEulerGraph.getEulerGraph(20);
        // Add an extra node with an odd degree to make the graph invalid for Eulerian circuit
        Node extraNode = graph.addNode("E");
        RandomEulerGraph.addEdgeWithWeight(graph, extraNode, graph.getNode(0));

        List<Node> eulerianCircuit = Hierholzer.hierholzer(graph);
        // The circuit should not be valid
        assertFalse(isEulerianCircuitValid(eulerianCircuit), "Graph with odd degree nodes should not have a valid Eulerian circuit");
    }

    private boolean isEulerianCircuitValid(List<Node> circuit) {
        if (circuit == null || circuit.isEmpty()) return false;

        for (Node node : circuit) {
            if (node.getDegree() % 2 != 0) return false;
        }
        return circuit.getFirst().equals(circuit.getLast());
    }
}
