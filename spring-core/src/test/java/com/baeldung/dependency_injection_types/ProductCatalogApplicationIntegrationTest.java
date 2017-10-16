package com.baeldung.dependency_injection_types;

import com.baeldung.dependency_injection_types.entities.Category;
import com.baeldung.dependency_injection_types.entities.Product;
import com.baeldung.dependency_injection_types.repositories.CategoryRepository;
import com.baeldung.dependency_injection_types.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductCatalogApplication.class, webEnvironment = NONE)
public class ProductCatalogApplicationIntegrationTest {

    @Autowired private ProductRepository productRepository;

    @Autowired private CategoryRepository categoryRepository;

    @Test
    public void whenApplicationStarted_ThenProductListHasElements() {
        assertNotNull(productRepository);
        assertEquals(1, productRepository
          .findAll()
          .size());
    }

    @Test
    public void whenApplicationStarted_ThenCategoryListHasElements() {
        assertNotNull(categoryRepository);
        assertEquals(1, categoryRepository
          .findAll()
          .size());
    }

    @Test
    public void givenStartedApplication_WhenAddingAProduct_ThenProductListSizeIncreases() {
        productRepository.add(Product
          .builder()
          .build());
        assertEquals(2, productRepository
          .findAll()
          .size());
    }

    @Test
    public void givenStartedApplication_WhenAddingACategory_ThenCategoryListSizeIncreases() {
        categoryRepository.add(Category
          .builder()
          .build());
        assertEquals(2, categoryRepository
          .findAll()
          .size());
    }

}
