package com.baeldung.server.rest;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.server.model.Employee;
import com.baeldung.server.repository.EmployeeRepository;

@Path("/employees")
public class EmployeeResource {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Employee getEmployee(@PathParam("id") int id) {
        return employeeRepository.getEmployee(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response updateEmployee(Employee employee, @PathParam("id") int id) {
        employeeRepository.updateEmployee(employee, id);
        return Response.status(Response.Status.OK.getStatusCode()).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteEmployee(@PathParam("id") int id) {
        employeeRepository.deleteEmployee(id);
        return Response.status(Response.Status.OK.getStatusCode()).build();
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addEmployee(Employee employee, @Context UriInfo uriInfo) {
        employeeRepository.addEmployee(new Employee(employee.getId(), employee.getFirstName()));
        return Response.status(Response.Status.CREATED.getStatusCode()).header("Location", String.format("%s/%s", uriInfo.getAbsolutePath().toString(), employee.getId())).build();
    }
}
