package com.baeldung.hexagonalpattern.adapter;

import java.util.List;

import com.baeldung.hexagonalpattern.entity.Product;
import com.baeldung.hexagonalpattern.port.ProductDaoInterface;

public class ProductDataAPIImpl implements ProductDaoInterface{

	@Override
	public List<Product> getAllProducts() {
		// TODO REST API Call logic here	

		return null;
	}

}
