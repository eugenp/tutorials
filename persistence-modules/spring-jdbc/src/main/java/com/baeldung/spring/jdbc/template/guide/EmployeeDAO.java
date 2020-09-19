package com.baeldung.spring.jdbc.template.guide;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDAO {

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;
    
    private SimpleJdbcCall simpleJdbcCall;

    @Autowired
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("EMPLOYEE");
        
        // Commented as the database is H2, change the database and create procedure READ_EMPLOYEE before calling getEmployeeUsingSimpleJdbcCall 
        //simpleJdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("READ_EMPLOYEE");
    }

    public int getCountOfEmployees() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM EMPLOYEE", Integer.class);
    }

    public List<Employee> getAllEmployees() {
        return jdbcTemplate.query("SELECT * FROM EMPLOYEE", new EmployeeRowMapper());
    }

    public int addEmplyee(final int id) {
        return jdbcTemplate.update("INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?)", id, "Bill", "Gates", "USA");
    }

    public int addEmplyeeUsingSimpelJdbcInsert(final Employee emp) {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ID", emp.getId());
        parameters.put("FIRST_NAME", emp.getFirstName());
        parameters.put("LAST_NAME", emp.getLastName());
        parameters.put("ADDRESS", emp.getAddress());

        return simpleJdbcInsert.execute(parameters);
    }

    public Employee getEmployee(final int id) {
        final String query = "SELECT * FROM EMPLOYEE WHERE ID = ?";
        return jdbcTemplate.queryForObject(query, new Object[] { id }, new EmployeeRowMapper());
    }

    public void addEmplyeeUsingExecuteMethod() {
        jdbcTemplate.execute("INSERT INTO EMPLOYEE VALUES (6, 'Bill', 'Gates', 'USA')");
    }

    public String getEmployeeUsingMapSqlParameterSource() {
        final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", 1);

        return namedParameterJdbcTemplate.queryForObject("SELECT FIRST_NAME FROM EMPLOYEE WHERE ID = :id", namedParameters, String.class);
    }

    public int getEmployeeUsingBeanPropertySqlParameterSource() {
        final Employee employee = new Employee();
        employee.setFirstName("James");

        final String SELECT_BY_ID = "SELECT COUNT(*) FROM EMPLOYEE WHERE FIRST_NAME = :firstName";

        final SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(employee);

        return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, Integer.class);
    }

    public int[] batchUpdateUsingJDBCTemplate(final List<Employee> employees) {
        return jdbcTemplate.batchUpdate("INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?)", new BatchPreparedStatementSetter() {

            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                ps.setInt(1, employees.get(i).getId());
                ps.setString(2, employees.get(i).getFirstName());
                ps.setString(3, employees.get(i).getLastName());
                ps.setString(4, employees.get(i).getAddress());
            }

            @Override
            public int getBatchSize() {
                return 3;
            }
        });
    }

    public int[] batchUpdateUsingNamedParameterJDBCTemplate(final List<Employee> employees) {
        final SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(employees.toArray());
        final int[] updateCounts = namedParameterJdbcTemplate.batchUpdate("INSERT INTO EMPLOYEE VALUES (:id, :firstName, :lastName, :address)", batch);
        return updateCounts;
    }
    
    public Employee getEmployeeUsingSimpleJdbcCall(int id) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("in_id", id);
        Map<String, Object> out = simpleJdbcCall.execute(in);

        Employee emp = new Employee();
        emp.setFirstName((String) out.get("FIRST_NAME"));
        emp.setLastName((String) out.get("LAST_NAME"));

        return emp;
    }
}
