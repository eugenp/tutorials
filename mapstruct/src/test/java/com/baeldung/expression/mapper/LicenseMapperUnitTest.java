package com.baeldung.expression.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.expression.dto.LicenseDto;
import com.baeldung.expression.model.License;

class LicenseMapperUnitTest {

    LicenseMapper licenseMapper = Mappers.getMapper(LicenseMapper.class);

    @Test
    void givenLicenseDtoWithStartDateAndWithoutEndDate_WhenMapperMethodIsInvoked_ThenLicenseShouldBePopulatedWithDefaultEndDate() {
        LicenseDto licenseDto = new LicenseDto();
        licenseDto.setStartDate(LocalDateTime.now());
        License license = licenseMapper.toLicense(licenseDto);
        assertThat(license).isNotNull();
        assertThat(license.getEndDate()
            .toLocalDate()).isEqualTo(LocalDate.now()
            .plusYears(1));
    }

    @Test
    void givenLicenseDtoWithEndDateAndWithoutStartDate_WhenMapperMethodIsInvoked_ThenLicenseShouldBePopulatedWithDefaultStartDate() {
        LicenseDto licenseDto = new LicenseDto();
        licenseDto.setEndDate(LocalDateTime.now()
            .plusYears(2));
        License license = licenseMapper.toLicense(licenseDto);
        assertThat(license).isNotNull();
        assertThat(license.getStartDate()
            .toLocalDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void givenLicenseDtoWithoutStartDateAndEndDate_WhenMapperMethodIsInvoked_ThenLicenseShouldBePopulatedWithDefaultDetails() {
        LicenseDto licenseDto = new LicenseDto();
        License license = licenseMapper.toLicense(licenseDto);
        assertThat(license).isNotNull();
        assertThat(license.getStartDate()
            .toLocalDate()).isEqualTo(LocalDate.now());
        assertThat(license.getEndDate()
            .toLocalDate()).isEqualTo(LocalDate.now()
            .plusYears(1));
        assertThat(license.isActive()).isTrue();
        assertThat(license.isRenewalRequired()).isFalse();
    }

    @Test
    void givenLicenseDtoWithEndDateInTwoWeeks_WhenMapperMethodIsInvoked_ThenLicenseShouldBePopulatedWithRenewalRequiredSetToTrue() {
        LicenseDto licenseDto = new LicenseDto();
        licenseDto.setEndDate(LocalDateTime.now()
            .plusDays(10));
        License license = licenseMapper.toLicense(licenseDto);
        assertThat(license).isNotNull();
        assertThat(license.isRenewalRequired()).isTrue();
    }

    @Test
    void givenLicenseDtoWithoutId_WhenMapperMethodIsInvoked_ThenLicenseShouldBePopulatedWithValidId() {
        LicenseDto licenseDto = new LicenseDto();
        UUID id = UUID.randomUUID();
        licenseDto.setId(id);
        licenseDto.setEndDate(LocalDateTime.now()
            .plusDays(10));
        License license = licenseMapper.toLicense(licenseDto);
        assertThat(license).isNotNull();
        assertThat(license.getId()).isSameAs(id);
    }

    @Test
    void givenLicenseDtoWithoutLicenseTypeString_whenMapperMethodIsInvoked_thenLicenseShouldBePopulatedWithoutLicenseType() {
        LicenseDto licenseDto = new LicenseDto();
        License license = licenseMapper.toLicense(licenseDto);
        assertThat(license).isNotNull();
        Assertions.assertNull(license.getLicenseType());
    }

    @Test
    void givenLicenseDtoWithInvalidLicenseTypeString_whenMapperMethodIsInvoked_thenLicenseShouldBePopulatedWithoutLicenseType() {
        LicenseDto licenseDto = new LicenseDto();
        licenseDto.setLicenseType("invalid_license_type");
        License license = licenseMapper.toLicense(licenseDto);
        assertThat(license).isNotNull();
        Assertions.assertNull(license.getLicenseType());
    }

    @Test
    void givenLicenseDtoWithValidLicenseTypeString_whenMapperMethodIsInvoked_thenLicenseShouldBePopulatedWithMatchingLicenseType() {
        LicenseDto licenseDto = new LicenseDto();
        licenseDto.setLicenseType("INDIVIDUAL");
        License license = licenseMapper.toLicense(licenseDto);
        assertThat(license).isNotNull();
        Assertions.assertNotNull(license.getLicenseType());
        assertThat(license.getLicenseType()).isEqualTo(License.LicenseType.INDIVIDUAL);
    }

}