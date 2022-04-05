package com.baeldung.quarkus;

import com.baeldung.quarkus.service.LibraryService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.verify;

@QuarkusTest
class LibraryResourceInjectSpyIntegrationTest {

    @InjectSpy
    LibraryService libraryService;

    @Test
    void whenGetBooksByAuthor_thenBookShouldBeFound() {
        given().contentType(ContentType.JSON).param("query", "Asimov")
          .when().get("/library/book")
          .then().statusCode(200);

        verify(libraryService).find("Asimov");
    }

}
