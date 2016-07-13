package com.baeldung.implicitconstructorinj;

public class FooService {

	private final FooRepository repository;

	public FooService(FooRepository repository) {
        this.repository = repository;
    }
}