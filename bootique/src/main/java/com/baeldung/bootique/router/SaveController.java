package com.baeldung.bootique.router;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.baeldung.bootique.service.HelloService;
import com.google.inject.Inject;

@Path("/save")
public class SaveController {

	@Inject
	HelloService helloService;
	
	@POST
	public String save() {
		return "Data Saved!";
	}
	
}
