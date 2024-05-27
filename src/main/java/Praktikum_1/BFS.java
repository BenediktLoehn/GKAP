package Praktikum_1;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;
import java.util.stream.Collectors;

public class BFS {
    // zum Zählen der Kanten
    static int edgeCount = 0;
    public static int bfs(Graph graph, String start, String end) {
        // Set für schon besuchte Knoten
        Set<Node> visitedNodes = new HashSet<>();
        // Queue für Knoten zum Besuchen
        Queue<Node> nodesToExplore = new ArrayDeque<>();
        // Start- und Endknoten
        Node startNode = graph.getNode(start);
        Node endNode = graph.getNode(end);

        // Startknoten mit 0 markieren und zu Set und Queue hinzufügen
        startNode.setAttribute("len", 0);
        nodesToExplore.add(startNode);
        visitedNodes.add(startNode);

        while (!nodesToExplore.isEmpty()) {
            // aktueller Knoten aus Queue rausholen
            Node current = nodesToExplore.poll();
            // Endknoten erreicht?
            if (current == endNode) break;

            for (Node neighbour : current.neighborNodes().collect(Collectors.toList())) {
                // Nachbarknoten finden und zur Queue hinzufügen
                if (current.hasEdgeToward(neighbour)) {
                    if (!visitedNodes.contains(neighbour)) {
                        nodesToExplore.add(neighbour);
                        // Nachbarsknoten mit nächster Zahl markieren
                        int i = current.getAttribute("len", Integer.class) + 1;
                        neighbour.setAttribute("len", i);
                        // Aktueller Knoten dem Nachbarknoten als Vorgänger setzen
                        neighbour.setAttribute("prev", current);
                    }
                    // Nachbarsknoten zum Set
                    visitedNodes.add(neighbour);
                }
            }
        }
        if (!visitedNodes.contains(endNode)) {
            return -1; // oder eine andere Markierung, um anzuzeigen, dass der Endknoten nicht erreicht wurde
        }
        // Path und Anzahl Kanten ausgeben
        System.out.println(getPath(endNode, true));
        System.out.format("Anzahl Kanten: %d", edgeCount);
        return edgeCount;
    }

    public static String getPath(Node current, boolean start) {
        // Startknoten
        if ( current.getAttribute("len", Integer.class) == null || current.getAttribute("len", Integer.class) == 0) return String.format("%s -> ", current.getId());
        // Vorgänger bekommen
        final Node prev = current.getAttribute("prev", Node.class);
        // Weg zwischen beiden Knoten finden und einfärben
        final Edge edgeBetween = current.getEdgeBetween(prev);
        edgeBetween.setAttribute("ui.style", "fill-color: rgb(0,100,255);");
        // Zähle einen hoch pro Kante
        edgeCount++;
        // Path von Start- zu Endknoten ausgeben
        return getPath(prev, false) + (start ? String.format("%s", current.getId()) : String.format("%s -> ", current.getId()));
    }
}


