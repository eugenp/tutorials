package com.baeldung.springdoc.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.springdoc.demo.model.Topic;


@RestController
@RequestMapping(path = "/")
public class TopicsController {
	
	List<Topic> topics = new ArrayList<Topic>() {{
		add(new Topic(1, "Topic1"));
		add(new Topic(2, "Topic2"));
		add(new Topic(3, "Topic3"));
	}};
	
	
	@GetMapping(value = "/topics")
	public ResponseEntity<List<Topic>> getAllTopics() {
		return new ResponseEntity<>(topics, HttpStatus.OK);
	}
}
