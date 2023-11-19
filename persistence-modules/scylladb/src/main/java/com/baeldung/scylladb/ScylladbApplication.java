package com.baeldung.scylladb;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.insertInto;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.selectFrom;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createTable;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.scylladb.model.User;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.insert.InsertInto;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspaceStart;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTableStart;
import com.datastax.oss.driver.api.querybuilder.select.Select;

public class ScylladbApplication {

    private String keySpaceName;
    private String tableName;

    public ScylladbApplication(String keySpaceName, String tableName) {
        this.keySpaceName = keySpaceName;
        this.tableName = tableName;
        CreateKeyspaceStart createKeyspace = createKeyspace(keySpaceName);
        SimpleStatement keypaceStatement = createKeyspace.ifNotExists()
          .withSimpleStrategy(3)
          .build();

        CreateTableStart createTable = createTable(keySpaceName, tableName);
        SimpleStatement tableStatement = createTable.ifNotExists()
          .withPartitionKey("id", DataTypes.BIGINT)
          .withColumn("name", DataTypes.TEXT)
          .build();

        try (CqlSession session = CqlSession.builder().build()) {
            ResultSet rs = session.execute(keypaceStatement);
            if (null == rs.getExecutionInfo().getErrors() || rs.getExecutionInfo().getErrors().isEmpty()) {
                rs = session.execute(tableStatement);
            }
        }
    }

    public List<String> getAllUserNames() {
        List<String> userNames = new ArrayList<>();
        try (CqlSession session = CqlSession.builder().build()) {
            String query = String.format("select * from %s.%s",keySpaceName,tableName);
            ResultSet rs = session.execute(query);
            for (Row r : rs.all())
                userNames.add(r.getString("name"));
        }
        return userNames;
    }

    public List<User> getUsersByUserName(String userName) {
        List<User> userList = new ArrayList<>();
        try (CqlSession session = CqlSession.builder().build()) {
            Select query = selectFrom(keySpaceName, tableName).all()
              .whereColumn("name")
              .isEqualTo(literal(userName))
              .allowFiltering();
	        SimpleStatement statement = query.build();
            ResultSet rs = session.execute(statement);
            for (Row r : rs)
                userList.add(new User(r.getLong("id"), r.getString("name")));
        }
        return userList;
    }

    public boolean addNewUser(User user) {
        boolean response = false;
        try (CqlSession session = CqlSession.builder().build()) {
            InsertInto insert = insertInto(keySpaceName, tableName);
            SimpleStatement statement = insert.value("id", literal(user.getId()))
              .value("name", literal(user.getName()))
              .build();
            ResultSet rs = session.execute(statement);
            response = null == rs.getExecutionInfo().getErrors() || rs.getExecutionInfo().getErrors().isEmpty();
        }
        return response;
    }

}
