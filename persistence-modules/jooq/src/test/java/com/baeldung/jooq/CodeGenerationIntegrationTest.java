package com.baeldung.jooq;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.codegen.GenerationTool;
import org.jooq.impl.DSL;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.jooq.model.tables.Article;
import com.baeldung.jooq.model.tables.Author;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.io.FileUtils;

public class CodeGenerationIntegrationTest {

    static DSLContext context;

    @BeforeClass
    public static void setup() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:tes;INIT=CREATE SCHEMA IF NOT EXISTS \"public\"");
        context = DSL.using(conn, SQLDialect.H2);

        context.createTableIfNotExists(Author.AUTHOR)
            .columns(
                Author.AUTHOR.ID,
                Author.AUTHOR.FIRST_NAME,
                Author.AUTHOR.LAST_NAME,
                Author.AUTHOR.AGE
            )
            .execute();
        context.createTableIfNotExists(Article.ARTICLE)
            .columns(
                Article.ARTICLE.ID,
                Article.ARTICLE.TITLE,
                Article.ARTICLE.DESCRIPTION,
                Article.ARTICLE.AUTHOR_ID
            )
            .execute();
    }

    @AfterClass
    public static void cleanup() throws IOException {
        File generatedDirectory = new File("src/main/java/com/baeldung/jooq/generated");
        FileUtils.deleteDirectory(generatedDirectory);
    }

    @Test
    public void testClassGenerationFromExistingDatabase() throws Exception {

        File generatedDirectory = new File("src/main/java/com/baeldung/jooq/generated");

        assertFalse(generatedDirectory.exists());

        URL jooqConfigURL = getClass().getClassLoader().getResource("jooq-config.xml");
        assertNotNull(jooqConfigURL);
        File file = new File(jooqConfigURL.getFile());
        GenerationTool.generate(Files.readString(file.toPath()));

        assertTrue(generatedDirectory.exists());
    }
}
