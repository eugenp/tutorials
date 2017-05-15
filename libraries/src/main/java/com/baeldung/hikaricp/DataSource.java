package com.baeldung.hikaricp;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {

	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;

	static {
//		config = new HikariConfig("datasource.properties");
		
//		Properties props = new Properties();
//		props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
//		props.setProperty("dataSource.user", "postgres");
//		props.setProperty("dataSource.password", "postgres");
//		props.setProperty("dataSource.databaseName", "postgres");
//		props.setProperty("dataSource.portNumber", "5432");
//		props.setProperty("dataSource.serverName", "localhost");
//		props.put("dataSource.logWriter", new PrintWriter(System.out));
//		config = new HikariConfig(props);
		
		config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
		config.setUsername("postgres");
		config.setPassword("postgres");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		ds = new HikariDataSource(config);
		
//		ds.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
//		ds.setUsername("postgres");
//		ds.setPassword("postgres");
	}

	private DataSource() {}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

}
