package com.shopping.zone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.shopping.zone.dao.ShoppingDaoImpl;
import com.shopping.zone.service.ShoppingDao;

@SpringBootApplication
public class ZoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZoneApplication.class, args);
	}

	@Bean
	public ShoppingDao shoppingDao() {
		ShoppingDao shoppingDao=new ShoppingDaoImpl();
		return shoppingDao;
	}
	
	
}
