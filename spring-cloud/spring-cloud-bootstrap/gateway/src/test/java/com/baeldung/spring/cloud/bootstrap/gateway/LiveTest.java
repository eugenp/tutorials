package com.baeldung.spring.cloud.bootstrap.gateway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.RestAssured;
import io.restassured.config.RedirectConfig;
import io.restassured.config.SessionConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.config;

public class LiveTest {

    private final String ROOT_URI = "http://localhost:8080";
    SessionFilter sessionFilter;

    @Before
    public void setup() {
        RestAssured.config = config()
            .redirect(RedirectConfig.redirectConfig().followRedirects(false))
            .sessionConfig(new SessionConfig().sessionIdName("SESSION"));
    }

    @Test
    public void whenGetAllBooks_thenSuccess() {
        final Response response = RestAssured.get(ROOT_URI + "/book-service/books");
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void whenAccessProtectedResourceWithoutLogin_thenRedirectToLogin() {
        final Response response = RestAssured.get(ROOT_URI + "/rating-service/ratings?bookId=1");
        Assert.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void whenAccessProtectedResourceAfterLogin_thenSuccess() {
        SessionData sessionData = login();
        final Response response = RestAssured.given()
            .auth().preemptive().basic("user", "password")
            .header("X-XSRF-TOKEN", sessionData.getCsrf())
            .filter(sessionFilter)
            .get(ROOT_URI + "/rating-service/ratings?bookId=1");
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void whenAccessAdminProtectedResource_thenForbidden() {
        SessionData sessionData = login();
        final Response response = RestAssured.given()
            .auth().preemptive().basic("user", "password")
            .header("X-XSRF-TOKEN", sessionData.getCsrf())
            .filter(sessionFilter)
            .get(ROOT_URI + "/rating-service/ratings");
        Assert.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode());

    }

    @Test
    public void whenAdminAccessProtectedResource_thenSuccess() {
        SessionData sessionData = login();
        final Response response = RestAssured.given()
            .auth().preemptive().basic("admin", "admin")
            .header("X-XSRF-TOKEN", sessionData.getCsrf())
            .filter(sessionFilter)
            .get(ROOT_URI + "/rating-service/ratings");
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void whenAdminAccessDiscoveryResource_thenSuccess() {
        SessionData sessionData = login();
        final Response response = RestAssured.given()
            .auth().preemptive().basic("admin", "admin")
            .header("X-XSRF-TOKEN", sessionData.getCsrf())
            .filter(sessionFilter)
            .get(ROOT_URI + "/discovery");
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenAddnewRating_thenSuccess() {

        final Rating rating = new Rating(1L, 4);

        SessionData sessionData = login();

        // request the protected resource
        final Response ratingResponse = RestAssured.given()
            .auth().preemptive().basic("admin", "admin")
            .header("X-XSRF-TOKEN", sessionData.getCsrf())
            .filter(sessionFilter)
            .and()
            .contentType(ContentType.JSON)
            .body(rating)
            .post(ROOT_URI + "/rating-service/ratings");
        final Rating result = ratingResponse.as(Rating.class);
        Assert.assertEquals(HttpStatus.OK.value(), ratingResponse.getStatusCode());
        Assert.assertEquals(rating.getBookId(), result.getBookId());
        Assert.assertEquals(rating.getStars(), result.getStars());
    }

    @Test
    public void whenAddnewBook_thenSuccess() {
        final Book book = new Book("Baeldung", "How to spring cloud");

        SessionData sessionData = login();

        // request the protected resource
        final Response bookResponse = RestAssured.given()
            .auth().preemptive().basic("admin", "admin")
            .header("X-XSRF-TOKEN", sessionData.getCsrf())
            .filter(sessionFilter)
            .and()
            .contentType(ContentType.JSON)
            .body(book)
            .post(ROOT_URI + "/book-service/books");
        final Book result = bookResponse.as(Book.class);
        Assert.assertEquals(HttpStatus.OK.value(), bookResponse.getStatusCode());
        Assert.assertEquals(book.getAuthor(), result.getAuthor());
        Assert.assertEquals(book.getTitle(), result.getTitle());

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Book {

        private Long id;
        private String author;
        private String title;

        public Book() {
        }

        public Book(String author, String title) {
            this.author = author;
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Rating {
        private Long id;
        private Long bookId;
        private int stars;

        public Rating() {
        }

        public Rating(Long bookId, int stars) {
            this.bookId = bookId;
            this.stars = stars;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getBookId() {
            return bookId;
        }

        public void setBookId(Long bookId) {
            this.bookId = bookId;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }
    }

    private SessionData login() {
        sessionFilter = new SessionFilter();
        Response getLoginResponse = RestAssured.given()
            .filter(sessionFilter)
            .when()
            .get("/")
            .then()
            .extract()
            .response();
        return new SessionData(getLoginResponse.cookie("XSRF-TOKEN"), sessionFilter.getSessionId());
    }

    private class SessionData {
        private String csrf;
        private String session;

        public SessionData(String csrf, String session) {
            this.csrf = csrf;
            this.session = session;
        }

        public String getCsrf() {
            return csrf;
        }

        public void setCsrf(String csrf) {
            this.csrf = csrf;
        }

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session = session;
        }
    }
}