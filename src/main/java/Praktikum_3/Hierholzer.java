package Praktikum_3;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class Hierholzer {

    static Random random = new Random();

    public static List<Node> hierholzer(Graph graph) {
        // Initialisiere das visited-Attribut für alle Kanten
        for (Edge edge : graph.edges().toList()) {
            edge.setAttribute("visited", false);
        }

        // Zufälligen Startknoten wählen
        Node startNode = graph.getNode(random.nextInt(graph.getNodeCount()));

        List<Node> eulerianCircuit = new ArrayList<>();
        findEulerianCircuit(startNode, eulerianCircuit);
        return eulerianCircuit;
    }

    private static void findEulerianCircuit(Node currentNode, List<Node> circuit) {
        Stack<Node> stack = new Stack<>();
        Node current = currentNode;

        while (!stack.isEmpty() || current.edges().anyMatch(edge -> !(edge.getAttribute("visited", Boolean.class)))) {
            if (current.edges().anyMatch(edge -> !(edge.getAttribute("visited", Boolean.class)))) {
                stack.push(current);
                for (Edge edge : current.edges().toList()) {
                    if (!(edge.getAttribute("visited", Boolean.class))) {
                        edge.setAttribute("visited", true);
                        edge.setAttribute("ui.class", "visited");
                        current = edge.getOpposite(current);
                        break;
                    }
                }
            } else {
                // Wenn alle Kanten besucht wurden, Knoten zum Kreis hinzufügen und nächsten Knoten betrachten
                circuit.add(current);
                current = stack.pop();
            }
        }
        circuit.add(current); // Füge den Startknoten am Ende hinzu um den Kreis zu schließen
    }
}
