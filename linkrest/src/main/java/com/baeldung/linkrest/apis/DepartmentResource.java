package com.baeldung.linkrest.apis;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.baeldung.cayenne.Department;
import com.nhl.link.rest.DataResponse;
import com.nhl.link.rest.LinkRest;
import com.nhl.link.rest.SimpleResponse;

@Path("department")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    @Context
    private Configuration config;

    @GET
    public DataResponse<Department> getAll(@Context UriInfo uriInfo) {
        return LinkRest.select(Department.class, config).uri(uriInfo).get();
    }

    @GET
    @Path("{id}")
    public DataResponse<Department> getOne(@PathParam("id") int id, @Context UriInfo uriInfo) {
        return LinkRest.select(Department.class, config).byId(id).uri(uriInfo).getOne();
    }

    @POST
    public SimpleResponse create(String data) {
        return LinkRest.create(Department.class, config).sync(data);
    }

    @PUT
    public SimpleResponse createOrUpdate(String data) {
        return LinkRest.createOrUpdate(Department.class, config).sync(data);
    }

    @Path("{id}/employees")
    public EmployeeSubResource getEmployees(@PathParam("id") int id, @Context UriInfo uriInfo) {
        return new EmployeeSubResource(id, config);
    }

    @DELETE
    @Path("{id}")
    public SimpleResponse delete(@PathParam("id") int id) {
        return LinkRest.delete(Department.class, config).id(id).delete();
    }
}
