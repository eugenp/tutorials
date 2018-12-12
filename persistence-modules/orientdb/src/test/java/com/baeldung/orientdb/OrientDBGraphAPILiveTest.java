package com.baeldung.orientdb;

import com.orientechnologies.orient.core.metadata.schema.OType;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class OrientDBGraphAPILiveTest {
    private static OrientGraphNoTx graph = null;

    // @BeforeClass
    public static void setup() {
        String orientDBFolder = System.getenv("ORIENTDB_HOME");
        graph = new OrientGraphNoTx("plocal:" + orientDBFolder + "/databases/BaeldungDB", "admin", "admin");
    }

    // @BeforeClass
    public static void init() {
        graph.createVertexType("Article");

        OrientVertexType writerType = graph.createVertexType("Writer");
        writerType.setStrictMode(true);
        writerType.createProperty("firstName", OType.STRING);
        writerType.createProperty("lastName", OType.STRING);
        writerType.createProperty("country", OType.STRING);

        OrientVertexType authorType = graph.createVertexType("Author", "Writer");
        authorType.createProperty("level", OType.INTEGER).setMax("3");

        OrientVertexType editorType = graph.createVertexType("Editor", "Writer");
        editorType.createProperty("level", OType.INTEGER).setMin("3");

        Vertex vEditor = graph.addVertex("class:Editor");
        vEditor.setProperty("firstName", "Maxim");
        vEditor.setProperty("lastName", "Mink's");
        vEditor.setProperty("country", "Cameroon");
        vEditor.setProperty("publicProfile", true);
        vEditor.setProperty("level", "7");

        Vertex vAuthor = graph.addVertex("class:Author");
        vAuthor.setProperty("firstName", "Jerome");
        vAuthor.setProperty("country", "Romania");
        vAuthor.setProperty("publicProfile", false);
        vAuthor.setProperty("level", "3");

        Vertex vArticle = graph.addVertex("class:Article");
        vArticle.setProperty("title", "Introduction to the OrientDB Java APIs.");
        vArticle.setProperty("priority", "High");
        vArticle.setProperty("type", "Article");
        vArticle.setProperty("level", "+L4");

        graph.addEdge(null, vAuthor, vEditor, "has");
        graph.addEdge(null, vAuthor, vArticle, "wrote");
    }

    // @Test
    public void givenBaeldungDB_checkWeHaveThreeRecords() {
        long size = graph.countVertices();

        assertEquals(3, size);
    }

    // @Test
    public void givenBaeldungDB_checkWeHaveTwoWriters() {
        long size = graph.countVertices("Writer");

        assertEquals(2, size);
    }

    // @Test
    public void givenBaeldungDB_getEditorWithLevelSeven() {
        String onlyEditor = "";
        for(Vertex v : graph.getVertices("Editor.level", 7)) {
            onlyEditor = v.getProperty("firstName").toString();
        }

        assertEquals("Maxim", onlyEditor);
    }

    // @AfterClass
    public static void closeDB() {
        graph.getRawGraph().getStorage().close(true, false);
    }
}
