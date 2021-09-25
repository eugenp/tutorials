package com.baeldung.repository;

import java.util.List;

import com.baeldung.models.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository {

    Category createCategory(Category category);

    List<Category> getAllCategories();

    Category removeCategory(Long categoryId);

    Category getCategoryById(Long categoryId);
}
