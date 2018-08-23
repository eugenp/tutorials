package com.baeldung.h2db.demo;

import java.sql.SQLException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.h2.tools.Server;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class SpringBootApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApp.class, args);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void initDb() {
		System.out.println("****** Creating table: employees ******");
		jdbcTemplate.execute("drop table employees if exists");
		jdbcTemplate.execute("create table employees(id serial,first_name varchar(255),last_name varchar(255))");

		System.out.println("****** Inserting Sample data in the table: employees ******");
		jdbcTemplate.execute("insert into employees(first_name, last_name) values('Eugen','Paraschiv')");
		jdbcTemplate.execute("insert into employees(first_name, last_name) values('Scott','Tiger')");
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server inMemoryH2DatabaseaServer() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
	}
}
