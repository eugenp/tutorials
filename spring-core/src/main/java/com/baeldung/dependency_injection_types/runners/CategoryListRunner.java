package com.baeldung.dependency_injection_types.runners;

import com.baeldung.dependency_injection_types.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;

public class CategoryListRunner implements CommandLineRunner {

    private CategoryRepository categoryRepository;


    @Override
    public void run(String... args) throws Exception {
        System.out.println(categoryRepository.findAll());
    }

    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
