package com.baeldung.takes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonStructure;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.misc.Href;
import org.takes.rq.RqHref;
import org.takes.rs.RsJson;


public final class TakesReadUser implements Take {
    
    public static Connection con;
    
    TakesReadUser(Connection connection) {
        con = connection;
    }

    @Override
    public Response act(final Request req) throws IOException, SQLException {
        Href href = new RqHref.Base(req).href();
        Iterable<String> ids = href.param("id");
        int id = Integer.parseInt((String) ids.iterator().next());

        final String SELECT_SQL_QUERY = "select id, user from take_user where id = " + id;
        JsonStructure json = null;
        try (PreparedStatement pst = con.prepareStatement(SELECT_SQL_QUERY); ResultSet rs = pst.executeQuery();) {
            while (rs.next()) {
                json = Json.createObjectBuilder()
                    .add("id", rs.getInt("id"))
                    .add("user", rs.getString("user"))
                    .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return new RsJson(json);        
    }

}
