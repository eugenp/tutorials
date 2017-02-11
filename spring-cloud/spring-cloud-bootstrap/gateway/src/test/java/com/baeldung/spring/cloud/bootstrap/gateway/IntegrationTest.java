package com.baeldung.spring.cloud.bootstrap.gateway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class IntegrationTest {

    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    private String testUrl = "http://localhost:8080";

    @Test
    public void testAccess() throws Exception {
        ResponseEntity<String> response = testRestTemplate.getForEntity(testUrl + "/book-service/books", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());

        //try the protected resource and confirm the redirect to login
        response = testRestTemplate.getForEntity(testUrl + "/book-service/books/1", String.class);
        Assert.assertEquals(HttpStatus.FOUND, response.getStatusCode());
        Assert.assertEquals("http://localhost:8080/login", response.getHeaders().get("Location").get(0));

        //login as user/password
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", "user");
        form.add("password", "password");
        response = testRestTemplate.postForEntity(testUrl + "/login", form, String.class);

        //extract the session from the cookie and propagate it to the next request
        String sessionCookie = response.getHeaders().get("Set-Cookie").get(0).split(";")[0];
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionCookie);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        addBook();

        //request the protected resource
        response = testRestTemplate.exchange(testUrl + "/book-service/books/1", HttpMethod.GET, httpEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());

        addRatings();

        //request the admin protected resource to determine it is still protected
        response = testRestTemplate.exchange(testUrl + "/rating-service/ratings", HttpMethod.GET, httpEntity, String.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        //login as the admin
        form.clear();
        form.add("username", "admin");
        form.add("password", "admin");
        response = testRestTemplate.postForEntity(testUrl + "/login", form, String.class);

        //extract the session from the cookie and propagate it to the next request
        sessionCookie = response.getHeaders().get("Set-Cookie").get(0).split(";")[0];
        headers = new HttpHeaders();
        headers.add("Cookie", sessionCookie);
        httpEntity = new HttpEntity<>(headers);

        //request the protected resource
        response = testRestTemplate.exchange(testUrl + "/rating-service/ratings", HttpMethod.GET, httpEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());

        //request the discovery resources as the admin
        response = testRestTemplate.exchange(testUrl + "/discovery", HttpMethod.GET, httpEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void addRatings() {
        //login as user/password
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", "user");
        form.add("password", "password");
        ResponseEntity<String> response = testRestTemplate.postForEntity(testUrl + "/login", form, String.class);

        //extract the session from the cookie and propagate it to the next request
        String sessionCookie = response.getHeaders().get("Set-Cookie").get(0).split(";")[0];
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionCookie);
        headers.add("ContentType", ContentType.APPLICATION_JSON.getMimeType());
        Rating rating = new Rating(1L, 4);

        HttpEntity<Rating> httpEntity = new HttpEntity<>(rating, headers);

        //request the protected resource
        ResponseEntity<Rating> bookResponse = testRestTemplate.postForEntity(testUrl + "/rating-service/ratings", httpEntity, Rating.class);
        Assert.assertEquals(HttpStatus.OK, bookResponse.getStatusCode());
        Assert.assertEquals(rating.getBookId(), bookResponse.getBody().getBookId());
        Assert.assertEquals(rating.getStars(), bookResponse.getBody().getStars());
    }

    private void addBook(){
        //login as user/password
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", "admin");
        form.add("password", "admin");
        ResponseEntity<String> response = testRestTemplate.postForEntity(testUrl + "/login", form, String.class);

        //extract the session from the cookie and propagate it to the next request
        String sessionCookie = response.getHeaders().get("Set-Cookie").get(0).split(";")[0];
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionCookie);
        headers.add("ContentType", ContentType.APPLICATION_JSON.getMimeType());
        Book book = new Book("Baeldung", "How to spring cloud");

        HttpEntity<Book> httpEntity = new HttpEntity<>(book, headers);

        //request the protected resource
        ResponseEntity<Book> bookResponse = testRestTemplate.postForEntity(testUrl + "/book-service/books", httpEntity, Book.class);
        Assert.assertEquals(HttpStatus.OK, bookResponse.getStatusCode());
        Assert.assertEquals(book.getAuthor(), bookResponse.getBody().getAuthor());
        Assert.assertEquals(book.getTitle(), bookResponse.getBody().getTitle());
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


}