package com.baeldung.expression.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class LicenseDto {

    private UUID id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
