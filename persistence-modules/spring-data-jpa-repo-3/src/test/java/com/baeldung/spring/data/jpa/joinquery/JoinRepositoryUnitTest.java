package com.baeldung.spring.data.jpa.joinquery;

import com.baeldung.spring.data.jpa.joinquery.DTO.ResultDTO_wo_Ids;
import com.baeldung.spring.data.jpa.joinquery.entities.Customer;
import com.baeldung.spring.data.jpa.joinquery.entities.CustomerOrder;
import com.baeldung.spring.data.jpa.joinquery.DTO.ResultDTO;
import com.baeldung.spring.data.jpa.joinquery.entities.Product;
import com.baeldung.spring.data.jpa.joinquery.repositories.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.baeldung.spring.data.jpa.joinquery.repositories.JoinRepository;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class })
class JoinRepositoryUnitTest {

    @Autowired
    JoinRepository joinRepository;

    @Autowired
    CustomerRepository customerRepository;

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        entityManager = entityManagerFactory.createEntityManager();
        Customer c = new Customer(1L, null, "email1@email.com", LocalDate.now(), "Customer");
        CustomerOrder o1 = new CustomerOrder(1L, null, LocalDate.now(), c);
        CustomerOrder o2 = new CustomerOrder(2L, null, LocalDate.of(2024,1,1), c);

        Product p1 = new Product(1L,"Product1", 25.20, o1);
        Product p2 = new Product(2L, "Product2", 26.20, o1);
        Product p3 = new Product(3L, "Product3", 27.20, o2);

        Set<CustomerOrder> cos01 = new HashSet<>();
        cos01.add(o1);
        cos01.add(o2);
        Set<Product> productsO1 = new HashSet<>();
        productsO1.add(p1);
        productsO1.add(p2);
        Set<Product> productsO2 = new HashSet<>();
        productsO1.add(p3);

        c.setCustomerOrders(cos01);
        o1.setProducts(productsO1);
        o2.setProducts(productsO2);

        EntityTransaction et = getTransaction();
        entityManager.persist(c);
        entityManager.flush();
        et.commit();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenFindResultDTOByCustomer_thenReturnResultDTO() {
        List<ResultDTO> resultDTO = joinRepository.findResultDTOByCustomer(1L);
        assertInstanceOf(ResultDTO.class, resultDTO.get(0));
    }

    @Test
    void whenFindByCustomerNativeQuery_thenReturnMapsForObjects() {
        List<Map<String, Object>> res = joinRepository.findByCustomer(1L);
        //the map objects in the res List has 9 String keys aka 9 columns corresponding to the columns of customer, customerOrder, product
        res.forEach(map -> {
            assertEquals(9, map.keySet().size());
        });
    }

    @Test
    void whenFindResultDTOByCustomerWithoutIds_thenReturnResultDTO() {
        List<ResultDTO_wo_Ids> resultDTO = joinRepository.findResultDTOByCustomerWithoutIds(1L);
        assertInstanceOf(ResultDTO_wo_Ids.class, resultDTO.get(0));
    }

    private EntityTransaction getTransaction() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        return transaction;
    }
}