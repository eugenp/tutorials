package com.baeldung.jena;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.SchemaDO;
import org.junit.jupiter.api.Test;

public class ModelUnitTest {
    @Test
    void whenWeHaveABlogModelThenWeCanAccessTheProperties() {
        Model model = ModelFactory.createDefaultModel();

        {
            Resource baeldung = model.createResource("/users/Baeldung")
                .addProperty(SchemaDO.name, "Baeldung")
                .addProperty(SchemaDO.url, "https://baeldung.com");

            Resource joeBloggs = model.createResource("/users/JoeBloggs")
                .addProperty(SchemaDO.name, "Joe Bloggs")
                .addProperty(SchemaDO.email, "joe.bloggs@example.com");

            Resource comment = model.createResource("/blog/posts/123/comments/1")
                .addProperty(SchemaDO.text, "What a great article!")
                .addProperty(SchemaDO.author, joeBloggs);

            Resource blogPost = model.createResource("/blog/posts/123");
            blogPost.addProperty(SchemaDO.headline, "Introduction to RDF and Apache Jena");
            blogPost.addProperty(SchemaDO.wordCount, "835");
            blogPost.addProperty(SchemaDO.author, baeldung);
            blogPost.addProperty(SchemaDO.comment, comment);
        }

        {
            Resource blogPost = model.getResource("/blog/posts/123");

            assertEquals("Introduction to RDF and Apache Jena", blogPost.getProperty(SchemaDO.headline).getString());

            Resource author = blogPost.getProperty(SchemaDO.author).getResource();
            assertEquals("Baeldung", author.getProperty(SchemaDO.name).getString());
        }
    }
}
