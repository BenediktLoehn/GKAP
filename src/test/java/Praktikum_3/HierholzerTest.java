package Praktikum_3;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.junit.jupiter.api.Test;
import org.graphstream.graph.Graph;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HierholzerTest {

    @Test
    public void positiveHierholzer() {
        Graph graph = RandomEulerGraph.getEulerGraph(20);
        List<Node> eulerianCircuit = Hierholzer.hierholzer(graph);

        assertNotNull(eulerianCircuit);
        assertFalse(eulerianCircuit.isEmpty());


        // Check if all edges were visited
        List<Edge> visitedEdges = new ArrayList<>();
        for (Edge edge : graph.edges().toList()) {
            assertTrue(edge.getAttribute("visited", Boolean.class));
            visitedEdges.add(edge);
        }
        Set<Edge> visitedEdgesSet = new HashSet<>(visitedEdges);
        assertEquals(visitedEdges.size(), visitedEdgesSet.size()); //no edge should be visited twice
        assertTrue(isEulerianCircuitValid(eulerianCircuit));
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

        //all nodes have even degree?
        for (Node node : circuit) {
            if (node.getDegree() % 2 != 0) return false;
        }
        // circuit starts and ends at the same node?
        return circuit.getFirst().equals(circuit.getLast());
    }
}
