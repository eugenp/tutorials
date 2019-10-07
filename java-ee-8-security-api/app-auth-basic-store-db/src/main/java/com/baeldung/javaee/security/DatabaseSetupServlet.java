package com.baeldung.javaee.security;

import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@DataSourceDefinition(
        name = "java:comp/env/jdbc/securityDS",
        className = "org.h2.jdbcx.JdbcDataSource",
        url = "jdbc:h2:~/securityTest;MODE=Oracle"
)
@WebServlet(value = "/init", loadOnStartup = 0)
public class DatabaseSetupServlet extends HttpServlet {

    @Resource(lookup = "java:comp/env/jdbc/securityDS")
    private DataSource dataSource;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @Override
    public void init() throws ServletException {
        super.init();
        initdb();
    }

    private void initdb() {
        executeUpdate(dataSource, "DROP TABLE IF EXISTS USERS");
        executeUpdate(dataSource, "DROP TABLE IF EXISTS GROUPS");

        executeUpdate(dataSource, "CREATE TABLE IF NOT EXISTS USERS(username VARCHAR(64) PRIMARY KEY, password VARCHAR(255))");
        executeUpdate(dataSource, "CREATE TABLE IF NOT EXISTS GROUPS(username VARCHAR(64), GROUPNAME VARCHAR(64))");

        executeUpdate(dataSource, "INSERT INTO USERS VALUES('admin', '" + passwordHash.generate("passadmin".toCharArray()) + "')");
        executeUpdate(dataSource, "INSERT INTO USERS VALUES('user', '" + passwordHash.generate("passuser".toCharArray()) + "')");

        executeUpdate(dataSource, "INSERT INTO GROUPS VALUES('admin', 'admin_role')");
        executeUpdate(dataSource, "INSERT INTO GROUPS VALUES('admin', 'user_role')");
        executeUpdate(dataSource, "INSERT INTO GROUPS VALUES('user', 'user_role')");
    }

    private void executeUpdate(DataSource dataSource, String query) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
