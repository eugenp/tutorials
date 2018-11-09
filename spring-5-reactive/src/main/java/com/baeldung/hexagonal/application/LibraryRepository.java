package com.baeldung.hexagonal.application;


import com.baeldung.hexagonal.doc.Port;
import com.baeldung.hexagonal.domain.Article;

import java.util.Optional;

@Port
public interface LibraryRepository {

    Optional<Article> findBy(String title);
}
