package com.baeldung.sample.adpater;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class DatabaseIntegrationAdapter {

	Connection con;

	public DatabaseIntegrationAdapter() {
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:hexagonal", "user", "password");
		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception occured --- unable to create the database connection",e);
		}

	}

	public Connection getConnection() {
		return con;
	}

	public void insertArticleDataInDB(String heading, String content) {
		try {
			getConnection().createStatement().execute("INSERT INTO ARTICLE VALUES(" + heading + "," + content + ")");
		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception occured --- unable to insert article into the database",e);
		}

	}

	public void insertArticleMetadataInDB(String content, Date date) {
		try {
			getConnection().createStatement().execute("INSERT INTO ARTICLE_METADATA VALUES(" + content + "," + date.toString() + ")");
		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception occured --- unable to insert article metadata into the database",e);
		}

	}

}
