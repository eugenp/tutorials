package com.baeldung.cassandra.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.cassandra.batch.domain.Product;
import com.baeldung.cassandra.batch.repository.KeyspaceRepository;
import com.baeldung.cassandra.batch.repository.ProductRepository;
import com.datastax.oss.driver.api.core.CqlSession;

public class Application {
	
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        CassandraConnector connector = new CassandraConnector();
        connector.connect("127.0.0.1", 9042, "datacenter1");
        CqlSession session = connector.getSession();

        KeyspaceRepository keyspaceRepository = new KeyspaceRepository(session);

        keyspaceRepository.createKeyspace("testKeyspace", 1);
        keyspaceRepository.useKeyspace("testKeyspace");

        ProductRepository productRepository = new ProductRepository(session);

        productRepository.createProductTable("testKeyspace");
        productRepository.createProductByIdTable("testKeyspace");
        productRepository.createProductByIdTable("testKeyspace");
        Product product = getProduct();
        productRepository.insertProductBatch(product);
        
        Product productV1 = getProduct();
        Product productV2 = getProduct();
        
        productRepository.insertProductVariantBatch(productV1, productV2);
        
      
        List<Product> products = productRepository.selectAllProduct("testKeyspace");
        products.forEach(x -> LOG.info(x.toString()));
        connector.close();
    }

    private Product getProduct() {
        Product product = new Product();
        product.setProductName("Banana");
        product.setDescription("Banana");
        product.setPrice(12f);
		
        return product;
    }
}


