package com.baeldung.cassandra.batch.epository;

import com.baeldung.cassandra.batch.domain.Product;
import com.baeldung.cassandra.batch.repository.KeyspaceRepository;
import com.baeldung.cassandra.batch.repository.ProductRepository;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ColumnDefinition;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testcontainers.containers.CassandraContainer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductRepositoryLiveTest {
    @Rule
    public CassandraContainer<?> cassandra = new CassandraContainer<>("cassandra:3.11.2");
    private KeyspaceRepository schemaRepository;

    private ProductRepository productRepository;

    private CqlSession session;

    private final String KEYSPACE_NAME = "testBaeldungKeyspace";
    private final String PRODUCT = "product";

    @Before
    public void connect() {
        cassandra.start();

        this.session =  CqlSession
                .builder()
                .addContactPoint(new InetSocketAddress(cassandra.getHost(),cassandra.getFirstMappedPort()))
                .withLocalDatacenter("datacenter1")
                .build();

        schemaRepository = new KeyspaceRepository(this.session);
        schemaRepository.createKeyspace(KEYSPACE_NAME, 1);
        schemaRepository.useKeyspace(KEYSPACE_NAME);
        productRepository = new ProductRepository(this.session);
    }
    
    @Test
    public void whenCreatingAProductTable_thenCreatedCorrectly() {
        productRepository.deleteTable(KEYSPACE_NAME);
        productRepository.createProductTable(KEYSPACE_NAME);

        ResultSet result = session.execute("SELECT * FROM " + KEYSPACE_NAME + "." + PRODUCT + ";");

        List<ColumnDefinition> colDef = new ArrayList<>();
        
        result.getColumnDefinitions().forEach(columnDef -> colDef.add(columnDef));
        List<String> columnNames = colDef.stream().map(ColumnDefinition::getName).map(CqlIdentifier::toString).collect(Collectors.toList());
        assertEquals(columnNames.size(), 5);
        assertTrue(columnNames.contains("product_id"));
        assertTrue(columnNames.contains("variant_id"));
        assertTrue(columnNames.contains("product_name"));
        assertTrue(columnNames.contains("description"));
        assertTrue(columnNames.contains("price"));
    }

    @Test
    public void whenCreatingRelatedProductBatch_thenCreatedCorrectly() {
        productRepository.deleteTable(KEYSPACE_NAME);
        productRepository.createProductTableByName(KEYSPACE_NAME);
        productRepository.createProductByIdTable(KEYSPACE_NAME);
    	
        Product product = getTestProduct();
        productRepository.insertProductBatch(product);
        List<Product> productByIdList = productRepository.selectAllProductById(KEYSPACE_NAME);
        List<Product> productByNameList = productRepository.selectAllProductByName(KEYSPACE_NAME);
        
        assertEquals(productByIdList.size(), 1);
        assertEquals(productByNameList.size(), 1);
        assertEquals(productByIdList.get(0).getProductName(), "Banana");
        assertEquals(productByNameList.get(0).getProductName(), "Banana");
        assertEquals(productByIdList.get(0).getDescription(), "Banana");
        assertEquals(productByNameList.get(0).getDescription(), "Banana");
        assertEquals(productByIdList.get(0).getPrice(), 12f,0f);
        assertEquals(productByNameList.get(0).getPrice(), 12f,0f);      
    }
    
    @Test
    public void whenCreatingMultiVariantProductBatch_thenCreatedCorrectly() {
        productRepository.deleteTable(KEYSPACE_NAME);
        productRepository.createProductTable(KEYSPACE_NAME);
    	
        Product productV1 = getTestProduct();
        Product productV2 = getTestProduct();
        productRepository.insertProductVariantBatch(productV1, productV2);
        List<Product> productList = productRepository.selectAllProduct(KEYSPACE_NAME);
        
        assertEquals(productList.size(), 2);
        assertEquals(productList.get(0).getProductName(), "Banana");
        assertEquals(productList.get(1).getProductName(), "Banana");   
        assertEquals(productList.get(0).getDescription(), "Banana");
        assertEquals(productList.get(1).getDescription(), "Banana");
        assertEquals(productList.get(0).getPrice(), 12f,0f);
        assertEquals(productList.get(1).getPrice(), 12f,0f);
    }

    private Product getTestProduct() {
        Product product = new Product();
        product.setProductName("Banana");
        product.setDescription("Banana");
        product.setPrice(12f);
		
        return product;
    }
}
