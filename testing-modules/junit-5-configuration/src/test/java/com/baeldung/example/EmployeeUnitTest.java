package com.baeldung.example;

import com.baeldung.junit.tags.example.EmployeeDAO;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

public class EmployeeUnitTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    private EmployeeDAO employeeDAO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        employeeDAO = new EmployeeDAO();
        employeeDAO.setJdbcTemplate(jdbcTemplate);
    }

    @Test
    public void givenNumberOfEmployeeWhenCountEmployeeThenCountMatch() {

        // given
        Mockito.when(jdbcTemplate.queryForObject(Mockito.any(String.class), Mockito.eq(Integer.class)))
            .thenReturn(1);

        // when
        int countOfEmployees = employeeDAO.getCountOfEmployees();

        // then
        Assert.assertThat(countOfEmployees, CoreMatchers.is(1));
    }
}
