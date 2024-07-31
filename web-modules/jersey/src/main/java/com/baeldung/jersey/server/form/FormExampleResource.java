package com.baeldung.jersey.server.form;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Path("form")
public class FormExampleResource
{
    @GET
    @Path("/example1")
    @Produces({MediaType.TEXT_HTML})
    public InputStream getExample1() throws Exception
    {
        File f = new File("src/main/resources/html/example1.html");
        return new FileInputStream(f);
    }

    @GET
    @Path("/example2")
    @Produces({MediaType.TEXT_HTML})
    public InputStream getExample2() throws Exception
    {
        File f = new File("src/main/resources/html/example2.html");
        return new FileInputStream(f);
    }

    @POST
    @Path("/example1")
    public String example1(@FormParam("first_name") String firstName,
                           @FormParam("last_name") String lastName,
                           @FormParam("age") String age)
    {
        return "Got: First = " + firstName + ", Last = " + lastName + ", Age = " + age;
    }

    @POST
    @Path("/example2")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String example2(@FormDataParam("first_name") String firstName,
                           @FormDataParam("last_name") String lastName,
                           @FormDataParam("age") String age,
                           @FormDataParam("photo") InputStream photo)
            throws Exception
    {
        int len;
        int size = 1024;
        byte[] buf;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        buf = new byte[size];
        while ((len = photo.read(buf, 0, size)) != -1)
            bos.write(buf, 0, len);
        buf = bos.toByteArray();
        return "Got: First = " + firstName + ", Last = " + lastName + ", Age = " + age + ", Photo (# of bytes) = " + buf.length;
    }
}
