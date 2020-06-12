package com.baeldung.hexagonal.user;

import com.baeldung.hexagonal.domain.ErrorTypeEnum;
import com.baeldung.hexagonal.domain.ShortenerException;
import com.baeldung.hexagonal.domain.service.ShortenedUrlService;
import com.baeldung.hexagonal.user.dto.CreateShortenedUrlRequest;
import com.baeldung.hexagonal.user.dto.CreateShortenedUrlResponse;
import com.baeldung.hexagonal.user.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorten-url")
public class ShortenUrlController {

    private ShortenedUrlService shortenedUrlService;

    public ShortenUrlController(ShortenedUrlService shortenedUrlService) {
        this.shortenedUrlService = shortenedUrlService;
    }

    @GetMapping(value = "/{code}")
    ResponseEntity<Void> get(@PathVariable("code") String code) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, shortenedUrlService.get(code))
                .build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    CreateShortenedUrlResponse create(@RequestBody CreateShortenedUrlRequest request) {
        return new CreateShortenedUrlResponse(shortenedUrlService.create(request.getUrl()));
    }

    @ExceptionHandler({ShortenerException.class})
    ResponseEntity<ErrorResponse> handleException(ShortenerException ex) {
        return ResponseEntity
                .status(ex.getErrorTypeEnum() == ErrorTypeEnum.URL_NOT_FOUND ?
                        HttpStatus.NOT_FOUND :
                        HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage()));
    }

}
