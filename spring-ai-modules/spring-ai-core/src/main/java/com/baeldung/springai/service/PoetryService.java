package com.baeldung.springai.service;

import com.baeldung.springai.dto.PoetryDto;

public interface PoetryService {

    String getCatHaiku();

    PoetryDto getPoetryByGenreAndTheme(String genre, String theme);
}
