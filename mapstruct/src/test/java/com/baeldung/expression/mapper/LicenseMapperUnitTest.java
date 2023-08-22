package com.baeldung.expression.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.expression.dto.LicenseDto;
import com.baeldung.expression.model.License;

class LicenseMapperUnitTest {

    LicenseMapper licenseMapper = Mappers.getMapper(LicenseMapper.class);

    @Test
    void givenLicenseDtoWithStartDateAndWithoutEndDate_WhenMapperMethodIsInvoked_ThenLicenseShouldBePopulatedWithDefaultEndDate() {
        License license = licenseMapper.toLicense(LicenseDto.builder()
            .startDate(LocalDateTime.now())
            .build());
        assertThat(license).isNotNull();
        assertThat(license.getEndDate()
            .toLocalDate()).isEqualTo(LocalDate.now()
            .plusYears(1));
    }

    @Test
    void givenLicenseDtoWithEndDateAndWithoutStartDate_WhenMapperMethodIsInvoked_ThenLicenseShouldBePopulatedWithDefaultStartDate() {
        License license = licenseMapper.toLicense(LicenseDto.builder()
            .endDate(LocalDateTime.now()
                .plusYears(2))
            .build());
        assertThat(license).isNotNull();
        assertThat(license.getStartDate()
            .toLocalDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void givenLicenseDtoWithoutStartDateAndEndDate_WhenMapperMethodIsInvoked_ThenLicenseShouldBePopulatedWithDefaultDetails() {
        License license = licenseMapper.toLicense(LicenseDto.builder()
            .build());
        assertThat(license).isNotNull()
            .satisfies(l -> {
                assertThat(l.getStartDate()
                    .toLocalDate()).isEqualTo(LocalDate.now());
                assertThat(l.getEndDate()
                    .toLocalDate()).isEqualTo(LocalDate.now()
                    .plusYears(1));
                assertThat(l.isActive()).isTrue();
                assertThat(l.isRenewalRequired()).isFalse();
            });
    }

    @Test
    void givenLicenseDtoWithEndDateInTwoWeeks_WhenMapperMethodIsInvoked_ThenLicenseShouldBePopulatedWithRenewalRequiredSetToTrue() {
        License license = licenseMapper.toLicense(LicenseDto.builder()
            .endDate(LocalDateTime.now()
                .plusDays(10))
            .build());
        assertThat(license).isNotNull();
        assertThat(license.isRenewalRequired()).isTrue();
    }

}