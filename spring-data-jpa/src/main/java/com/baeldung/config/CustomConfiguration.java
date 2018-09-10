package com.baeldung.config;

import com.baeldung.dao.repositories.InventoryRepository;
import com.baeldung.domain.MerchandiseEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = InventoryRepository.class)
@EntityScan(basePackageClasses = MerchandiseEntity.class)
public class CustomConfiguration {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CustomConfiguration.class, args);

        InventoryRepository repo = context.getBean(InventoryRepository.class);

        MerchandiseEntity pants = new MerchandiseEntity("Pair of Pants");
        repo.save(pants);

        MerchandiseEntity shorts = new MerchandiseEntity("Pair of Shorts");
        repo.save(shorts);

        pants.setTitle("Branded Luxury Pants");
        repo.save(pants);
    }
}
