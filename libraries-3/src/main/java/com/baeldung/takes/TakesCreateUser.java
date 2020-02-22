package com.baeldung.takes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqForm;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.RsWithStatus;

public final class TakesCreateUser implements Take {
    
    public static Connection con;
    
    TakesCreateUser(Connection connection) {
        con = connection;
    }

    @Override
    public Response act(final Request req) throws IOException {
        
        RqForm form = new RqFormSmart(req);
        Iterable<String> idParam = form.param("id");
        Iterable<String> userParam = form.param("user");
        
        int id = Integer.parseInt(idParam.iterator().next());
        String user = userParam.iterator().next();
        
        final String INSERT_SQL_QUERY = "insert into take_user values ("+id+", "+"'"+user+"')";

        try (PreparedStatement pst = con.prepareStatement(INSERT_SQL_QUERY)) {
            System.out.println(pst.executeUpdate());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new RsWithStatus(200);
    }

}
