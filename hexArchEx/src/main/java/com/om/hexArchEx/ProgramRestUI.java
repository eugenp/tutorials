package com.om.hexArchEx;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProgramRestUI {
	@PostMapping
	void createProgram(@RequestBody Programs programs); // spelling mistake found here for programs

	@GetMapping
	public List<Programs> listPrograms();
}
