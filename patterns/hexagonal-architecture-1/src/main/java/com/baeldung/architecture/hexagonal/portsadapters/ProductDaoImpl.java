package com.baeldung.architecture.hexagonal.portsadapters;

import com.baeldung.architecture.hexagonal.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDaoImpl implements ProductDao {

  @Autowired
  ProductRepository repository;

  @Override
  public int getPrice(String productId) {
    return repository.findById(productId)
        .map(productRow -> productRow.getPrice())
        .orElse(0);
  }
}
