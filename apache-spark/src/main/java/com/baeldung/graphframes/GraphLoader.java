package com.baeldung.graphframes;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.graphframes.GraphFrame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GraphLoader {

    public JavaSparkContext getSparkContext() throws IOException {
        Path temp = Files.createTempDirectory("sparkGraphFrames");
        SparkConf sparkConf = new SparkConf().setAppName("SparkGraphX").setMaster("local[*]");
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
        javaSparkContext.setCheckpointDir(temp.toString());
        return javaSparkContext;
    }

    public GraphFrame getGraphFrameUserRelationship() throws IOException {
        Path temp = Files.createTempDirectory("sparkGraphFrames");
        SparkSession session = SparkSession.builder()
            .appName("SparkGraphFrameSample")
            .config("spark.sql.warehouse.dir", temp.toString())//"/file:C:/temp"
            .sparkContext(getSparkContext().sc())
            .master("local[*]")
            .getOrCreate();
        List<User> users = loadUsers();

        Dataset<Row> userDataset = session.createDataFrame(users, User.class);

        List<Relationship> relationshipsList = getRelations();
        Dataset<Row> relationshipDataset = session.createDataFrame(relationshipsList, Relationship.class);

        GraphFrame graphFrame = new GraphFrame(userDataset, relationshipDataset);

        return graphFrame;
    }

    public List<Relationship> getRelations() {
        List<Relationship> relationships = new ArrayList<>();
        relationships.add(new Relationship("Friend", "1", "2"));
        relationships.add(new Relationship("Following", "1", "4"));
        relationships.add(new Relationship("Friend", "2", "4"));
        relationships.add(new Relationship("Relative", "3", "1"));
        relationships.add(new Relationship("Relative", "3", "4"));

        return relationships;
    }

    private List<User> loadUsers() {
        User john = new User(1L, "John");
        User martin = new User(2L, "Martin");
        User peter = new User(3L, "Peter");
        User alicia = new User(4L, "Alicia");

        List<User> users = new ArrayList<>();

        users.add(new User(1L, "John"));
        users.add(new User(2L, "Martin"));
        users.add(new User(3L, "Peter"));
        users.add(new User(4L, "Alicia"));

        return users;
    }
}
