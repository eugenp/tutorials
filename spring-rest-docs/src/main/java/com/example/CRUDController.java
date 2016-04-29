package com.example;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CRUDController {
	
	@RequestMapping(method=RequestMethod.GET)
	public ResourceSupport read() {
		ResourceSupport index = new ResourceSupport();
		index.add(linkTo(MyRestController.class).withRel("notes"));
		index.add(linkTo(MyRestController.class).withRel("tags"));
		return index;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResourceSupport save() {
		ResourceSupport index = new ResourceSupport();
		index.add(linkTo(MyRestController.class).withRel("notes"));
		index.add(linkTo(MyRestController.class).withRel("tags"));
		return index;
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResourceSupport update() {
		ResourceSupport index = new ResourceSupport();
		index.add(linkTo(MyRestController.class).withRel("notes"));
		index.add(linkTo(MyRestController.class).withRel("tags"));
		return index;
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public ResourceSupport delete() {
		ResourceSupport index = new ResourceSupport();
		index.add(linkTo(MyRestController.class).withRel("notes"));
		index.add(linkTo(MyRestController.class).withRel("tags"));
		return index;
	}

}
