package org.baeldung.mapper;

import static org.junit.Assert.*;

import org.baeldung.dto.EmployeeDTO;
import org.baeldung.entity.Division;
import org.baeldung.entity.Employee;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

public class EmployeeMapperTest {
	
	@Test
	public void givenEmployeeDTOtoEmployee_whenMaps_thenCorrect(){
		EmployeeMapper mapper  = Mappers.getMapper(EmployeeMapper.class);
		
		EmployeeDTO dto = new EmployeeDTO();
		dto.setDivisionId(1);
		dto.setDivisionName("IT Division");
		dto.setEmployeeId(1);
		dto.setEmployeeName("John");
		
		Employee entity = mapper.employeeDTOtoEmployee(dto);
		
		assertEquals(dto.getDivisionId(), entity.getDivision().getId());
		assertEquals(dto.getDivisionName(), entity.getDivision().getName());
		assertEquals(dto.getEmployeeId(),entity.getId());
		assertEquals(dto.getEmployeeName(),entity.getName());
	}
	
	@Test
	public void givenEmployeetoEmployeeDTO_whenMaps_thenCorrect(){
		EmployeeMapper mapper  = Mappers.getMapper(EmployeeMapper.class);
		
		Employee entity = new Employee();
		entity.setDivision(new Division(1,"IT Division"));
		entity.setId(1);
		entity.setName("John");
		
		EmployeeDTO dto = mapper.employeeToEmployeeDTO(entity);
		
		assertEquals(dto.getDivisionId(), entity.getDivision().getId());
		assertEquals(dto.getDivisionName(), entity.getDivision().getName());
		assertEquals(dto.getEmployeeId(),entity.getId());
		assertEquals(dto.getEmployeeName(),entity.getName());
	}
	
}
