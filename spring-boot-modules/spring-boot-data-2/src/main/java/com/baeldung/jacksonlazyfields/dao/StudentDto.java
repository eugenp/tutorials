package com.baeldung.jacksonlazyfields.dao;

import java.util.List;

public record StudentDto(Long id, String name, List<String> courseNames) {
}
