package com.baeldung.hexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * Hexagonal Architecture Sample Service Main Class
 * 
 * @author : Udara Gunathilake
 * @email : udara.dhammika@gmail.com
 * @date : Feb 17, 2020
 */
@SpringBootApplication
@Slf4j
public class Hexagonal {
    public static void main(String[] args) {
	log.info("Hexagonal Architecture Sample Service Starting up..!!");

	SpringApplication.run(Hexagonal.class, args);
    }
}
