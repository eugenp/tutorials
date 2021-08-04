package com.baeldung.eval.hexagonal.store;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostRepository  extends JpaRepository<JpaPost, String> {

}
