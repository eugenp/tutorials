package com.example;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;

@RestController
@RequestMapping("/crud")
public class CRUDController {
	
	@RequestMapping(method=RequestMethod.GET)
	public List<CrudInput> read() {
		List<CrudInput> returnList=new ArrayList<CrudInput>();
		return returnList;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method=RequestMethod.POST)
	public HttpHeaders save(@RequestBody CrudInput crudInput) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(linkTo(CRUDController.class).slash(crudInput.getTitle()).toUri());
		return httpHeaders;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	void delete(@PathVariable("id") long id) {
		
	}
}
