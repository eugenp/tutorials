package com.baeldung.mapper;

import com.baeldung.dto.DivisionDTO;
import com.baeldung.dto.EmployeeDTO;
import com.baeldung.entity.Division;
import com.baeldung.entity.Employee;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EmployeeMapperUnitTest {

    EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    @Test
    public void givenEmployeeDTOwithDiffNametoEmployee_whenMaps_thenCorrect() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(1);
        dto.setEmployeeName("John");

        Employee entity = mapper.employeeDTOtoEmployee(dto);

        assertEquals(dto.getEmployeeId(), entity.getId());
        assertEquals(dto.getEmployeeName(), entity.getName());
    }

    @Test
    public void givenEmployeewithDiffNametoEmployeeDTO_whenMaps_thenCorrect() {
        Employee entity = new Employee();
        entity.setId(1);
        entity.setName("John");

        EmployeeDTO dto = mapper.employeeToEmployeeDTO(entity);

        assertEquals(dto.getEmployeeId(), entity.getId());
        assertEquals(dto.getEmployeeName(), entity.getName());
    }

    @Test
    public void givenEmpDTONestedMappingToEmp_whenMaps_thenCorrect() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setDivision(new DivisionDTO(1, "Division1"));

        Employee entity = mapper.employeeDTOtoEmployee(dto);

        assertEquals(dto.getDivision().getId(), entity.getDivision().getId());
        assertEquals(dto.getDivision().getName(), entity.getDivision().getName());
    }

    @Test
    public void givenEmployeeWithNestedMappingToEmployeeDTO_whenMaps_thenCorrect() {
        Employee entity = new Employee();
        entity.setDivision(new Division(1, "Division1"));

        EmployeeDTO dto = mapper.employeeToEmployeeDTO(entity);

        assertEquals(dto.getDivision().getId(), entity.getDivision().getId());
        assertEquals(dto.getDivision().getName(), entity.getDivision().getName());
    }

    @Test
    public void givenEmployeeListToEmployeeDTOList_whenMaps_thenCorrect() {
        List<Employee> employeeList = new ArrayList<>();
        Employee emp = new Employee();
        emp.setId(1);
        emp.setName("EmpName");
        emp.setDivision(new Division(1, "Division1"));
        employeeList.add(emp);

        List<EmployeeDTO> employeeDtoList = mapper.convertEmployeeListToEmployeeDTOList(employeeList);
        EmployeeDTO employeeDTO = employeeDtoList.get(0);
        assertEquals(employeeDTO.getEmployeeId(), emp.getId());
        assertEquals(employeeDTO.getEmployeeName(), emp.getName());
        assertEquals(employeeDTO.getDivision().getId(), emp.getDivision().getId());
        assertEquals(employeeDTO.getDivision().getName(), emp.getDivision().getName());
    }

    @Test
    public void givenEmployeeDTOListToEmployeeList_whenMaps_thenCorrect() {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        EmployeeDTO empDTO = new EmployeeDTO();
        empDTO.setEmployeeId(1);
        empDTO.setEmployeeName("EmpName");
        empDTO.setDivision(new DivisionDTO(1, "Division1"));
        employeeDTOList.add(empDTO);

        List<Employee> employeeList = mapper.convertEmployeeDTOListToEmployeeList(employeeDTOList);
        Employee employee = employeeList.get(0);
        assertEquals(employee.getId(), empDTO.getEmployeeId());
        assertEquals(employee.getName(), empDTO.getEmployeeName());
        assertEquals(employee.getDivision().getId(), empDTO.getDivision().getId());
        assertEquals(employee.getDivision().getName(), empDTO.getDivision().getName());
    }

    @Test
    public void givenEmpStartDtMappingToEmpDTO_whenMaps_thenCorrect() throws ParseException {
        Employee entity = new Employee();
        entity.setStartDt(new Date());

        EmployeeDTO dto = mapper.employeeToEmployeeDTO(entity);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        assertEquals(format.parse(dto.getEmployeeStartDt()).toString(), entity.getStartDt().toString());
    }

    @Test
    public void givenEmpDTOStartDtMappingToEmp_whenMaps_thenCorrect() throws ParseException {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeStartDt("01-04-2016 01:00:00");

        Employee entity = mapper.employeeDTOtoEmployee(dto);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        assertEquals(format.parse(dto.getEmployeeStartDt()).toString(), entity.getStartDt().toString());
    }
}
