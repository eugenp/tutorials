package com.baeldung.architecture_hexagonal.port;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.architecture_hexagonal.core.Person;

public interface PersonUIPort {

	@PostMapping(value = "create", consumes = { "application/json" })
	public void create(@RequestBody Person request);

	@GetMapping("view/{id}")
	public Person view(@PathVariable Long id);

	@GetMapping("view/phonebook")
	public List<Person> getPhonebook();

}
