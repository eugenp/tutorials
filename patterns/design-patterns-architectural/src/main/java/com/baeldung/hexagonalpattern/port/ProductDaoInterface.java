package com.baeldung.hexagonalpattern.port;

import java.util.List;
import com.baeldung.hexagonalpattern.entity.Product;

public interface ProductDaoInterface {
	
	List<Product> getAllProducts();

}
