package com.baeldung.spring.jdbc.blob;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Types;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.support.SqlBinaryValue;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@Sql(value = "/com/baeldung/spring/jdbc/blob/create-document-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/com/baeldung/spring/jdbc/blob/drop-document-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = InsertBlobUsingJdbcTemplateApplication.class)
@TestPropertySource(locations = { "classpath:com/baeldung/spring/jdbc/blob/application.properties" })
class InsertBlobUsingJdbcTemplateUnitTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String CONTENT = "I am a very very long content.";

    @Test
    void whenUsingSetBytes_thenCorrect() {
        byte[] bytes = CONTENT.getBytes(StandardCharsets.UTF_8);
        //@formatter:off
        jdbcTemplate.update("INSERT INTO DOCUMENT (ID, FILENAME, DATA) VALUES (?, ?, ?)",
          1,
          "bigfile.txt",
          bytes
        );
        //@formatter:on

        byte[] stored = jdbcTemplate.queryForObject("SELECT DATA FROM DOCUMENT WHERE ID = 1", (rs, rowNum) -> rs.getBytes("data"));
        assertEquals(CONTENT, new String(stored, StandardCharsets.UTF_8));
    }

    @Test
    void whenUsingSetBinaryStream_thenCorrect() {
        InputStream stream = new ByteArrayInputStream(CONTENT.getBytes(StandardCharsets.UTF_8));

        //@formatter:off
        jdbcTemplate.update("INSERT INTO DOCUMENT (ID, FILENAME, DATA) VALUES (?, ?, ?)",
          2,
          "bigfile.txt",
          stream
        );
        //@formatter:on

        byte[] stored = jdbcTemplate.queryForObject("SELECT DATA FROM DOCUMENT WHERE ID = 2", (rs, rowNum) -> rs.getBytes("data"));
        assertEquals(CONTENT, new String(stored, StandardCharsets.UTF_8));

    }

    @Test
    void whenUsingLobHandler_thenCorrect() {
        byte[] bytes = CONTENT.getBytes(StandardCharsets.UTF_8);

        //@formatter:off
        jdbcTemplate.update("INSERT INTO DOCUMENT (ID, FILENAME, DATA) VALUES (?, ?, ?)",
            new Object[] { 3, "bigfile.txt", new SqlLobValue(bytes, new DefaultLobHandler()) },
            new int[] { Types.INTEGER, Types.VARCHAR, Types.BLOB }
        );
        //@formatter:on

        byte[] stored = jdbcTemplate.queryForObject("SELECT DATA FROM DOCUMENT WHERE ID = 3", (rs, rowNum) -> rs.getBytes("DATA"));
        assertEquals(CONTENT, new String(stored, StandardCharsets.UTF_8));
    }

    @Test
    void whenUsingSqlBinaryValue_thenCorrect() {
        byte[] bytes = CONTENT.getBytes(StandardCharsets.UTF_8);
        //@formatter:off
        jdbcTemplate.update("INSERT INTO DOCUMENT (ID, FILENAME, DATA) VALUES (?, ?, ?)",
            4,
            "bigfile.txt",
            new SqlParameterValue(Types.BLOB, new SqlBinaryValue(bytes))
        );
        //@formatter:on

        byte[] stored = jdbcTemplate.queryForObject("SELECT DATA FROM DOCUMENT WHERE ID = 4", (rs, rowNum) -> rs.getBytes("DATA"));
        assertEquals(CONTENT, new String(stored, StandardCharsets.UTF_8));
    }

}