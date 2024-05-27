package Praktikum_1;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graphs {

    // Regex
    static Pattern LINE_MATCHER = Pattern.compile("(?<node1>.+?)(?:\\s*(?:(?<directed>->)|(?<undirected>--))\\s*(?<node2>.+?)\\s*(?:\\((?<edge>.+?)\\))?\\s*(?::\\s*(?<weight>\\d+?))?)?;", Pattern.DOTALL | Pattern.MULTILINE);

    // css
    public static String loadString(String name) throws Exception {
        final URI resource = Objects.requireNonNull(Graphs.class.getClassLoader().getResource(name), String.format("resource with name '%s' not found", name)).toURI();
        return Files.readString(Path.of(resource));
    }

    public static Graph erstelleGraph(String file) throws Exception {
        System.setProperty("org.graphstream.ui", "swing");
        Scanner scanner = new Scanner(Graphs.class.getResourceAsStream(file));
        // neuen Graphen erstellen und speichern
        Graph graph = new MultiGraph("graph", false, true);
        graph.setAttribute("ui.stylesheet", loadString("graph.css"));
        // file durchgehen
        while (scanner.hasNextLine()) {
            final Matcher matcher = LINE_MATCHER.matcher(scanner.nextLine());

            if (matcher.find()) {
                // ist der Graph gerichtet?
                boolean directed = matcher.group("directed") != null;
                // finde den ersten Knoten und setze ihn als source Knoten
                String node1 = matcher.group("node1");
                final Node source = graph.addNode(node1);
                source.setAttribute("ui.label", node1);

                // finde zweiten Knoten
                String node2 = matcher.group("node2");
                if (node2 != null) {
                    // setze zweiten Knoten als target Knoten
                    final Node target = graph.addNode(node2);
                    target.setAttribute("ui.label", node2);
                    // setze Kante von source zu target, boolean: directed oder undirected
                    final Edge sourceTargetEdge = graph.addEdge(UUID.randomUUID().toString(), source, target, directed);
                    // finde Kantenname
                    String edge = matcher.group("edge");
                    // erstelle passenden Kantenname
                    String labelForEdge = "";
                    if (edge != null) {
                        labelForEdge += "(" + edge + ") ";
                    }
                    // finde Kantengewicht und füge es hinzu
                    String weight = matcher.group("weight");
                    if (weight != null) {
                        labelForEdge += weight;
                    }
                    sourceTargetEdge.setAttribute("ui.label", labelForEdge);
                }
            }
        }
        return graph;
    }

    public static void main(String[] args) throws Exception {
        Graph graph = erstelleGraph("graph03.gka");
        BFS.bfs(graph, "Bremen", "Walsrode");
        // Visualisiere Graph
        graph.display();
    }
}
