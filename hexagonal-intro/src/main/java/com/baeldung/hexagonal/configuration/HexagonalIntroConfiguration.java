package com.baeldung.hexagonal.configuration;

import com.baeldung.hexagonal.repository.mongodb.SpringDataMongoDbOrderRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories(basePackageClasses = SpringDataMongoDbOrderRepository.class)
public class HexagonalIntroConfiguration {
}
