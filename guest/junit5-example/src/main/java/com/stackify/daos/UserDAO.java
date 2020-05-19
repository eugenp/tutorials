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
            String createQuery = "CREATE TABLE users(email varchar(50) primary key, name varchar(50))";
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

    public void update(User user) {
        try (Connection con = ConnectionUtil.getConnection()) {

            String updateQuery = "UPDATE users SET name=? WHERE email=?";
            PreparedStatement pstmt = con.prepareStatement(updateQuery);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());

            pstmt.executeUpdate();
        } catch (SQLException exc) {
            logger.error(exc.getMessage());
        }
    }

    public void delete(User user) {
        try (Connection con = ConnectionUtil.getConnection()) {

            String deleteQuery = "DELETE FROM users WHERE email=?";
            PreparedStatement pstmt = con.prepareStatement(deleteQuery);
            pstmt.setString(1, user.getEmail());

            pstmt.executeUpdate();
        } catch (SQLException exc) {
            logger.error(exc.getMessage());
        }
    }

    public void delete(String email) {
        try (Connection con = ConnectionUtil.getConnection()) {

            String deleteQuery = "DELETE FROM users WHERE email=?";
            PreparedStatement pstmt = con.prepareStatement(deleteQuery);
            pstmt.setString(1, email);

            pstmt.executeUpdate();
        } catch (SQLException exc) {
            logger.error(exc.getMessage());
        }
    }

    public User findOne(String email) {
        User user = null;

        try (Connection con = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM users WHERE email=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
            }

        } catch (SQLException exc) {
            logger.error(exc.getMessage());
        }

        return user;
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

    public void deleteAll() {
        try (Connection con = ConnectionUtil.getConnection()) {

            String deleteQuery = "DELETE FROM users";
            PreparedStatement pstmt = con.prepareStatement(deleteQuery);

            pstmt.executeUpdate();
        } catch (SQLException exc) {
            logger.error(exc.getMessage());
        }
    }

}
