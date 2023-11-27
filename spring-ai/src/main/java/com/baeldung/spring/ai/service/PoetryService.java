package com.baeldung.spring.ai.service;

import com.baeldung.spring.ai.dto.PoetryDto;

public interface PoetryService {

    String getCatHaiku();

    PoetryDto getPoetryByGenreAndTheme(String genre, String theme);
}
