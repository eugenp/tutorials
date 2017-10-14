package com.baeldung.dependency_injection_types.runners;

import com.baeldung.dependency_injection_types.entities.Category;
import com.baeldung.dependency_injection_types.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;

public class CategoryImporter implements CommandLineRunner {

    private CategoryRepository categoryRepository;

    public CategoryImporter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        categoryRepository.add(Category
          .builder()
          .code("code1")
          .name("category1")
          .build());
        System.out.println("Imported a category");
    }
}
