package org.baeldung.mapper;

import org.baeldung.dto.DivisionDTO;
import org.baeldung.dto.EmployeeDTO;
import org.baeldung.entity.Division;
import org.baeldung.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface EmployeeMapper {

    @Mappings({ @Mapping(target = "employeeId", source = "entity.id"), @Mapping(target = "employeeName", source = "entity.name") })
    EmployeeDTO employeeToEmployeeDTO(Employee entity);

    @Mappings({ @Mapping(target = "id", source = "dto.employeeId"), @Mapping(target = "name", source = "dto.employeeName") })
    Employee employeeDTOtoEmployee(EmployeeDTO dto);

    DivisionDTO divisionToDivisionDTO(Division entity);

    Division divisionDTOtoDivision(DivisionDTO dto);

}
