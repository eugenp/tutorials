package com.baeldung.pattern.hexagonal2.domain.adapter.openlib;

import com.baeldung.pattern.hexagonal2.domain.port.BookServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Profile("openlib")
public class BookOpenlibServiceAdapter implements BookServicePort {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getBookTitle(String isbn) {
        OpenLibBookDTO dto = restTemplate.getForObject("https://openlibrary.org/isbn/" + isbn, OpenLibBookDTO.class);
        return dto.getTitle();
    }

}
