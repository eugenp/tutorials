package com.baeldung.architecture.hexagonal;

public class HttpApi implements ApiInterface {
    private MovieService service;

    public HttpApi(MovieService service) {
        this.service = service;
    }

    public Movie get(String name) {
        return service.search(name);
    }
}
