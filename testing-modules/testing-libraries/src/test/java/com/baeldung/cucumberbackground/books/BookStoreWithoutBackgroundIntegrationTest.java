package com.baeldung.cucumberbackground.books;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/book-store-without-background.feature")
public class BookStoreWithoutBackgroundIntegrationTest {

}

