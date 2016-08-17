package org.baeldung.mapper;

import static org.junit.Assert.assertEquals;

import org.baeldung.dto.DivisionDTO;
import org.baeldung.dto.EmployeeDTO;
import org.baeldung.entity.Division;
import org.baeldung.entity.Employee;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

public class EmployeeMapperTest {

    @Test
    public void givenEmployeeDTOwithDiffNametoEmployee_whenMaps_thenCorrect() {
        EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);

        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(1);
        dto.setEmployeeName("John");

        Employee entity = mapper.employeeDTOtoEmployee(dto);

        assertEquals(dto.getEmployeeId(), entity.getId());
        assertEquals(dto.getEmployeeName(), entity.getName());
    }

    @Test
    public void givenEmployeewithDiffNametoEmployeeDTO_whenMaps_thenCorrect() {
        EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);

        Employee entity = new Employee();
        entity.setId(1);
        entity.setName("John");

        EmployeeDTO dto = mapper.employeeToEmployeeDTO(entity);

        assertEquals(dto.getEmployeeId(), entity.getId());
        assertEquals(dto.getEmployeeName(), entity.getName());
    }

    @Test
    public void givenEmployeeDTOwithNestedMappingToEmployee_whenMaps_thenCorrect() {
        EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);

        EmployeeDTO dto = new EmployeeDTO();
        dto.setDivision(new DivisionDTO(1, "Division1"));

        Employee entity = mapper.employeeDTOtoEmployee(dto);

        assertEquals(dto.getDivision().getId(), entity.getDivision().getId());
        assertEquals(dto.getDivision().getName(), entity.getDivision().getName());
    }

    @Test
    public void givenEmployeeWithNestedMappingToEmployeeDTO_whenMaps_thenCorrect() {
        EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);

        Employee entity = new Employee();
        entity.setDivision(new Division(1, "Division1"));

        EmployeeDTO dto = mapper.employeeToEmployeeDTO(entity);

        assertEquals(dto.getDivision().getId(), entity.getDivision().getId());
        assertEquals(dto.getDivision().getName(), entity.getDivision().getName());
    }

}
