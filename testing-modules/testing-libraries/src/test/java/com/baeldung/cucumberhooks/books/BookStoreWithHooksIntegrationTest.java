package com.baeldung.cucumberhooks.books;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/book-store-with-hooks.feature",
  glue = "com.baeldung.cucumberhooks.books"
)
public class BookStoreWithHooksIntegrationTest {

}
