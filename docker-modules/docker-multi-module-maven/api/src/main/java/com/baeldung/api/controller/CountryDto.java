package com.baeldung.api.controller;

public class CountryDto {

    private final Long id;
    private final String name;
    private final String code;
    private final String emoji;

    public CountryDto(Long id, String name, String code, String emoji) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.emoji = emoji;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getEmoji() {
        return emoji;
    }
}
