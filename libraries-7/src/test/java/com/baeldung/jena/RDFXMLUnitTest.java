package com.baeldung.jena;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.SchemaDO;
import org.junit.jupiter.api.Test;

public class RDFXMLUnitTest {
    @Test
    void whenWeHaveAModel_ThenWeCanSerializeAsRDFXMLUsingRDFDataMgr() {
        Model model = ModelFactory.createDefaultModel();

        model.createResource("tag:baeldung:/blog/posts/123")
            .addProperty(SchemaDO.headline, "Introduction to RDF and Apache Jena")
            .addProperty(SchemaDO.wordCount, "835")
            .addProperty(SchemaDO.author, model.createResource("tag:baeldung:/users/Baeldung")
                .addProperty(SchemaDO.name, "Baeldung")
                .addProperty(SchemaDO.url, "https://baeldung.com"))
            .addProperty(SchemaDO.comment, model.createResource("tag:baeldung:/blog/posts/123/comments/1")
                .addProperty(SchemaDO.text, "What a great article!")
                .addProperty(SchemaDO.author, model.createResource("tag:baeldung:/users/JoeBloggs")
                    .addProperty(SchemaDO.name, "Joe Bloggs")
                    .addProperty(SchemaDO.email, "joe.bloggs@example.com")));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        RDFDataMgr.write(out, model, Lang.RDFXML);

        assertEquals("""
            <rdf:RDF
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:j.0="https://schema.org/">
              <rdf:Description rdf:about="tag:baeldung:/blog/posts/123">
                <j.0:comment>
                  <rdf:Description rdf:about="tag:baeldung:/blog/posts/123/comments/1">
                    <j.0:author>
                      <rdf:Description rdf:about="tag:baeldung:/users/JoeBloggs">
                        <j.0:email>joe.bloggs@example.com</j.0:email>
                        <j.0:name>Joe Bloggs</j.0:name>
                      </rdf:Description>
                    </j.0:author>
                    <j.0:text>What a great article!</j.0:text>
                  </rdf:Description>
                </j.0:comment>
                <j.0:author>
                  <rdf:Description rdf:about="tag:baeldung:/users/Baeldung">
                    <j.0:url>https://baeldung.com</j.0:url>
                    <j.0:name>Baeldung</j.0:name>
                  </rdf:Description>
                </j.0:author>
                <j.0:wordCount>835</j.0:wordCount>
                <j.0:headline>Introduction to RDF and Apache Jena</j.0:headline>
              </rdf:Description>
            </rdf:RDF>
            """, out.toString());
    }

    @Test
    void whenWeHaveAModel_ThenWeCanSerializeAsRDFXMLUsingWrite() {
        Model model = ModelFactory.createDefaultModel();

        model.createResource("tag:baeldung:/blog/posts/123")
            .addProperty(SchemaDO.headline, "Introduction to RDF and Apache Jena")
            .addProperty(SchemaDO.wordCount, "835")
            .addProperty(SchemaDO.author, model.createResource("tag:baeldung:/users/Baeldung")
                .addProperty(SchemaDO.name, "Baeldung")
                .addProperty(SchemaDO.url, "https://baeldung.com"))
            .addProperty(SchemaDO.comment, model.createResource("tag:baeldung:/blog/posts/123/comments/1")
                .addProperty(SchemaDO.text, "What a great article!")
                .addProperty(SchemaDO.author, model.createResource("tag:baeldung:/users/JoeBloggs")
                    .addProperty(SchemaDO.name, "Joe Bloggs")
                    .addProperty(SchemaDO.email, "joe.bloggs@example.com")));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        model.write(out);

        assertEquals("""
            <rdf:RDF
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:j.0="https://schema.org/">
              <rdf:Description rdf:about="tag:baeldung:/blog/posts/123">
                <j.0:comment>
                  <rdf:Description rdf:about="tag:baeldung:/blog/posts/123/comments/1">
                    <j.0:author>
                      <rdf:Description rdf:about="tag:baeldung:/users/JoeBloggs">
                        <j.0:email>joe.bloggs@example.com</j.0:email>
                        <j.0:name>Joe Bloggs</j.0:name>
                      </rdf:Description>
                    </j.0:author>
                    <j.0:text>What a great article!</j.0:text>
                  </rdf:Description>
                </j.0:comment>
                <j.0:author>
                  <rdf:Description rdf:about="tag:baeldung:/users/Baeldung">
                    <j.0:url>https://baeldung.com</j.0:url>
                    <j.0:name>Baeldung</j.0:name>
                  </rdf:Description>
                </j.0:author>
                <j.0:wordCount>835</j.0:wordCount>
                <j.0:headline>Introduction to RDF and Apache Jena</j.0:headline>
              </rdf:Description>
            </rdf:RDF>
            """, out.toString());
    }

    @Test
    void whenWeHaveRDFXML_thenWeCanParseThis() {
        String rdfxml = """
            <rdf:RDF
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:j.0="https://schema.org/">
              <rdf:Description rdf:about="tag:baeldung:/blog/posts/123">
                <j.0:comment>
                  <rdf:Description rdf:about="tag:baeldung:/blog/posts/123/comments/1">
                    <j.0:author>
                      <rdf:Description rdf:about="tag:baeldung:/users/JoeBloggs">
                        <j.0:email>joe.bloggs@example.com</j.0:email>
                        <j.0:name>Joe Bloggs</j.0:name>
                      </rdf:Description>
                    </j.0:author>
                    <j.0:text>What a great article!</j.0:text>
                  </rdf:Description>
                </j.0:comment>
                <j.0:author>
                  <rdf:Description rdf:about="tag:baeldung:/users/Baeldung">
                    <j.0:url>https://baeldung.com</j.0:url>
                    <j.0:name>Baeldung</j.0:name>
                  </rdf:Description>
                </j.0:author>
                <j.0:wordCount>835</j.0:wordCount>
                <j.0:headline>Introduction to RDF and Apache Jena</j.0:headline>
              </rdf:Description>
            </rdf:RDF>
            """;

        Model model = ModelFactory.createDefaultModel();
        model.read(new StringReader(rdfxml), null);

        Resource blogPost = model.getResource("tag:baeldung:/blog/posts/123");

        assertEquals("Introduction to RDF and Apache Jena", blogPost.getProperty(SchemaDO.headline).getString());

        Resource author = blogPost.getProperty(SchemaDO.author).getResource();
        assertEquals("Baeldung", author.getProperty(SchemaDO.name).getString());

    }
}
