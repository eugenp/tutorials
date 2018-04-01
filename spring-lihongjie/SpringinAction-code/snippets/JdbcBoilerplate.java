
//<start id="jdbc_JdbcTemplate_long"/> 
public Employee getEmployeeById(final long id) {    
  return jdbcTemplate.execute(             //<co id="co_boilerplate_gone"/>
          "select id, firstname, lastname, salary from " +
            "employee where id=?", 
          new PreparedStatementCallback<Employee>() {

    public Employee doInPreparedStatement( //<co id="co_doInPreparedStatement"/>
            PreparedStatement stmt)
            throws SQLException, DataAccessException {
      
      stmt.setLong(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        Employee employee = new Employee();
        employee.setId(rs.getLong("id"));
        employee.setFirstName(rs.getString("firstname"));
        employee.setLastName(rs.getString("lastname"));
        employee.setSalary(rs.getBigDecimal("salary"));
        return employee;
      }
      return null;
    }
  });
}
//<end id="jdbc_JdbcTemplate_long"/>

//<start id="jdbc_JdbcTemplate_queryForObject"/> 
public Employee getEmployeeById(long id) {    
  return jdbcTemplate.queryForObject(
          "select id, firstname, lastname, salary " +   //<co id="co_sql_query"/>
          "from employee where id=?", 
          new RowMapper<Employee>() {                 
            public Employee mapRow(ResultSet rs,
                    int rowNum) throws SQLException {    //<co id="co_map_the_results"/>
              Employee employee = new Employee();
              employee.setId(rs.getLong("id"));
              employee.setFirstName(rs.getString("firstname"));
              employee.setLastName(rs.getString("lastname"));
              employee.setSalary(rs.getBigDecimal("salary"));
              return employee;
            }
          },
          id);    // <co id="co_specify_query_params"/>
}
//<end id="jdbc_JdbcTemplate_queryForObject"/> 

//<start id="jdbc_Boilerplate"/> 
public Employee getEmployeeById(long id) {
  Connection conn = null;
  PreparedStatement stmt = null;
  ResultSet rs = null;
  try {
    conn = dataSource.getConnection();
    stmt = conn.prepareStatement(
          "select id, firstname, lastname, salary from " +
          "employee where id=?");         //<co id="co_selectEmployee"/>
    stmt.setLong(1, id);
    rs = stmt.executeQuery();
    Employee employee = null;
    if (rs.next()) {
      employee = new Employee();          //<co id="co_createEmployee"/>
      employee.setId(rs.getLong("id"));
      employee.setFirstName(rs.getString("firstname"));
      employee.setLastName(rs.getString("lastname"));
      employee.setSalary(rs.getBigDecimal("salary"));
    }
    return employee;
  } catch (SQLException e) {  //<co id="co_handleJdbcException"/>
             
  } finally {
      if(rs != null) {                  //<co id="co_cleanUpJdbcMess"/>
        try {
          rs.close();
        } catch(SQLException e) {}
      }
      
      if(stmt != null) {
        try {
        stmt.close();
        } catch(SQLException e) {}
      }
      
      if(conn != null) {
        try {
          conn.close();
        } catch(SQLException e) {}
      }
  }

  return null;
}
//<end id="jdbc_Boilerplate"/> 
