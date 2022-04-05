	package com.baeldung.spring.data.solr.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.data.solr.config.SolrConfig;
import com.baeldung.spring.data.solr.model.Product;
import com.baeldung.spring.data.solr.repository.ProductRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SolrConfig.class)
public class ProductRepositoryLiveTest {

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void clearSolrData() {
        productRepository.deleteAll();
    }

    @Test
    public void whenSavingProduct_thenAvailableOnRetrieval() throws Exception {
        final Product product = new Product();
        product.setId("P000089998");
        product.setName("Desk");
        productRepository.save(product);
        final Product retrievedProduct = productRepository.findById(product.getId()).get();
        assertEquals(product.getId(), retrievedProduct.getId());
    }

    @Test
    public void whenUpdatingProduct_thenChangeAvailableOnRetrieval() throws Exception {
        final Product product = new Product();
        product.setId("P0001");
        product.setName("T-Shirt");

        productRepository.save(product);

        product.setName("Shirt");
        productRepository.save(product);

        final Product retrievedProduct = productRepository.findById(product.getId()).get();
        assertEquals(product.getName(), retrievedProduct.getName());
    }

    @Test
    public void whenDeletingProduct_thenNotAvailableOnRetrieval() throws Exception {
        final Product product = new Product();
        product.setId("P0001");
        product.setName("Desk");
        productRepository.save(product);

        productRepository.delete(product);

        Product retrievedProduct = productRepository.findById(product.getId()).get();
        assertNull(retrievedProduct);

    }

    @Test
    public void whenFindByName_thenAvailableOnRetrieval() throws Exception {
        Product phone = new Product();
        phone.setId("P0001");
        phone.setName("Phone");
        productRepository.save(phone);

        List<Product> retrievedProducts = productRepository.findByName("Phone");
        assertEquals(phone.getId(), retrievedProducts.get(0).getId());
    }

    @Test
    public void whenSearchingProductsByQuery_thenAllMatchingProductsShouldAvialble() throws Exception {
        final Product phone = new Product();
        phone.setId("P0001");
        phone.setName("Smart Phone");
        productRepository.save(phone);

        final Product phoneCover = new Product();
        phoneCover.setId("P0002");
        phoneCover.setName("Phone Cover");
        productRepository.save(phoneCover);

        final Product wirelessCharger = new Product();
        wirelessCharger.setId("P0003");
        wirelessCharger.setName("Phone Charging Cable");
        productRepository.save(wirelessCharger);

        Page<Product> result = productRepository.findByCustomQuery("Phone", PageRequest.of(0, 10));
        assertEquals(3, result.getNumberOfElements());
    }

    @Test
    public void whenSearchingProductsByNamedQuery_thenAllMatchingProductsShouldAvialble() throws Exception {
        final Product phone = new Product();
        phone.setId("P0001");
        phone.setName("Smart Phone");
        productRepository.save(phone);

        final Product phoneCover = new Product();
        phoneCover.setId("P0002");
        phoneCover.setName("Phone Cover");
        productRepository.save(phoneCover);

        final Product wirelessCharger = new Product();
        wirelessCharger.setId("P0003");
        wirelessCharger.setName("Phone Charging Cable");
        productRepository.save(wirelessCharger);

        Page<Product> result = productRepository.findByNamedQuery("one", PageRequest.of(0, 10));
        assertEquals(3, result.getNumberOfElements());
    }

}
