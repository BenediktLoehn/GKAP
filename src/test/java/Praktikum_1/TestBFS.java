package Praktikum_1;

import Praktikum_1.BFS;
import Praktikum_1.Graphs;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TestBFS {

    @Test
    //Testen, ob der richige Path berechnet wird
    public void testGetPath() throws Exception {
        Graph graph = Graphs.erstelleGraph("graph03.gka");
        BFS.bfs(graph, "Bremen", "Walsrode");
        String path = BFS.getPath(graph.getNode("Walsrode"), true);
        assertEquals("Bremen -> Hamburg -> Walsrode", path);
    }

    @Test
        // Testet, ob die Anzahl der Kanten richtig ausgegeben wird
    void testEdgeCountPositive() throws Exception {
        Graph graph = Graphs.erstelleGraph("graph03.gka");
        assertNotNull(graph);
        int edgeCount = BFS.bfs(graph, "Bremen", "Walsrode");
        assertEquals(2, edgeCount);
    }

    @Test
        // Testet, ob ein nicht vorhandener Weg richtig ausgegeben wird
    void testEdgeCountNull() throws Exception {
        Graph graph = Graphs.erstelleGraph("graph01.gka");
        assertNotNull(graph);
        int edgeCount = BFS.bfs(graph, "n", "n");
        assertEquals(0, edgeCount);
    }
}
