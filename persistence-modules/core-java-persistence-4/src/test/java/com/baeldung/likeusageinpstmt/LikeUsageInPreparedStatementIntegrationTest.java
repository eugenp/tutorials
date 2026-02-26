package com.baeldung.likeusageinpstmt;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LikeUsageInPreparedStatementIntegrationTest {

    private static JdbcDataSource ds;

    @BeforeAll
    static void setup() throws SQLException {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;");
        ds.setUser("sa");
        ds.setPassword("");
        // first create the messages table
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement("CREATE TABLE MESSAGES (ID INT PRIMARY KEY, CONTENT VARCHAR(255))")) {
            pstmt1.execute();
        }
        // Let's insert some test data
        try (Connection conn = ds.getConnection();
             // @formatter:off
             PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO MESSAGES (ID, CONTENT) VALUES " +
                 " (1, 'a hello message')," +
                 " (2, 'a long hello message')," +
                 " (3, 'We have spent 50% budget for marketing')," +
                 " (4, 'We have reached 50% of our goal')," +
                 " (5, 'We have received 50 emails')"
             )
             // @formatter:on
        ) {
            stmt2.executeUpdate();
        }
    }

    @Test
    void whenConcatenatingWildcardCharsInParamForLike_thenCorrect() throws SQLException {
        String keyword = "hello";
        try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement("SELECT ID, CONTENT FROM MESSAGES WHERE CONTENT LIKE ?")) {
            pstmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                List<String> contents = new ArrayList<>();
                while (rs.next()) {
                    contents.add(rs.getString("CONTENT"));
                }
                assertThat(contents).containsExactlyInAnyOrder("a hello message", "a long hello message");
            }
        }
    }

    @Test
    void whenUsingSqlConcatFunctionForLike_thenCorrect() throws SQLException {
        String keyword = "hello";
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT ID, CONTENT FROM MESSAGES WHERE CONTENT LIKE CONCAT('%', ?, '%')")) {
            pstmt.setString(1, keyword);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<String> contents = new ArrayList<>();
                while (rs.next()) {
                    contents.add(rs.getString("CONTENT"));
                }
                assertThat(contents).containsExactlyInAnyOrder("a hello message", "a long hello message");
            }

        }
    }

    @Test
    void whenKeywordContainsWildcardChar_thenIncorrect() throws SQLException {
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT ID, CONTENT FROM MESSAGES WHERE CONTENT LIKE CONCAT('%', ?, '%')")) {
            pstmt.setString(1, "50%");
            try (ResultSet rs = pstmt.executeQuery()) {
                List<String> contents = new ArrayList<>();
                while (rs.next()) {
                    contents.add(rs.getString("CONTENT"));
                }
                assertThat(contents).containsExactlyInAnyOrder(
                    // @formatter:off
                    "We have spent 50% budget for marketing",
                    "We have reached 50% of our goal",
                    "We have received 50 emails"); //<-- we do not expect this one
                // @formatter:on
            }

        }
    }

    String escapeLikeSpecialChars(String input) {
        return input.replace("!", "!!")
            .replace("%", "!%")
            .replace("_", "!_");
    }

    @Test
    void whenEscapeInSqlForLike_thenCorrect() throws SQLException {
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT ID, CONTENT FROM MESSAGES WHERE CONTENT LIKE ? ESCAPE '!'")) {

            pstmt.setString(1, "%" + escapeLikeSpecialChars("50%") + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                List<String> contents = new ArrayList<>();
                while (rs.next()) {
                    contents.add(rs.getString("CONTENT"));
                }
                assertThat(contents).containsExactlyInAnyOrder("We have spent 50% budget for marketing", "We have reached 50% of our goal");
            }
        }
    }

    @Test
    void whenEscapeInSqlWithConcatFunctionForLike_thenCorrect() throws SQLException {
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT ID, CONTENT FROM MESSAGES WHERE CONTENT LIKE CONCAT('%',?,'%') ESCAPE '!'")) {
            pstmt.setString(1, escapeLikeSpecialChars("50%"));
            try (ResultSet rs = pstmt.executeQuery()) {
                List<String> contents = new ArrayList<>();
                while (rs.next()) {
                    contents.add(rs.getString("CONTENT"));
                }
                assertThat(contents).containsExactlyInAnyOrder("We have spent 50% budget for marketing", "We have reached 50% of our goal");
            }
        }
    }
}