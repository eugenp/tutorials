package com.hexagonalArcht.demo.ports;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hexagonalArcht.demo.model.Student;

public interface StudentUIPort {
	
	@PostMapping("register")
	public void register(@RequestBody Student request);

	@GetMapping("view/{id}")
	public Student view(@PathVariable Integer id);

}
