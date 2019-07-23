package com.baeldung.outside.driver.user_interface;

import com.baeldung.inside.domain.model.Quote;

public interface UserInterfacePort {
    Quote getQuote();

    boolean saveQuote(Quote quote);
}
