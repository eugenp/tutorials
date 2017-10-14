package com.baeldung.dependency_injection_types.repositories;

import com.baeldung.dependency_injection_types.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    private List<Category> categories = new ArrayList<>();

    public List<Category> findAll() {
        return categories;
    }

    public void add(Category category) {
        categories.add(category);
    }
}
