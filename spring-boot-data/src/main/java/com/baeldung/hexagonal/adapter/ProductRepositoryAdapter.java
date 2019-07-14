package com.baeldung.hexagonal.adapter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.domain.Product;
import com.baeldung.hexagonal.port.ProductRepositoryPort;

/**
 * 
 * @author shwetaJ
 *
 */

@Service
public class ProductRepositoryAdapter implements ProductRepositoryPort{
	private static final Logger logger = LoggerFactory.getLogger(ProductRepositoryAdapter.class);

	@PersistenceContext
    private EntityManager entityManager;
    
	@Override
	@Transactional
	public Product createProduct(Product product){
		product.setProductId(0);
		entityManager.persist(product);
        return product;
	}
	
	@Override
	public Product getProduct(int productId){
        return entityManager.find(Product.class, productId);
	}
	
	@Override
	public List<Product> getAllProducts(){
		logger.info("Returning All Products");
		List<Product> retList = new ArrayList<>();
		
		Query qry = entityManager.createQuery("select prod from Product prod");
		retList = qry.getResultList();
		logger.info("REturned DAta: " + retList);
		
		/*
		Product product1 = new Product();
		product1.setProductId(1);
		product1.setProductDesc("Product 1 description goes here");
		product1.setProductCategory("Category-1");
		product1.setProductName("Product 1");
		product1.setProductPrice(100000);
		retList.add(product1);

		Product product2 = new Product();
		product2.setProductId(2);
		product2.setProductDesc("Product 2 description goes here");
		product2.setProductCategory("Category-1");
		product2.setProductName("Product 2");
		product2.setProductPrice(200000);
		retList.add(product2);
		*/
		return retList;
	}	
}
