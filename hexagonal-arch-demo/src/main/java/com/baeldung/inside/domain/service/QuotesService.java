package com.baeldung.inside.domain.service;

import com.baeldung.inside.domain.model.Quote;
import com.baeldung.outside.driven.Repository.RepositoryPort;
import com.baeldung.outside.driven.Repository.database.QuoteDBModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuotesService {

    private RepositoryPort repositoryPort;

    @Autowired
    public QuotesService(RepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public boolean createQuote(String message, String author) {
        return repositoryPort.createQuote(message, author);
    }

    public Quote getQuote() {
        return _convertDBModelToDomain(repositoryPort.getQuote());
    }

    private Quote _convertDBModelToDomain(QuoteDBModel quoteDBModel) {
        if (quoteDBModel == null) {
            return null;
        }
        return new Quote(quoteDBModel.getMessage(), quoteDBModel.getAuthor());
    }

}
