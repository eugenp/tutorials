package com.baeldung.transformers.dto;

import java.util.List;

public record DepartmentStudentsDto(String department, List<String> students) {
}
