package com.baeldung.outside.driver.user_interface.spring_contoller;

import com.baeldung.inside.domain.model.Quote;
import com.baeldung.inside.domain.service.QuotesService;
import com.baeldung.outside.driver.user_interface.UserInterfacePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Component
public class UserInterfaceAdapter implements UserInterfacePort {

    private QuotesService quotesService;

    @Autowired
    public UserInterfaceAdapter(QuotesService quotesService) {
        this.quotesService = quotesService;
    }

    @RequestMapping("/quote")
    public Quote getQuoteForUser() {
        Quote quote = getQuote();
        if (quote == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No quote was found."
            );
        }
        return quote;
    }

    @RequestMapping(
            path = "/quote",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveQuoteForUser(@RequestBody Quote quote) {
        boolean isSaved = saveQuote(quote);
        if (isSaved) {
            return "Quote is saved";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Quote is not saved."
            );
        }
    }

    @Override
    public Quote getQuote() {
        return quotesService.getQuote();
    }

    @Override
    public boolean saveQuote(Quote quote) {
        return quotesService.createQuote(quote.getMessage(), quote.getAuthor());
    }

}
