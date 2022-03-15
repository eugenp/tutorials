package com.om.hexArchEx;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class ProgramRepoImpl
	implements ProgramRepo {
	private Map<String, Programs> programsStore
		= new HashMap<String, Programs>();

	@Override
	public void createProgram(Programs program)
	{
		programsStore.put(program.getName(), program);
	}


	@Override
	public List<Programs> getAllPrograms()
	{
		return programsStore.values().stream().collect(Collectors.toList());
	}
}
