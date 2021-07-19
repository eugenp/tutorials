package com.baeldung.spring.oracle.pooling;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class SpringOraclePoolingApplication implements CommandLineRunner{
	
    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(SpringOraclePoolingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Connection Polling datasource : "+ dataSource);
        
    }

}
