package Praktikum_2;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.graphicGraph.stylesheet.Color;
import java.util.*;
import java.util.stream.Collectors;
public class Prim {

    static Random rand = new Random();
    public static void prim(Graph graph) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> (int) n.getAttribute("minEdgeWeight")));
        for (Node node : graph) {
            node.setAttribute("minEdgeWeight", Integer.MAX_VALUE);
            node.setAttribute("parent", null);
            node.setAttribute("mst", false);
        }

        //Zufälliger Startknoten
        Node start = graph.getNode(rand.nextInt(graph.getNodeCount()) + 1);
        start.setAttribute("minEdgeWeight", 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            currentNode.setAttribute("mst", true);

            for (Edge edge : currentNode.edges().toList()) {
                Node neighborNode = edge.getOpposite(currentNode);
                int weight = (int) edge.getAttribute("weight");

                if (neighborNode.hasAttribute("mst") && weight < (Integer) neighborNode.getAttribute("minEdgeWeight")) {
                    neighborNode.setAttribute("minEdgeWeight", weight);
                    neighborNode.setAttribute("parent", currentNode);
                    neighborNode.setAttribute("mst", true);
                    pq.add(neighborNode);
                }
            }
        }
        for (Node node : graph) {
            Node parent = (Node) node.getAttribute("parent");
            if (parent != null) {
                Edge edge = node.getEdgeBetween(parent);
                if (edge != null) {
                    edge.setAttribute("mst", true);
                    edge.setAttribute("ui.class", "mst");
                }
            }
        }

        int totalWeight = Kruskal.calculateTotalWeight(graph);
        graph.setAttribute("totalWeight", totalWeight);
    }
}
