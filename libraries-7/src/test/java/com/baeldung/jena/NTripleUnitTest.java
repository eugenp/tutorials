package com.baeldung.jena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.SchemaDO;
import org.junit.jupiter.api.Test;

public class NTripleUnitTest {
    @Test
    void whenWeHaveAModel_ThenWeCanSerializeAsNTriples() {
        Model model = ModelFactory.createDefaultModel();

        model.createResource("/blog/posts/123")
            .addProperty(SchemaDO.headline, "Introduction to RDF and Apache Jena")
            .addProperty(SchemaDO.wordCount, "835")
            .addProperty(SchemaDO.author, model.createResource("/users/Baeldung")
                .addProperty(SchemaDO.name, "Baeldung")
                .addProperty(SchemaDO.url, "https://baeldung.com"))
            .addProperty(SchemaDO.comment, model.createResource("/blog/posts/123/comments/1")
                .addProperty(SchemaDO.text, "What a great article!")
                .addProperty(SchemaDO.author, model.createResource("/users/JoeBloggs")
                    .addProperty(SchemaDO.name, "Joe Bloggs")
                    .addProperty(SchemaDO.email, "joe.bloggs@example.com")));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        RDFDataMgr.write(out, model, Lang.NTRIPLES);

        assertEquals("""
            </blog/posts/123/comments/1> <https://schema.org/author> </users/JoeBloggs> .
            </blog/posts/123/comments/1> <https://schema.org/text> "What a great article!" .
            </users/Baeldung> <https://schema.org/url> "https://baeldung.com" .
            </users/Baeldung> <https://schema.org/name> "Baeldung" .
            </users/JoeBloggs> <https://schema.org/email> "joe.bloggs@example.com" .
            </users/JoeBloggs> <https://schema.org/name> "Joe Bloggs" .
            </blog/posts/123> <https://schema.org/comment> </blog/posts/123/comments/1> .
            </blog/posts/123> <https://schema.org/author> </users/Baeldung> .
            </blog/posts/123> <https://schema.org/wordCount> "835" .
            </blog/posts/123> <https://schema.org/headline> "Introduction to RDF and Apache Jena" .
            """, out.toString());
    }
}
