package com.baeldung.jgrapht;

import static org.junit.Assert.assertTrue;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Before;
import org.junit.Test;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;

public class GraphImageGenerationUnitTest {
    static DefaultDirectedGraph<String, DefaultEdge> g;

    @Before
    public void createGraph() throws IOException {
        File imgFile = new File("src/test/resources/graph.png");
        imgFile.createNewFile();
        g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        String x1 = "x1";
        String x2 = "x2";
        String x3 = "x3";
        g.addVertex(x1);
        g.addVertex(x2);
        g.addVertex(x3);
        g.addEdge(x1, x2);
        g.addEdge(x2, x3);
        g.addEdge(x3, x1);
    }

    @Test
    public void givenAdaptedGraph_whenWriteBufferedImage_ThenFileShouldExist() throws IOException {
        JGraphXAdapter<String, DefaultEdge> graphAdapter = new JGraphXAdapter<String, DefaultEdge>(g);
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());
        File imgFile = new File("src/test/resources/graph.png");
        BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        ImageIO.write(image, "PNG", imgFile);
        assertTrue(imgFile.exists());
    }
}