package com.baeldung.service.impl;

import java.util.List;

import com.baeldung.models.Category;

public interface CategoryService {

    Category createCategory(Category category);

    List<Category> getAllCategories();

    Category removeCategory(Long category);

    Category getCategoryById(Long categoryId);
}
