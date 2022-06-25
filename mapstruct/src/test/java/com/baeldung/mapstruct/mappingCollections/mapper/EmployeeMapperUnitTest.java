package com.baeldung.mapstruct.mappingCollections.mapper;

import com.baeldung.mapstruct.mappingCollections.dto.EmployeeDTO;
import com.baeldung.mapstruct.mappingCollections.model.Employee;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeMapperUnitTest {

    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    @Test
    void whenMappingToEmployeeDTOList_thenExpectCorrectMappingResult() {
        Employee employee = new Employee("John", "Doe");

        List<EmployeeDTO> result = employeeMapper.listOfEmployeeToListOfEmployeeDto(Collections.singletonList(employee));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
        assertThat(result.get(0).getLastName()).isEqualTo("Doe");
    }

    @Test
    void whenMappingToEmployeeDTOSet_thenExpectCorrectMappingResult() {
        Employee employee = new Employee("John", "Doe");

        Set<EmployeeDTO> result = employeeMapper.setOfEmployeeToSetOfEmployeeDto(Collections.singleton(employee));

        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().getFirstName()).isEqualTo("John");
        assertThat(result.iterator().next().getLastName()).isEqualTo("Doe");
    }

    @Test
    void whenMappingToEmployeeDTOMap_thenExpectCorrectMappingResult() {
        Employee employee = new Employee("John", "Doe");

        Map<String, EmployeeDTO> result = employeeMapper.mapOfEmployeeToMapOfEmployeeDto(Collections.singletonMap("1", employee));

        assertThat(result).hasSize(1);
        assertThat(result.get("1").getFirstName()).isEqualTo("John");
        assertThat(result.get("1").getLastName()).isEqualTo("Doe");
    }
}
