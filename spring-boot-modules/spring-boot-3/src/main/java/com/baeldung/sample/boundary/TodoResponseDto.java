package com.baeldung.sample.boundary;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TodoResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String status;

}
