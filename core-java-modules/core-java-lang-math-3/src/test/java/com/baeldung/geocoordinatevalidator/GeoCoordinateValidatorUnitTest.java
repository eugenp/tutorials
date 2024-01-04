package com.baeldung.geocoordinatevalidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GeoCoordinateValidatorUnitTest {

    @Test
    public void givenValidDDCoordinates_whenValidatingWithRegex_thenReturnsTrue() {
        GeoCoordinateValidator validator = new GeoCoordinateValidator();
        assertTrue(validator.isValidDDFormatWithRegex("34.0522  ,   -118.2437"));
        assertTrue(validator.isValidDDFormatWithRegex("-34.0522, 118.2437"));
        assertTrue(validator.isValidDDFormatWithRegex("-90.0, 180.0"));
        assertTrue(validator.isValidDDFormatWithRegex("90.0, -180.0"));
    }

    @Test
    public void givenInvalidDDCoordinates_whenValidatingWithRegex_thenReturnsFalse() {
        GeoCoordinateValidator validator = new GeoCoordinateValidator();
        assertFalse(validator.isValidDDFormatWithRegex("invalid"));
        assertFalse(validator.isValidDDFormatWithRegex("90degrees, 180degrees"));
        assertFalse(validator.isValidDDFormatWithCustom("90.000001, 0"));
    }

    @Test
    public void givenValidDMSCoordinates_whenValidatingWithRegex_thenReturnsTrue() {
        GeoCoordinateValidator validator = new GeoCoordinateValidator();
        assertTrue(validator.isValidDMSFormatWithRegex("45°30'15.5\"S  ,    123°45'59.999\"W"));
        assertTrue(validator.isValidDMSFormatWithRegex("45°30'N, 123°45'23.2\"W"));
        assertTrue(validator.isValidDMSFormatWithRegex("45°30'23.2\"N, 123°45'W"));
        assertTrue(validator.isValidDMSFormatWithRegex("45°30'N, 123°45'W"));
        assertTrue(validator.isValidDMSFormatWithRegex("34°12'34\"N, 118°14'37\"W"));
        assertTrue(validator.isValidDMSFormatWithRegex("34°12'34\"s, 118°14'37\"e"));
    }

    @Test
    public void givenInvalidDMSCoordinates_whenValidatingWithRegex_thenReturnsFalse() {
        GeoCoordinateValidator validator = new GeoCoordinateValidator();
        assertFalse(validator.isValidDMSFormatWithRegex("invalid"));
        assertFalse(validator.isValidDMSFormatWithRegex("91degress12'34\"W, 118degress14'37\"W"));
        assertFalse(validator.isValidDMSFormatWithRegex("1000°12'34\"N, 118°14'37\"W"));
        assertFalse(validator.isValidDMSFormatWithRegex("34°12'34\"W, 118°14'37\"N"));
    }

    @Test
    public void givenValidMGRSCoordinates_whenValidatingWithRegex_thenReturnsTrue() {
        GeoCoordinateValidator validator = new GeoCoordinateValidator();
        assertTrue(validator.isValidMGRSFormatWithRegex("33TWN1234567890"));
        assertTrue(validator.isValidMGRSFormatWithRegex("33TWN12346789"));
        assertTrue(validator.isValidMGRSFormatWithRegex("33TWN123678"));
        assertTrue(validator.isValidMGRSFormatWithRegex("33TWN1267"));
    }

    @Test
    public void givenInvalidMGRSCoordinates_whenValidatingWithRegex_thenReturnsFalse() {
        GeoCoordinateValidator validator = new GeoCoordinateValidator();
        assertFalse(validator.isValidMGRSFormatWithRegex("invalid"));
        assertFalse(validator.isValidMGRSFormatWithRegex("33TIO1234567890"));
        assertFalse(validator.isValidMGRSFormatWithRegex("1000TWN1234567890"));
    }

    @Test
    public void givenValidDDCoordinates_whenValidatingWithCustom_thenReturnsTrue() {
        GeoCoordinateValidator validator = new GeoCoordinateValidator();
        assertTrue(validator.isValidDDFormatWithCustom("34.0522, -118.2437"));
        assertTrue(validator.isValidDDFormatWithCustom("-34.0522, 118.2437"));
        assertTrue(validator.isValidDDFormatWithCustom("-90.0, 180.0"));
        assertTrue(validator.isValidDDFormatWithCustom("90.0, -180.0"));
    }

    @Test
    public void givenInvalidDDCoordinates_whenValidatingWithCustom_thenReturnsFalse() {
        GeoCoordinateValidator validator = new GeoCoordinateValidator();
        assertFalse(validator.isValidDDFormatWithCustom("90degrees, 180degrees"));
        assertFalse(validator.isValidDDFormatWithCustom("invalid"));
        assertFalse(validator.isValidDDFormatWithCustom("91.0, -118.2437"));
        assertFalse(validator.isValidDDFormatWithCustom("34.0522, -181.0"));
    }

    @Test
    public void givenValidDMSCoordinates_whenValidatingWithCustom_thenReturnsTrue() {
        GeoCoordinateValidator validator = new GeoCoordinateValidator();
        assertTrue(validator.isValidDMSFormatWithCustomValidation("34°12'34\"N, 118°14'37\"W"));
        assertTrue(validator.isValidDMSFormatWithCustomValidation("34°12'34\"s, 118°14'37\"e"));
        assertTrue(validator.isValidDMSFormatWithCustomValidation("45°30'N, 123°45'23.2\"W"));
        assertTrue(validator.isValidDMSFormatWithCustomValidation("45°30'23.2\"N, 123°45'W"));
        assertTrue(validator.isValidDMSFormatWithCustomValidation("45°30'N, 123°45'W"));
    }

    @Test
    public void givenInvalidDMSCoordinates_whenValidatingWithCustom_thenReturnsFalse() {
        GeoCoordinateValidator validator = new GeoCoordinateValidator();
        assertFalse(validator.isValidDMSFormatWithCustomValidation("invalid"));
        assertFalse(validator.isValidDMSFormatWithCustomValidation("91°12'34\"N, 118°14'37\"W"));
        assertFalse(validator.isValidDMSFormatWithCustomValidation("34°60'34\"N, 118°14'37\"W"));
        assertFalse(validator.isValidDMSFormatWithCustomValidation("34°12'60\"N, 118°14'37\"W"));
        assertFalse(validator.isValidDMSFormatWithCustomValidation("34°12'34\"N, 181°14'37\"W"));
    }
}
