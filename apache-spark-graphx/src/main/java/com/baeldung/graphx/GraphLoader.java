package com.baeldung.graphx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.graphx.Edge;
import org.apache.spark.graphx.Graph;
import org.apache.spark.storage.StorageLevel;

import scala.Function1;
import scala.Function2;
import scala.Predef;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;

public class GraphLoader {

    public static Map<Long, User> USERS = new HashMap<>();
    public static ClassTag<Relationship> RELATIONSHIP_TAG = ClassTag$.MODULE$.apply(Relationship.class);
    public static ClassTag<User> USER_TAG = ClassTag$.MODULE$.apply(User.class);

    public JavaSparkContext getSparkContext() {
        SparkConf sparkConf = new SparkConf().setAppName("SparkGraphX").setMaster("local[*]");
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
        return javaSparkContext;
    }

    public Graph<User, Relationship> mapUserRelationship() {
        JavaSparkContext javaSparkContext = getSparkContext();

        List<Edge<String>> edges = getEdges();

        JavaRDD<Edge<String>> edgeJavaRDD = javaSparkContext.parallelize(edges);

        ClassTag<String> stringTag = ClassTag$.MODULE$.apply(String.class);

        Graph<String, String> graph = Graph.fromEdges(edgeJavaRDD.rdd(), "Following", StorageLevel.MEMORY_ONLY(),
                StorageLevel.MEMORY_ONLY(), stringTag, stringTag);

        Graph<String, Relationship> relationshipGraph = graph.mapEdges(new MapRelationship(), RELATIONSHIP_TAG);
        Predef.$eq$colon$eq<String, User> eq = null;

        return relationshipGraph.mapVertices(new MapUser(), USER_TAG, eq);
    }

    public List<Edge<String>> getEdges() {
        List<Edge<String>> edges = new ArrayList<>();
        edges.add(new Edge<>(1L, 2L, "Friend"));
        edges.add(new Edge<>(1L, 4L, "Following"));
        edges.add(new Edge<>(2L, 4L, "Friend"));
        edges.add(new Edge<>(3L, 1L, "Relative"));
        edges.add(new Edge<>(3L, 4L, "Relative"));

        return edges;
    }

    public Map<Long, User> getUsers() {
        if (USERS.isEmpty()) {
            loadUsers();
        }

        return USERS;
    }

    private void loadUsers() {
        User john = new User(1L, "John");
        User martin = new User(2L, "Martin");
        User peter = new User(3L, "Peter");
        User alicia = new User(4L, "Alicia");

        USERS.put(1L, john);
        USERS.put(2L, martin);
        USERS.put(3L, peter);
        USERS.put(4L, alicia);
    }

    private static class MapRelationship implements Function1<Edge<String>, Relationship>, Serializable {

        @Override
        public Relationship apply(Edge<String> edge) {
            return new Relationship(edge.attr, new GraphLoader().getUsers().get(edge.srcId()), USERS.get(edge.dstId()));
        }
    }

    private static class MapUser implements Function2<Object, String, User>, Serializable {
        @Override
        public User apply(Object id, String name) {
            return new GraphLoader().getUsers().get((Long) id);
        }
    }
}
