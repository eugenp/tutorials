package io.orkes.example.saga;

import io.orkes.example.saga.dao.BaseDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@SpringBootApplication
@ComponentScan(basePackages = {"io.orkes"})
public class SagaApplication {

	private static final BaseDAO db = new BaseDAO("jdbc:sqlite:food_delivery.db");

	public static void main(String[] args) {
		SpringApplication.run(SagaApplication.class, args);
		initDB();
	}

	public static void initDB() {
		db.createTables("orders");
		db.createTables("inventory");
		db.createTables("payments");
		db.createTables("shipments");
	}
}
