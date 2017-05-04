package com.baeldung.hikaricp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HikariCPDemo {

	public static void fetchData() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		final String SQL_QUERY = "select * from user_auth_details";
		try {
			con = DataSource.getConnection();
			pst = con.prepareStatement(SQL_QUERY);
			rs = pst.executeQuery();
			while (rs.next()) {
				System.out.println(
						String.format("Username : %s, Password : %s, Role : %s, Mail : %s", rs.getString("username"),
								rs.getString("password"), rs.getString("role"), rs.getString("mail")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
