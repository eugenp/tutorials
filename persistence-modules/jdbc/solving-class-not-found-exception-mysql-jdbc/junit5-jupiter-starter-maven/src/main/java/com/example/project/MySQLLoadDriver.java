package com.example.project;

public class MySQLLoadDriver {

	public int loadDriver() {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/mysql?" +"user=root"+"password=mysql");
			return 1;

		} catch (ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
			return 0;

		}/** catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return 0;

		}**/
	}
}
