package com.baeldung.jersey.server;

import com.baeldung.jersey.server.model.Person;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/response")
public class Responder {

    @GET
    @Path("/ok")
    public Response getOkResponse() {

        String message = "This is a text response";

        return Response
          .status(Response.Status.OK)
          .entity(message)
          .build();
    }

    @GET
    @Path("/not_ok")
    public Response getNOkTextResponse() {

        String message = "There was an internal server error";

        return Response
          .status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(message)
          .build();
    }

    @GET
    @Path("/text_plain")
    public Response getTextResponseTypeDefined() {

        String message = "This is a plain text response";

        return Response
          .status(Response.Status.OK)
          .entity(message)
          .type(MediaType.TEXT_PLAIN)
          .build();
    }

    @GET
    @Path("/text_plain_annotation")
    @Produces({ MediaType.TEXT_PLAIN })
    public Response getTextResponseTypeAnnotated() {

        String message = "This is a plain text response via annotation";

        return Response
          .status(Response.Status.OK)
          .entity(message)
          .build();
    }

    @GET
    @Path("/pojo")
    public Response getPojoResponse() {

        Person person = new Person("Abh", "Nepal");

        return Response
          .status(Response.Status.OK)
          .entity(person)
          .build();
    }

    @GET
    @Path("/json")
    public Response getJsonResponse() {

        String message = "{\"hello\": \"This is a JSON response\"}";

        return Response
          .status(Response.Status.OK)
          .entity(message)
          .type(MediaType.APPLICATION_JSON)
          .build();
    }

    @GET
    @Path("/xml")
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?>" + "<hello> This is a xml response </hello>";
    }

    @GET
    @Path("/html")
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html> " + "<title>" + " This is a html title  </title>" + "<body><h1>" + " This is a html response body " + "</body></h1>" + "</html> ";
    }
}