CREATE ALIAS UPDATE_EMPLOYEE_DESIGNATION AS $$
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
@CODE
void updateEmployeeDesignation(final Connection conn, final String employeeNumber, final String designation) throws SQLException {
        CallableStatement updateStatement = conn.prepareCall("update deptemployee set designation = '" + designation + "' where employeeNumber = '" + employeeNumber + "'");
//        updateStatement.setString(0, designation);
//        updateStatement.setString(1, employeeNumber);
		System.out.println("update deptemployee set designation = '" + designation + "' where employeeNumber = '" + employeeNumber + "'");
        boolean result = updateStatement.execute();
        System.out.println("Result "+result);
}
$$;