package com.baeldung.hikaricp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HikariCPDemo {

	public static void fetchData() {
		final String SQL_QUERY = "select * from user_auth_details";
		try ( Connection con = DataSource.getConnection(); 
				PreparedStatement pst = con.prepareStatement(SQL_QUERY); 
				ResultSet rs = pst.executeQuery(); ) {
			while (rs.next()) {
				System.out.println(
						String.format("Username : %s, Password : %s, Role : %s, Mail : %s", rs.getString("username"),
								rs.getString("password"), rs.getString("role"), rs.getString("mail")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
