package com.baeldung;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.filter.RequestValidatorFilter;
import com.baeldung.model.Employee;

import ratpack.guice.Guice;
import ratpack.hikari.HikariModule;
import ratpack.http.MutableHeaders;
import ratpack.jackson.Jackson;
import ratpack.http.MutableHeaders;
import ratpack.server.RatpackServer;

public class Application {

	public static void main(String[] args) throws Exception {
		
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(new Employee(1L, "Mr", "John Doe"));
		employees.add(new Employee(2L, "Mr", "White Snow"));
		
		
		RatpackServer.start(
				server -> server.registry(Guice.registry(bindings -> bindings.module(HikariModule.class, config -> {
					config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
					config.addDataSourceProperty("URL",
							"jdbc:h2:mem:baeldung;INIT=RUNSCRIPT FROM 'classpath:/DDL.sql'");
				}))).handlers(chain -> chain
						.all(
								// ctx -> {
								// MutableHeaders headers =
								// ctx.getResponse().getHeaders();
								// headers.set("Access-Control-Allow-Origin","*");
								// headers.set("Accept-Language", "en-us");
								// headers.set("Accept-Charset", "UTF-8");
								// ctx.next();
								// }
								new RequestValidatorFilter())
						.get(ctx -> ctx.render("Welcome to baeldung ratpack!!!"))
						.get("data/employees", ctx -> ctx.render(Jackson.json(employees)))
						.get(":name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!!!"))
						.post(":amount", ctx -> ctx
								.render(" Amount $" + ctx.getPathTokens().get("amount") + " added successfully !!!"))));
	}

}
 
