package com.baeldung.jpa.projections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;


public class ProductRepositoryIntegrationTest {
    private static ProductRepository productRepository;

    @BeforeClass
    public static void once() throws IOException {
        productRepository = new ProductRepository();
    }
    
    @Test
    public void givenProductData_whenIdAndNameProjectionUsingJPQL_thenListOfObjectArrayReturned() {
        List<Object[]> resultList = productRepository.findAllIdAndNamesUsingJPQL();
        
        assertNotNull(resultList);
        assertEquals(4, resultList.size());
        assertEquals(1L, resultList.get(0)[0]);
        assertEquals("Product Name 1", resultList.get(0)[1]);
        assertEquals(2L, resultList.get(1)[0]);
        assertEquals("Product Name 2", resultList.get(1)[1]);
        assertEquals(3L, resultList.get(2)[0]);
        assertEquals("Product Name 3", resultList.get(2)[1]);
        assertEquals(4L, resultList.get(3)[0]);
        assertEquals("Product Name 4", resultList.get(3)[1]);
    }
    
    @Test
    public void givenProductData_whenIdAndNameProjectionUsingCriteriaBuilder_thenListOfObjectArrayReturned() {
        List<Object[]> resultList = productRepository.findAllIdAndNamesUsingCriteriaBuilderArray();
                
        assertNotNull(resultList);
        assertEquals(4, resultList.size());
        assertEquals(1L, resultList.get(0)[0]);
        assertEquals("Product Name 1", resultList.get(0)[1]);
        assertEquals(2L, resultList.get(1)[0]);
        assertEquals("Product Name 2", resultList.get(1)[1]);
        assertEquals(3L, resultList.get(2)[0]);
        assertEquals("Product Name 3", resultList.get(2)[1]);
        assertEquals(4L, resultList.get(3)[0]);
        assertEquals("Product Name 4", resultList.get(3)[1]);
    }
    
    @SuppressWarnings("rawtypes")
    @Test
    public void givenProductData_whenNameProjectionUsingJPQL_thenListOfStringReturned() {
        List resultList = productRepository.findAllNamesUsingJPQL();
        
        assertNotNull(resultList);
        assertEquals(4, resultList.size());
        assertEquals("Product Name 1", resultList.get(0));
        assertEquals("Product Name 2", resultList.get(1));
        assertEquals("Product Name 3", resultList.get(2));
        assertEquals("Product Name 4", resultList.get(3));
    }
    
    @Test
    public void givenProductData_whenNameProjectionUsingCriteriaBuilder_thenListOfStringReturned() {
        List<String> resultList = productRepository.findAllNamesUsingCriteriaBuilder();
        
        assertNotNull(resultList);
        assertEquals(4, resultList.size());
        assertEquals("Product Name 1", resultList.get(0));
        assertEquals("Product Name 2", resultList.get(1));
        assertEquals("Product Name 3", resultList.get(2));
        assertEquals("Product Name 4", resultList.get(3));
    }
    
    @Test
    public void givenProductData_whenCountByCategoryUsingJPQL_thenOK() {
        List<Object[]> resultList = productRepository.findCountByCategoryUsingJPQL();

        assertNotNull(resultList);
        assertEquals(3, resultList.size());
        assertEquals("category1", resultList.get(0)[0]);
        assertEquals(2L, resultList.get(0)[1]);
        assertEquals("category2", resultList.get(1)[0]);
        assertEquals(1L, resultList.get(1)[1]);
        assertEquals("category3", resultList.get(2)[0]);
        assertEquals(1L, resultList.get(2)[1]);
    }

    @Test
    public void givenProductData_whenCountByCategoryUsingCriteriaBuider_thenOK() {
        List<Object[]> resultList = productRepository.findCountByCategoryUsingCriteriaBuilder();

        assertNotNull(resultList);
        assertEquals(3, resultList.size());
        assertEquals("category1", resultList.get(0)[0]);
        assertEquals(2L, resultList.get(0)[1]);
        assertEquals("category2", resultList.get(1)[0]);
        assertEquals(1L, resultList.get(1)[1]);
        assertEquals("category3", resultList.get(2)[0]);
        assertEquals(1L, resultList.get(2)[1]);
    }
}
