package com.baeldung.graphframes;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.graphx.Edge;
import org.apache.spark.graphx.Graph;
import org.apache.spark.graphx.VertexRDD;
import org.graphframes.GraphFrame;
import scala.Tuple2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphExperiments {
    public static Map<Long, User> USERS = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Logger.getLogger("org").setLevel(Level.OFF);
        GraphLoader loader = new GraphLoader();
        GraphFrame graph = loader.getGraphFrameUserRelationship();

        GraphExperiments experiments = new GraphExperiments();
        experiments.doGraphFrameOperations(graph);
        experiments.doGraphFrameAlgorithms(graph);
    }

    private void doGraphFrameOperations(GraphFrame graph) {
        graph.vertices().show();
        graph.edges().show();

        graph.vertices().filter("name = 'Martin'").show();

        graph.filterEdges("type = 'Friend'")
            .dropIsolatedVertices().vertices().show();

        graph.degrees().show();
        graph.inDegrees().show();
        graph.outDegrees().show();
    }

    private void doGraphFrameAlgorithms(GraphFrame graph) {

        graph.pageRank().maxIter(20).resetProbability(0.15).run().vertices().show();

        graph.connectedComponents().run().show();

        graph.triangleCount().run().show();
    }

}
