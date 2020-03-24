package com.baeldung.jersey.server;

import javax.ws.rs.*;

@Path("items")
public class Items {

    @GET
    @Path("/cookie")
    public String readCookieParam(@CookieParam("cookieParamToRead") String cookieParamToRead) {
        return "Cookie parameter value is [" + cookieParamToRead + "]";
    }

    @GET
    @Path("/header")
    public String readHeaderParam(@HeaderParam("headerParamToRead") String headerParamToRead) {
        return "Header parameter value is [" + headerParamToRead + "]";
    }

    @GET
    @Path("/path/{pathParamToRead}")
    public String readPathParam(@PathParam("pathParamToRead") String pathParamToRead) {
        return "Path parameter value is [" + pathParamToRead + "]";
    }

    @GET
    @Path("/query")
    public String readQueryParam(@QueryParam("queryParamToRead") String queryParamToRead) {
        return "Query parameter value is [" + queryParamToRead + "]";
    }

    @POST
    @Path("/form")
    public String readFormParam(@FormParam("formParamToRead") String formParamToRead) {
        return "Form parameter value is [" + formParamToRead + "]";
    }

    @GET
    @Path("/matrix")
    public String readMatrixParam(@MatrixParam("matrixParamToRead") String matrixParamToRead) {
        return "Matrix parameter value is [" + matrixParamToRead + "]";
    }

    @POST
    @Path("/bean/{pathParam}")
    public String readBeanParam(@BeanParam ItemParam itemParam) {
        return itemParam.toString();
    }
}