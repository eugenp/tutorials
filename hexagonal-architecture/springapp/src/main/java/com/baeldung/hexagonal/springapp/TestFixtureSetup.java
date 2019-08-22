package com.baeldung.hexagonal.springapp;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.baeldung.hexagonal.core.LoggedInCustomerHolder;
import com.baeldung.hexagonal.springapp.entity.CustomerEntity;
import com.baeldung.hexagonal.springapp.entity.ProductEntity;
import com.baeldung.hexagonal.springapp.mapper.CustomerMapper;
import com.baeldung.hexagonal.springapp.repository.JpaCouponRepository;
import com.baeldung.hexagonal.springapp.repository.JpaProductRepository;

@Configuration
public class TestFixtureSetup {

    @Autowired JpaProductRepository productRepository;

    @Autowired JpaCouponRepository couponRepository;

    @Autowired LoggedInCustomerHolder customerHolder;


    @Autowired PlatformTransactionManager transactionManager;

    @Autowired EntityManager em;

    @PostConstruct
    public void initialize() {
        ProductEntity product1 = new ProductEntity();
        product1.setName("product-1");
        product1.setStockQuantity(10);
        product1.setUnitPrice(200);
        productRepository.save(product1);

        ProductEntity product2 = new ProductEntity();
        product2.setName("product-2");
        product2.setStockQuantity(15);
        product2.setUnitPrice(300);
        productRepository.save(product2);

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute(status -> {
            CustomerEntity customer = new CustomerEntity();
            customer.setEmail("john.doe@example.org");
            em.persist(customer);
            return customer;
        });

    }

}
