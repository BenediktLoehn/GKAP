import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

public class Main {

    public static void main(String args[]) throws Exception {
        System.setProperty("org.graphstream.ui", "swing");
        String graphString = "";
        try {
            URL url = Main.class.getResource("graph01.gka");
            if (url != null) {
                 List<String> graphStrings = Files.readAllLines(Paths.get(url.toURI()));
                graphString = String.join("\n", graphStrings);

            } else {
                System.err.println("Die Datei wurde nicht gefunden.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Graph String:");
        System.out.println(graphString);
        String regex = "(?<source>[^\\s]+) *(?<edge>--|->)? *(?<target>[^\\s]+)?(?: *\\((?<edgeName>[^)]+)\\))?(?:: *(?<weight>\\d+))?;";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(graphString);
        Graph graph = new SingleGraph("Tutorial 1");
        while (matcher.find()) {
            String source = matcher.group("source");
            String edge = matcher.group("edge");
            System.out.println("Source: " + source);
            if (graph.getNode(source) == null) {
                graph.addNode(source);
            }

            String target = matcher.group("target");
            System.out.println("Target: " + target);
            if (graph.getNode(target) == null) {
                graph.addNode(target);
            }
            boolean directed = edge != null && edge.equals("->");
            System.out.println("Edge: " + matcher.group("edge"));
            graph.addEdge(source + target, source, target, directed);
            System.out.println("Edge Name: " + matcher.group("edgeName"));
            System.out.println("Weight: " + matcher.group("weight") + "\n");
        }
        graph.display();
    }

}
