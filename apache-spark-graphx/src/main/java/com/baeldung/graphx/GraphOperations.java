package com.baeldung.graphx;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.graphx.Edge;
import org.apache.spark.graphx.Graph;
import org.apache.spark.graphx.VertexRDD;

import scala.Tuple2;

public class GraphOperations {
    public static Map<Long, User> USERS = new HashMap<>();

    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.OFF);
        GraphOperations operations = new GraphOperations();
        operations.doOperations();
    }

    private void doOperations() {
        GraphLoader loader = new GraphLoader();
        Graph<User, Relationship> userGraph = loader.mapUserRelationship();

        System.out.println("Mapped Users: ");
        userGraph.vertices().toJavaRDD().foreach((VoidFunction<Tuple2<Object, User>>) tuple -> System.out
                .println("id: " + tuple._1 + " name: " + tuple._2));

        System.out.println("Mapped Relationships: ");
        userGraph.edges().toJavaRDD()
                .foreach((VoidFunction<Edge<Relationship>>) edge -> System.out.println(edge.attr().toString()));

        VertexRDD<Object> degreesVerticesRDD = userGraph.ops().degrees();
        VertexRDD<Object> inDegreesVerticesRDD = userGraph.ops().inDegrees();
        VertexRDD<Object> outDegreesVerticesRDD = userGraph.ops().outDegrees();

        System.out.println("degrees: ");
        degreesVerticesRDD.toJavaRDD().foreach((VoidFunction<Tuple2<Object, Object>>) tuple -> System.out
                .println("id: " + tuple._1 + " count: " + tuple._2));

        System.out.println("inDegrees: ");
        inDegreesVerticesRDD.toJavaRDD().foreach((VoidFunction<Tuple2<Object, Object>>) tuple -> System.out
                .println("id: " + tuple._1 + " count: " + tuple._2));

        System.out.println("outDegrees: ");
        outDegreesVerticesRDD.toJavaRDD().foreach((VoidFunction<Tuple2<Object, Object>>) tuple -> System.out
                .println("id: " + tuple._1 + " count: " + tuple._2));
    }

}
