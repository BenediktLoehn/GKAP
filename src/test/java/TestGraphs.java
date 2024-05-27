import Praktikum_1.Graphs;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGraphs {

    @Test
        // Testet, ob der Graph korrekt angezeigt und gespeichert wird
    void testGraphCreation() throws Exception {
        Graph graph = Graphs.erstelleGraph("graph03.gka");
        assertNotNull(graph);
        assertEquals(22, graph.getNodeCount());
        assertEquals(40, graph.getEdgeCount());
    }
    @Test
        //Testet, ob ein Graph aus einer nicht vorhandenen Datei nicht erstellt wird
    void testGraphCreationFailed() {
        assertThrows(NullPointerException.class, () -> Graphs.erstelleGraph("graph1000.gka"));
    }
}
