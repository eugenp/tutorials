package com.baeldung.graphql.server;

import com.baeldung.graphql.data.BookRepository;
import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends HttpServlet {

    private SimpleGraphQLHttpServlet graphQLServlet;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        graphQLServlet.service(req, resp);
    }

    @Override
    public void init() {
        GraphQLSchema schema = SchemaParser.newParser()
          .resolvers(new GraphQLQuery(new BookRepository()))
          .file("schema.graphqls")
          .build()
          .makeExecutableSchema();
        graphQLServlet = SimpleGraphQLHttpServlet
          .newBuilder(schema)
          .build();
    }
}
