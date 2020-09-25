package com.baeldung.architecture.hexagonal;

public class HttpApi implements ApiInterface {
    private MovieService service;

    public HttpApi(MovieService service) {
        this.service = service;
    }

    // TODO implement HTTP endpoint
    public Movie get(String name) {
        return service.search(name);
    }
}
