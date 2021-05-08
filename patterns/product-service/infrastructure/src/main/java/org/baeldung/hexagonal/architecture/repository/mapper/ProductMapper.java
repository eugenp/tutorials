package org.baeldung.hexagonal.architecture.repository.mapper;

import java.util.List;

import org.baeldung.hexagonal.architecture.ProductDto;
import org.baeldung.hexagonal.architecture.repository.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
	
	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	Product toProduct(ProductDto productDto);
	
	ProductDto fromProduct(Product product);
	
	List<Product> toProductList(List<ProductDto> productDtoList);

    List<ProductDto> fromProductList(List<Product> productList);
}
