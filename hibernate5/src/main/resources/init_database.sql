CREATE ALIAS UPDATE_EMPLOYEE_DESIGNATION AS $$
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
@CODE
void updateEmployeeDesignation(final Connection conn, final String employeeNumber, final String designation) throws SQLException {
        CallableStatement updateStatement = conn.prepareCall("update deptemployee set designation = '" + designation + "' where employeeNumber = '" + employeeNumber + "'");
        updateStatement.execute();
}
$$;