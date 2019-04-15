package com.baeldung.datajpadelete.repository;

import com.baeldung.datajpadelete.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
