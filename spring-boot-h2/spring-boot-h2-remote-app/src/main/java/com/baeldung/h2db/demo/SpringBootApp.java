package com.baeldung.h2db.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@SpringBootApplication
public class SpringBootApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApp.class, args);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void initDb() {
		System.out.println("****** Inserting More Sample data in the table: employees ******");
		jdbcTemplate.execute("insert into employees(first_name, last_name) values('Donald','Trump')");
		jdbcTemplate.execute("insert into employees(first_name, last_name) values('Barack','Obama')");

		System.out.println("****** Fetching from table: employees ******");
		jdbcTemplate.query("select id,first_name,last_name from employees",
				new RowMapper<Object>() {
			@Override
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				System.out.println(rs.getString("id") + " " +
						rs.getString("first_name") + " " +
						rs.getString("last_name"));
				return null;
			}
		});
	}
}
