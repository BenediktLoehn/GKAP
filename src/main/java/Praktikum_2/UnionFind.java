package Praktikum_2;

import org.graphstream.graph.Node;

import java.util.*;

public class UnionFind {

    private List<Set<Node>> nodes = new ArrayList<>();

    public boolean union(Node left, Node right) {

        for (Set<Node> components : nodes) {
            if (components.contains(left) && components.contains(right)) {
                // Kante erzeugt Zyklus, nicht hinzufügen
                return false;
            } else if (components.contains(left) || components.contains(right)) {
                // Fallunterscheidung, habe ich bereits ein Set?
                Set<Node> foundSet = null;
                for (Set<Node> inner : nodes) {
                    if (inner == components) continue;
                    if (inner.contains(left) || inner.contains(right)) foundSet = inner;
                }
                if (foundSet != null) {
                    // Fall 1: Ist in anderem Set -> die zwei sets müssen gejoint werden
                    nodes.remove(foundSet);
                    components.addAll(foundSet);
                } else {
                    // Fall 2: Ist in keinem Set -> kann gefahrlos hinzugefügt werden
                    components.add(left);
                    components.add(right);
                }
                return true;
            }
        }
        // Es existiert noch gar kein Set -> neu erstellen
        nodes.add(new HashSet<>(List.of(left, right)));
        return true;
    }



}
