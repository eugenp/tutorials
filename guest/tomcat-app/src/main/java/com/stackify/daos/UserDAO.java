package com.stackify.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.stackify.models.User;
import com.stackify.utils.ConnectionUtil;

public class UserDAO {

    private Logger logger = LogManager.getLogger(UserDAO.class);

    public void createTable() {
        try (Connection con = ConnectionUtil.getConnection()) {
            String createQuery = "CREATE TABLE IF NOT EXISTS users(email varchar(50) primary key, name varchar(50))";
            PreparedStatement pstmt = con.prepareStatement(createQuery);

            pstmt.execute();
        } catch (SQLException exc) {
            logger.error(exc.getMessage());
        }

    }

    public void add(User user) {
        try (Connection con = ConnectionUtil.getConnection()) {

            String insertQuery = "INSERT INTO users(email,name) VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getName());

            pstmt.executeUpdate();
        } catch (SQLException exc) {
            logger.error(exc.getMessage());
        }
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection con = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM users";
            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                users.add(user);
            }
        } catch (SQLException exc) {
            logger.error(exc.getMessage());
        }

        return users;
    }

}
