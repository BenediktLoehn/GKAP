package Praktikum_3;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.*;

import static Praktikum_2.RandomWeightedGraph.loadString;

public class RandomEulerGraph {
   static Random random = new Random();

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = getEulerGraph(10);
        graph.display();

        List<String> eulerianPathHierholzer = Hierholzer.hierholzer(graph);
        System.out.println("Eulerian Path: " + eulerianPathHierholzer);
    }

    public static Graph getEulerGraph(int nodes) {
        // Graph initialisieren
        Graph graph = new MultiGraph("Random Weighted Graph");
        graph.setAttribute("ui.stylesheet", loadString("main.css"));

        // Knoten hinzufügen
        List<Node> nodesList = new ArrayList<>(nodes);
        for (int i = 0; i < nodes; i++) {
            Node addedNode = graph.addNode(String.valueOf(i));
            addedNode.setAttribute("ui.label", String.valueOf(i));
            nodesList.add(addedNode);
        }

        // Zufällige Kanten und Gewichte hinzufügen
        addRandomEulerEdges(graph, nodesList);
        return graph;
    }

    private static void addRandomEulerEdges(Graph graph, List<Node> nodes) {
        int numEdges = random.nextInt(nodes.size() * 2) + nodes.size(); // Zufällige Anzahl von Kanten, mindestens so viele wie Knoten
        int edgesAdded = 0;

        // Kanten zufällig hinzufügen
        while (edgesAdded < numEdges) {
            Collections.shuffle(nodes);
            Node node1 = nodes.get(0);
            Node node2 = nodes.get(1);

            if (!node1.hasEdgeBetween(node2)) {
                addEdgeWithWeight(graph, node1, node2);
                edgesAdded++;
            }
        }

        // Sicherstellen, dass alle Knoten am Ende einen geraden Grad haben
        ensureAllNodesHaveEvenDegree(graph);
    }

    private static void addEdgeWithWeight(Graph graph, Node node1, Node node2) {
        Edge edge = graph.addEdge(UUID.randomUUID().toString(), node1, node2);
        int weight = random.nextInt(5) + 1;
        edge.setAttribute("ui.label", weight);
        edge.setAttribute("weight", weight);
    }

    private static void ensureAllNodesHaveEvenDegree(Graph graph) {
        List<Node> nodes = new ArrayList<>();
        for (Node node : graph) {
            nodes.add(node);
        }

        List<Node> oddDegreeNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.getDegree() % 2 != 0) {
                oddDegreeNodes.add(node);
            }
        }

        // Füge Kanten zwischen Knoten mit ungeradem Grad hinzu
        for (int i = 0; i < oddDegreeNodes.size(); i += 2) {
            if (i + 1 < oddDegreeNodes.size()) {
                addEdgeWithWeight(graph, oddDegreeNodes.get(i), oddDegreeNodes.get(i + 1));
            }
        }
    }
}
