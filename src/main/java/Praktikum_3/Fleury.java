package Praktikum_3;

import Praktikum_1.BFS;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class Fleury {
    public static List<Node> fleury(Graph graph) {
        // Set für schon besuchte Edges
        final Set<String> visitedEdges = new HashSet<>();
        // Ergebnisliste
        final List<Node> eulerKreis = new ArrayList<>();
        // StartNode setzen
        final Node startNode = graph.getNode(0);

        Node currentNode = startNode;
        // solange bis alle Edges besucht wurden sind
        while (visitedEdges.size() < graph.getEdgeCount()) {
            // für Lambda-Funktion weiter unten
            Node finalCurrentNode = currentNode;

            Edge choosenEdge = currentNode.edges()
                    // entferne bereits besuchte Kanten
                    .filter(edge -> !visitedEdges.contains(edge.getId()))
                    // entferne Kanten, die Brücken sind
                    .filter(edge -> !isBridge(graph, edge, visitedEdges))
                    // wähle erste Kante
                    .findFirst()
                    //  für falls es nur noch Brücken gibt
                    .orElse(currentNode
                            .edges()
                            .filter(edge -> !visitedEdges.contains(edge.getId()))
                            .findAny()
                            // falls nichts gefunden wird: RuntimeException mit Infos
                            .orElseThrow(() -> new RuntimeException(
                                    // gibt Kreis bis zum Fehler aus, plus Problemknoten, dessen Kanten und die unbesuchten Kanten
                                    System.lineSeparator() + "Eulerian Path: " + eulerKreis + System.lineSeparator() +
                                            "node:" + finalCurrentNode.getId() + System.lineSeparator() +
                                            "TotalEdgeCount:" + finalCurrentNode.edges().toList().size() + System.lineSeparator() +
                                            "AvailEdges:" + finalCurrentNode.edges().filter(edge -> !visitedEdges.contains(edge.getId())).toList().size()
                            )));
            // füge aktuellen Knoten zur ergebnisliste hinzu
            eulerKreis.add(currentNode);
            // aktualisiere currentNode auf Nachbarknoten
            currentNode = graph.getEdge(choosenEdge.getId()).getOpposite(currentNode);
            // füge Kante zum Set hinzu
            visitedEdges.add(choosenEdge.getId());
        }
        // Abschluss des Kreises
        eulerKreis.add(startNode);
        System.out.println("Fleury Eulerian Path: " + eulerKreis);
        return eulerKreis;

        // Geht alle Kanten durch und setzt Kantennummer
        /*for (int i = 0; i < eulerKreis.size() - 1; i++) {
            Node source = eulerKreis.get(0);
            Node target = eulerKreis.get(1);
            Edge edge = source.getEdgeBetween(target);
            edge.setAttribute("ui.label", i + 1);
        }*/
    }
    private static boolean isBridge(Graph graph, Edge edge, Set<String> visitedEdges) {
        // Entferne vorübergehend alle bereits besuchten Kanten aus dem Graphen
        List<Edge> removed = new ArrayList<>();
        for (String edge1 : visitedEdges) {
            removed.add(graph.removeEdge(edge1));
        }

        // Entferne vorübergehend die zu prüfende Kante aus dem Graphen
        graph.removeEdge(edge);

        // Prüfe die Konnektivität mit BFS
        boolean isConnected = bfsCheck(edge.getSourceNode(), edge.getTargetNode());

        // Füge die Kante wieder in den Graphen ein
        graph.addEdge(edge.getId(), edge.getSourceNode(), edge.getTargetNode());

        // Füge die anderen entfernten Kanten wieder ein
        for (Edge removedEdge : removed) {
            graph.addEdge(removedEdge.getId(), removedEdge.getSourceNode(), removedEdge.getTargetNode());
        }

        // Eine Kante ist eine Brücke, wenn das Entfernen sie trennt
        return !isConnected;
    }

    // BFS-Check für Konnektivität
    private static boolean bfsCheck(Node source, Node target) {
        // Set und Queue fürs Abarbeiten
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        // füge source Knoten zu beiden hinzu
        queue.add(source);
        visited.add(source);

        // während abzuarbeitende Queue noch nicht leer ist
        while (!queue.isEmpty()) {
            // hol dir aktuellen Knoten
            Node current = queue.poll();
            // gehe Liste von Kanten des Knotens durch
            for (Edge edge : current.edges().toList()) {
                // hole dir Nachbarsknoten und prüfe, ob der target ist
                Node neighbor = edge.getOpposite(current);
                if (!visited.contains(neighbor)) {
                    if (neighbor.equals(target)) {
                        return true; // Ziel gefunden
                    }
                    // füge nächsten Knoten hinzu
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return false; // Ziel nicht erreichbar
    }
}




