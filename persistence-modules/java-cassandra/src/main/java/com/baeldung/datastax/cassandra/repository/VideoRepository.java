package com.baeldung.datastax.cassandra.repository;

import com.baeldung.datastax.cassandra.domain.Video;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VideoRepository {

    private static final String TABLE_NAME = "videos";

    private final CqlSession session;

    public VideoRepository(CqlSession session) {
        this.session = session;
    }

    public void createTable() {
        createTable(null);
    }

    public void createTable(String keyspace) {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append(" (")
            .append("video_id UUID,")
            .append("title TEXT,")
            .append("creation_date TIMESTAMP,")
            .append("PRIMARY KEY(video_id));");

        String query = sb.toString();

        executeStatement(SimpleStatement.newInstance(query), keyspace);
    }

    public UUID insertVideo(Video video) {
        return insertVideo(video, null);
    }

    public UUID insertVideo(Video video, String keyspace) {
        UUID videoId = UUID.randomUUID();

        video.setId(videoId);

        String absoluteTableName = keyspace != null ? keyspace + "." + TABLE_NAME: TABLE_NAME;

        StringBuilder sb = new StringBuilder("INSERT INTO ").append(absoluteTableName)
            .append("(video_id, title, creation_date) values (:video_id, :title, :creation_date)");

        PreparedStatement preparedStatement = session.prepare(sb.toString());

        BoundStatement statement = preparedStatement.bind()
            .setUuid("video_id", video.getId())
            .setString("title", video.getTitle())
            .setInstant("creation_date", video.getCreationDate());

        session.execute(statement);

        return videoId;
    }

    public List<Video> selectAll() {
        return selectAll(null);
    }

    public List<Video> selectAll(String keyspace) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);

        String query = sb.toString();

        ResultSet resultSet = executeStatement(SimpleStatement.newInstance(query), keyspace);

        List<Video> result = new ArrayList<>();

        resultSet.forEach(x -> result.add(
            new Video(x.getUuid("video_id"), x.getString("title"), x.getInstant("creation_date"))
        ));

        return result;
    }

    private ResultSet executeStatement(SimpleStatement statement, String keyspace) {
        if (keyspace != null) {
            statement.setKeyspace(CqlIdentifier.fromCql(keyspace));
        }

        return session.execute(statement);
    }
}
