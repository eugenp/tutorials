package com.baeldung.jacksonlazyfields.dto;

import java.util.List;

public record DepartmentDto(Long id, String name, List<String> courseNames) {
}
