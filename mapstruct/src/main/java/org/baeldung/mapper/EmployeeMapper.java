package org.baeldung.mapper;

import org.baeldung.dto.EmployeeDTO;
import org.baeldung.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface EmployeeMapper {

	@Mappings({
		@Mapping(target="divisionId",source="entity.division.id"),
		@Mapping(target="divisionName",source="entity.division.name"),
		@Mapping(target="employeeId",source="entity.id"),
		@Mapping(target="employeeName",source="entity.name")
	})
	EmployeeDTO employeeToEmployeeDTO(Employee entity);
	
	@Mappings({
		@Mapping(target="id",source="dto.employeeId"),
		@Mapping(target="name",source="dto.employeeName"),
		@Mapping(target="division",expression="java(new org.baeldung.entity.Division(dto.getDivisionId(),dto.getDivisionName()))")
	})
	Employee employeeDTOtoEmployee(EmployeeDTO dto);
	
}
