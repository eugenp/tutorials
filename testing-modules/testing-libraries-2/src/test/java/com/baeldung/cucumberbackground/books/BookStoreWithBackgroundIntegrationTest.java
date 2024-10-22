package com.baeldung.cucumberbackground.books;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/book-store-with-background.feature")
public class BookStoreWithBackgroundIntegrationTest {

}

