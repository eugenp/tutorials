package com.baeldung.expression.mapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.mapstruct.AfterMapping;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.baeldung.expression.dto.LicenseDto;
import com.baeldung.expression.model.License;

@Mapper
public interface LicenseMapper {

    @Mapping(target = "startDate", expression = "java(mapStartDate(licenseDto))")
    @Mapping(target = "endDate", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "renewalRequired", conditionExpression = "java(isEndDateInTwoWeeks(licenseDto))", source = ".")
    License toLicense(LicenseDto licenseDto);

    @AfterMapping
    default void afterMapping(LicenseDto licenseDto, @MappingTarget License license) {
        OffsetDateTime endDate = licenseDto.getEndDate() != null ? licenseDto.getEndDate()
            .atOffset(ZoneOffset.UTC) : OffsetDateTime.now()
            .plusYears(1);
        license.setEndDate(endDate);
    }

    default OffsetDateTime mapStartDate(LicenseDto licenseDto) {
        return licenseDto.getStartDate() != null ? licenseDto.getStartDate()
            .atOffset(ZoneOffset.UTC) : OffsetDateTime.now();
    }

    default boolean isEndDateInTwoWeeks(LicenseDto licenseDto) {
        return licenseDto.getEndDate() != null && Duration.between(licenseDto.getEndDate(), LocalDateTime.now())
            .toDays() <= 14;
    }

    @Condition
    default boolean mapsToExpectedLicenseType(String licenseType) {
        try {
            return licenseType != null && License.LicenseType.valueOf(licenseType) != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}