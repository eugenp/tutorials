package com.baeldung.outside.driven.Repository;

import com.baeldung.outside.driven.Repository.database.QuoteDBModel;

public interface RepositoryPort {

    boolean createQuote(String message, String author);

    QuoteDBModel getQuote();
}
