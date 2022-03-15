package com.om.hexArchEx;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/programsService")
public interface ProgramsService {
	
	@Path("/programs")
	@POST
	Response createPrograms(Programs programs);
		

	@Path("/programs")
	@GET
	public List<Programs> listPrograms();
}
