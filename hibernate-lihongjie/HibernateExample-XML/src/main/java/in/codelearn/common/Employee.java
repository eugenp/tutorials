package in.codelearn.common;

/**
 * Model class for Stock
 */
public class Employee implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer employeeId;
	private String employeeCode;
	private String employeeName;

	public Employee() {
	}

	public Employee(String employeeCode, String employeeName) {
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
}
