package com.article.hexagonal.architecture.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.article.hexagonal.architecture.dtos.ProductDto;
import com.article.hexagonal.architecture.model.Product;
import com.article.hexagonal.architecture.service.ProductService;

/**
 * @author AshwiniKeshri
 *
 */

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<ProductDto> list() {
		productService.findAll().stream().map(x->x.getDescription())
	    return null;
	    //return productService.findAll().stream().map(p -> new ProductDto(p)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public ProductDto get(@PathVariable long productId) {
		Product p = productService.findById(productId);
		return p != null ? new ProductDto(p) : null;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ProductDto create(@RequestBody ProductDto product) {
		Product p = new Product();
		p.setDescription(product.getDescription());
		p.setName(product.getName());
		p.setPrice(product.getPrice());
		p.setQuantity(product.getQuantity());
		Long id = productService.create(p);
		product.setId(id);
		return product;
	}

}
