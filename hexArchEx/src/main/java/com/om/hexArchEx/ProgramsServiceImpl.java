package com.om.hexArchEx;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgramsServiceImpl
	implements ProgramsService {


	@Autowired
	private ProgramRepo programRepo;
	
	@Override
	public Response createPrograms(Programs program)
	{
		programRepo.createProgram(program);
		return Response.ok(program).build();
	}

	@Override
	public List<Programs> listPrograms()
	{
		return programRepo.getAllPrograms();
	}
	
}
