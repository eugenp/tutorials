package com.baeldung.sample.boundary;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TodoRequestDto {
    @NotBlank
    private String title;
    private String description;
    private LocalDate dueDate;
    @Pattern(regexp = "new|progress|completed|archived")
    private String status;
}
