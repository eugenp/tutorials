package com.baeldung.graphx;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.graphx.Graph;
import org.apache.spark.graphx.VertexRDD;
import org.apache.spark.graphx.lib.PageRank;

import scala.Tuple2;

public class GraphAlgorithms {
    public static Map<Long, User> USERS = new HashMap<>();

    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.OFF);

        GraphLoader loader = new GraphLoader();
        Graph<User, Relationship> graph = loader.mapUserRelationship();

        Graph<Object, Object> pageRank = PageRank.run(graph, 20, 0.0001, GraphLoader.USER_TAG,
                GraphLoader.RELATIONSHIP_TAG);

        VertexRDD<Object> usersRDD = pageRank.vertices();

        System.out.println("---- PageRank: ");
        System.out.println("- Users Ranked ");
        usersRDD.toJavaRDD()
                .foreach((VoidFunction<Tuple2<Object, Object>>) tuple -> System.out.println(tuple.toString()));

        System.out.println("---- Connected Components: ");
        Graph<Object, Relationship> connectedComponents = graph.ops().connectedComponents();

        connectedComponents.vertices().toJavaRDD()
                .foreach((VoidFunction<Tuple2<Object, Object>>) tuple -> System.out.println(tuple.toString()));

        System.out.println("---- Triangle Count: ");
        Graph<Object, Relationship> triangleCount = graph.ops().triangleCount();

        triangleCount.vertices().toJavaRDD()
                .foreach((VoidFunction<Tuple2<Object, Object>>) tuple -> System.out.println(tuple.toString()));
    }
}
