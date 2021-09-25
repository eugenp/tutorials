package com.baeldung.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baeldung.models.Category;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private static final Map<Long, Category> categoryMap = new HashMap<>(0);

    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(categoryMap.values());
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryMap.get(categoryId);
    }

    @Override
    public Category createCategory(Category category) {
        categoryMap.put(category.getCategoryId(), category);
        return category;
    }

    @Override
    public Category removeCategory(Long categoryId) {
        if (categoryMap.get(categoryId) != null) {
            Category category = categoryMap.get(categoryId);
            categoryMap.remove(categoryId);
            return category;
        } else return null;
    }


}
