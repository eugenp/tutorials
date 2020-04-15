package com.baeldung.cucumberbackground.books;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/book-store-with-background.feature")
public class BookStoreWithBackgroundIntegrationTest {

}

