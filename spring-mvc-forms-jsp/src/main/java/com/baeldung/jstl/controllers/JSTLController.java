package com.baeldung.jstl.controllers;

import com.baeldung.jstl.dbaccess.SQLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.*;
import java.util.Calendar;

@Controller
public class JSTLController {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSTLController.class.getName());

    @Autowired
    ServletContext servletContext;

    @PostConstruct
    public void init() {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = SQLConnection.getConnection();
            preparedStatement = connection.prepareStatement("create table IF NOT EXISTS USERS " +
                    "( id int not null primary key AUTO_INCREMENT," +
                    " email VARCHAR(255) not null, first_name varchar (255), last_name varchar (255), registered DATE)");
            int status = preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS total FROM USERS;");
            ResultSet result = preparedStatement.executeQuery();
            if(result!=null) {
                result.next();
                if (result.getInt("total") == 0) {
                    generateDummy(connection);
                }
            } else {
                generateDummy(connection);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }

        }
    }

    @RequestMapping(value = "/core_tags", method = RequestMethod.GET)
    public ModelAndView coreTags(final Model model) {
        ModelAndView mv = new ModelAndView("core_tags");
        return mv;
    }

    @RequestMapping(value = "/core_tags_redirect", method = RequestMethod.GET)
    public ModelAndView coreTagsRedirect(final Model model) {
        ModelAndView mv = new ModelAndView("core_tags_redirect");
        return mv;
    }


    @RequestMapping(value = "/formatting_tags", method = RequestMethod.GET)
    public ModelAndView formattingTags(final Model model) {
        ModelAndView mv = new ModelAndView("formatting_tags");
        return mv;
    }

    @RequestMapping(value = "/sql_tags", method = RequestMethod.GET)
    public ModelAndView sqlTags(final Model model) {
        ModelAndView mv = new ModelAndView("sql_tags");
        return mv;
    }

    @RequestMapping(value = "/xml_tags", method = RequestMethod.GET)
    public ModelAndView xmlTags(final Model model) {
        System.out.println("dddddddddddddddddffffffffffffff");
        ModelAndView mv = new ModelAndView("xml_tags");
        return mv;
    }

    @RequestMapping(value = "/items_xml", method = RequestMethod.GET)
    @ResponseBody public FileSystemResource getFile(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/xml");
        return new FileSystemResource(new File(servletContext.getRealPath("/WEB-INF/items.xsl")));
    }

    @RequestMapping(value = "/function_tags", method = RequestMethod.GET)
    public ModelAndView functionTags(final Model model) {
        ModelAndView mv = new ModelAndView("function_tags");
        return mv;
    }


    private void generateDummy(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USERS " +
                "(email, first_name, last_name, registered) VALUES (?, ?, ?, ?);");
        preparedStatement.setString(1, "patrick@baeldung.com");
        preparedStatement.setString(2, "Patrick");
        preparedStatement.setString(3, "Frank");
        preparedStatement.setDate(4, new Date(Calendar.getInstance().getTimeInMillis()));
        preparedStatement.addBatch();
        preparedStatement.setString(1, "bfrank@baeldung.com");
        preparedStatement.setString(2, "Benjamin");
        preparedStatement.setString(3, "Franklin");
        preparedStatement.setDate(4, new Date(Calendar.getInstance().getTimeInMillis()));
        preparedStatement.executeBatch();
    }
}
