package com.baeldung.hexagonalspringboot;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.hexagonalspringboot.domain.Product;
import com.baeldung.hexagonalspringboot.domain.ProductSearchServiceImpl;
import com.baeldung.hexagonalspringboot.port.IProductSearchRepository;
import com.baeldung.hexagonalspringboot.port.IProductSearchService;

@SpringBootTest
class ProductSearchServiceImplTest {
    
    @Autowired
    private IProductSearchService productService;

    @Test
    public void whenGetAllproducts_notEmpty() {
        List<Product> allProducts = productService.getAllproducts();
        assertThat(allProducts, not(IsEmptyCollection.empty()));
    }

   @Test
    public void whenValidCategory_notEmpty() {
        String valid = "tablet";
        List<Product> products = productService.getproductsByCategory(valid);
        assertThat(products, not(IsEmptyCollection.empty()));
    }

   @Test
    public void whenInValidCategory_Empty() {
        String invalid = "dummy";
        List<Product> products = productService.getproductsByCategory(invalid);
        assertThat(products, (IsEmptyCollection.empty()));
    }
}
