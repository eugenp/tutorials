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
import com.baeldung.cayenne.Employee;
import com.nhl.link.rest.DataResponse;
import com.nhl.link.rest.LinkRest;
import com.nhl.link.rest.SimpleResponse;

@Produces(MediaType.APPLICATION_JSON)
public class EmployeeSubResource {

    private Configuration config;

    private int departmentId;

    public EmployeeSubResource(int departmentId, Configuration config) {
        this.departmentId = departmentId;
        this.config = config;
    }

    public EmployeeSubResource() {
    }

    @GET
    public DataResponse<Employee> getAll(@Context UriInfo uriInfo) {
        return LinkRest.select(Employee.class, config).toManyParent(Department.class, departmentId, Department.EMPLOYEES).uri(uriInfo).get();
    }

    @GET
    @Path("{id}")
    public DataResponse<Employee> getOne(@PathParam("id") int id, @Context UriInfo uriInfo) {
        return LinkRest.select(Employee.class, config).toManyParent(Department.class, departmentId, Department.EMPLOYEES).byId(id).uri(uriInfo).getOne();
    }

    @POST
    public SimpleResponse create(String data) {

        return LinkRest.create(Employee.class, config).toManyParent(Department.class, departmentId, Department.EMPLOYEES).sync(data);
    }

    @PUT
    public SimpleResponse createOrUpdate(String data) {
        return LinkRest.create(Employee.class, config).toManyParent(Department.class, departmentId, Department.EMPLOYEES).sync(data);
    }

    @DELETE
    @Path("{id}")
    public SimpleResponse delete(@PathParam("id") int id) {
        return LinkRest.delete(Employee.class, config).toManyParent(Department.class, departmentId, Department.EMPLOYEES).id(id).delete();

    }

}
