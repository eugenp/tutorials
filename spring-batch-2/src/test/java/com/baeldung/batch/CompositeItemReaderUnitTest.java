package com.baeldung.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.support.CompositeItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.baeldung.batch.model.Product;

@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { CompositeItemReaderConfig.class })
public class CompositeItemReaderUnitTest {
    @Autowired
    private CompositeItemReader<Product> compositeReader;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.update("DELETE FROM products");
        jdbcTemplate.update("INSERT INTO products (productid, productname, stock, price) VALUES (?, ?, ?, ?)",
            102, "Banana", 30, 1.49);
    }

    @Test
    public void givenTwoReaders_whenRead_thenProcessProductsInOrder() throws Exception {
        StepExecution stepExecution = new StepExecution("testStep", new JobExecution(1L, new JobParameters()), 1L);
        ExecutionContext executionContext = stepExecution.getExecutionContext();
        compositeReader.open(executionContext);

        Product product1 = compositeReader.read();
        assertNotNull(product1);
        assertEquals(101, product1.getProductId());
        assertEquals("Apple", product1.getProductName());

        Product product2 = compositeReader.read();
        assertNotNull(product2);
        assertEquals(102, product2.getProductId());
        assertEquals("Banana", product2.getProductName());
    }

    @Test
    public void givenMultipleReader_whenOneReaderReturnNull_thenProcessDataFromNextReader() throws Exception {
        ItemStreamReader<Product> emptyReader = mock(ItemStreamReader.class);
        when(emptyReader.read()).thenReturn(null);

        ItemStreamReader<Product> validReader = mock(ItemStreamReader.class);
        when(validReader.read()).thenReturn(new Product(103L, "Cherry", 20, BigDecimal.valueOf(2.99)), null);

        CompositeItemReader<Product> compositeReader = new CompositeItemReader<>(Arrays.asList(emptyReader, validReader));

        Product product = compositeReader.read();
        assertNotNull(product);
        assertEquals(103, product.getProductId());
        assertEquals("Cherry", product.getProductName());
    }


    @Test
    public void givenEmptyReaders_whenRead_thenReturnNull() throws Exception {
        ItemStreamReader<Product> emptyReader = mock(ItemStreamReader.class);
        when(emptyReader.read()).thenReturn(null);

        CompositeItemReader<Product> compositeReader = new CompositeItemReader<>(Arrays.asList(emptyReader, emptyReader));

        Product product = compositeReader.read();
        assertNull(product);
    }
}
