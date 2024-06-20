package Praktikum_3;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.*;

import static Praktikum_2.RandomWeightedGraph.loadString;

public class RandomEulerGraph {
   static Random random = new Random();
   static int edgeIdCounter = 0;

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = getEulerGraph(10);

        List<Node> eulerianPathHierholzer = Hierholzer.hierholzer(graph);
        System.out.println("Hierholzer Eulerian Path: " + eulerianPathHierholzer);

        Fleury.fleury(graph);
        graph.display();
    }

    public static Graph getEulerGraph(int nodes) {
        // Graph initialisieren
        Graph graph = new MultiGraph("Euler Graph");

        // Knoten hinzufügen
        for (int i = 0; i < nodes; i++) {
            Node addedNode = graph.addNode(String.valueOf(i));
            addedNode.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 20px;text-size:15px;");
            addedNode.setAttribute("ui.label", String.valueOf(i));
        }

        // Zufällige Kanten und Gewichte hinzufügen
        addRandomEulerEdges(graph);
        return graph;
    }

    private static void addRandomEulerEdges(Graph graph) {
        // Liste aller Knoten erstellen
        List<Node> nodes = new ArrayList<>();
        for (Node node : graph) {
            nodes.add(node);
        }

        // Initialer zusammenhängender Graph
        ensureGraphIsConnected(graph, nodes);

        // Sicherstellen, dass alle Knoten einen geraden Grad haben
        ensureAllNodesHaveEvenDegree(graph);
    }

    private static void ensureGraphIsConnected(Graph graph, List<Node> nodes) {
        // Erstelle einen zusammenhängenden Graphen
        Collections.shuffle(nodes);
        for (int i = 0; i < nodes.size() - 1; i++) {
            addEdgeWithWeight(graph, nodes.get(i), nodes.get(i + 1));
        }

        // Verbinde zufällige Knoten weiter, um die Verbindung zu stärken
        int additionalEdges = random.nextInt(nodes.size()); // Zusätzliche zufällige Kanten
        for (int i = 0; i < additionalEdges; i++) {
            Collections.shuffle(nodes);
            Node node1 = nodes.get(0);
            Node node2 = nodes.get(1);
            if (!node1.hasEdgeBetween(node2)) {
                addEdgeWithWeight(graph, node1, node2);
            }
        }
    }

    static void addEdgeWithWeight(Graph graph, Node node1, Node node2) {
        Edge edge = graph.addEdge(String.valueOf(edgeIdCounter++), node1, node2);
        int weight = random.nextInt(5) + 1;
        edge.setAttribute("weight", weight);
        edge.setAttribute("visited", false);
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
        while (oddDegreeNodes.size() >= 2) {
            Node node1 = oddDegreeNodes.remove(0);
            Node node2 = oddDegreeNodes.remove(0);
            addEdgeWithWeight(graph, node1, node2);
        }

        // Wenn eine ungerade Anzahl von Knoten vorhanden ist, die entfernt wurde
        if (oddDegreeNodes.size() == 1) {
            // Füge eine Schleife zu sich selbst hinzu, um den Grad gerade zu machen
            Node node = oddDegreeNodes.remove(0);
            addEdgeWithWeight(graph, node, node);
        }
    }


    // Alter code

/*
    public static Graph getEulerGraph(int nodes) {
        // Graph initialisieren
        Graph graph = new MultiGraph("Random Weighted Graph");
       // graph.setAttribute("ui.stylesheet", loadString("main.css"));

        // Knoten hinzufügen
        List<Node> nodesList = new ArrayList<>(nodes);
        for (int i = 0; i < nodes; i++) {
            Node addedNode = graph.addNode(String.valueOf(i));
            addedNode.setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 20px;text-size:15px;");
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

    public static void addEdgeWithWeight(Graph graph, Node node1, Node node2) {
        Edge edge = graph.addEdge(UUID.randomUUID().toString(), node1, node2);
        int weight = random.nextInt(5) + 1;
        //edge.setAttribute("ui.label", edgeIdCounter);
        edgeIdCounter++;
        edge.setAttribute("weight", weight);
        edge.setAttribute("visited", false);
    }

    private static void ensureAllNodesHaveEvenDegree(Graph graph) {
        List<Node> nodes = new ArrayList<>();
        for (Node node : graph) {
            nodes.add(node);
        }

        List<Node> oddDegreeNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.getDegree() % 2 != 0 || node.getDegree() == 0) {
                oddDegreeNodes.add(node);
            }
        }

        // Füge Kanten zwischen Knoten mit ungeradem Grad hinzu
        for (int i = 0; i < oddDegreeNodes.size(); i += 2) {
            if (i + 1 < oddDegreeNodes.size()) {
                addEdgeWithWeight(graph, oddDegreeNodes.get(i), oddDegreeNodes.get(i + 1));
            }
        }
    }*/
}
