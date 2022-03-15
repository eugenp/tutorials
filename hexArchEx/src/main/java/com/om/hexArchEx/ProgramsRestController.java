package com.om.hexArchEx;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/programs")
public class ProgramsRestController implements ProgramRestUI {
	@Autowired
	private ProgramsService programsService;

	@Override
	public void createProgram(Programs program)
	{
		programsService.createPrograms(program);
	}

	@Override
	public List<Programs> listPrograms()
	{
		return programsService.listPrograms();
	}
}
