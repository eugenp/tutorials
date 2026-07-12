package com.baeldung.mybatisflex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.baeldung.mybatisflex.mapper")
public class MyBatisFlexApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisFlexApplication.class, args);
    }

}
