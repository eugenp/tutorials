package com.baeldung.pattern.hexagonal2.domain.adapter.google;

import com.baeldung.pattern.hexagonal2.domain.port.BookServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Profile("google")
public class BookGoogleServiceAdapter implements BookServicePort {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getBookTitle(String isbn) {
        GoogleBookDTO dto = restTemplate.getForObject("https://www.googleapis.com/books/v1/volumes?q=isbn" + isbn, GoogleBookDTO.class);
        return dto.getItems().get(0).getVolumeInfo().getTitle();
    }

}
