package Praktikum_3;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class Hierholzer {

    static Random random = new Random();

    public static List<String> hierholzer(Graph graph) {
        List<String> eulerianPath = new ArrayList<>();
        Deque<Node> stack = new ArrayDeque<>();
        Map<String, List<Edge>> edges = new HashMap<>();

        for (Node node : graph) {
            edges.put(node.getId(), new ArrayList<>(node.edges().toList()));
        }

        Node startNode = graph.getNode(random.nextInt(graph.getNodeCount()));
        stack.push(startNode);

        while (!stack.isEmpty()) {
            Node current = stack.peek();
            List<Edge> currentEdges = edges.get(current.getId());

            if (currentEdges.isEmpty()) {
                eulerianPath.add(stack.pop().getId());
            } else {
                Edge edge = currentEdges.removeFirst();
                stack.push(edge.getOpposite(current));
            }
        }

        Collections.reverse(eulerianPath);
        return eulerianPath;
    }
}
