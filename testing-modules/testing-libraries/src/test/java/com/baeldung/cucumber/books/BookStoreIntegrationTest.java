package com.baeldung.cucumber.books;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/book-store.feature")
public class BookStoreIntegrationTest {

}

