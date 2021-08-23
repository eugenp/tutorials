package com.baeldung.hexagonalpattern.service;

import java.util.List;
import com.baeldung.hexagonalpattern.entity.Product;
import com.baeldung.hexagonalpattern.port.ProductDaoInterface;

public class ProductDaoService {
	
	private ProductDaoInterface productDao;
	
	public List<Product> getAllProducts(){
		return productDao.getAllProducts();
	}

}
