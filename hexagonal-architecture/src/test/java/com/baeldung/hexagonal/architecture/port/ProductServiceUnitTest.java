package com.baeldung.hexagonal.architecture.port;

import com.baeldung.hexagonal.architecture.domain.model.Product;
import com.baeldung.hexagonal.architecture.domain.service.ProductServiceImplementation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ProductServiceUnitTest {

    @TestConfiguration
    static class ProductServiceTestConfig {
        @Bean
        public ProductService productService() {
            return new ProductServiceImplementation();
        }
    }

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Before
    public void setUp() {
        Product mobilePhone = new Product(1, "Mobile Phone", "Samsung Galaxy S9");
        Product book = new Product(2, "Book", "Kite Runner");
        Product laptop = new Product(3, "Laptop", "Macbook Pro");
        Product electronics = new Product(4, "Electronics", "Bluetooth Speaker");

        List<Product> products = Arrays.asList(mobilePhone, book, laptop);

        Mockito.when(productRepository.getProducts()).thenReturn(products);
        Mockito.when(productRepository.getProductById(mobilePhone.getProductId())).thenReturn(mobilePhone);
        Mockito.when(productRepository.getProductById(5)).thenReturn(null);
        Mockito.when(productRepository.addProduct(electronics)).thenReturn(electronics);
        Mockito.when(productRepository.removeProduct(laptop.getProductId())).thenReturn(laptop);
    }

    @Test
    public void whenValidProductId_thenProductShouldBeFound() {
        Integer id = 1;
        Product product = productService.getProductById(1);

        assertThat(product.getProductId()).isEqualTo(id);
        verifyGetByProductIdIsCalledOnce();
    }

    @Test
    public void whenInValidProductId_thenProductShouldNotBeFound() {
        Product product = productService.getProductById(5);

        assertThat(product).isNull();
        verifyGetByProductIdIsCalledOnce();
    }

    @Test
    public void givenThreeProducts_whenGetAllProducts_thenThreeProductsReturned() {
        Product mobilePhone = new Product(1, "Mobile Phone", "Samsung Galaxy S9");
        Product book = new Product(2, "Book", "Kite Runner");
        Product laptop = new Product(3, "Laptop", "Macbook Pro");

        List<Product> allProducts = productService.getProducts();

        assertThat(allProducts).hasSize(3).extracting(Product::getType).contains(mobilePhone.getType(), book.getType(), laptop.getType());
        verifyGetProductsIsCalledOnce();
    }

    @Test
    public void whenAddProduct_thenProductTypeIsMatched() {
        Product electronics = new Product(4, "Electronics", "Bluetooth Speaker");

        assertThat(productService.addProduct(electronics)).extracting(Product::getType).as(electronics.getType());
    }

    @Test
    public void whenRemoveProductById_thenTwoProductReturned() {
        Product laptop = new Product(3, "Laptop", "Macbook Pro");

        assertThat(productService.removeProduct(3)).extracting(Product::getType).as(laptop.getType());
    }

    private void verifyGetByProductIdIsCalledOnce() {
        Mockito.verify(productRepository, VerificationModeFactory.times(1)).getProductById(Mockito.anyInt());
        Mockito.reset(productRepository);
    }

    private void verifyGetProductsIsCalledOnce() {
        Mockito.verify(productRepository, VerificationModeFactory.times(1)).getProducts();
        Mockito.reset(productRepository);
    }
}
