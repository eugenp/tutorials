package com.baeldung.geocoordinatevalidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GeoCoordinateValidatorUnitTest {

    @Test
    public void givenValidDDCoordinates_whenValidatingWithRegex_thenReturnsTrue() {
        assertTrue(GeoCoordinateValidator.isValidDDFormatWithRegex("34.0522  ,   -118.2437"));
        assertTrue(GeoCoordinateValidator.isValidDDFormatWithRegex("-34.0522, 118.2437"));
        assertTrue(GeoCoordinateValidator.isValidDDFormatWithRegex("-90.0, 180.0"));
        assertTrue(GeoCoordinateValidator.isValidDDFormatWithRegex("90.0, -180.0"));
    }

    @Test
    public void givenInvalidDDCoordinates_whenValidatingWithRegex_thenReturnsFalse() {
        assertFalse(GeoCoordinateValidator.isValidDDFormatWithRegex("invalid"));
        assertFalse(GeoCoordinateValidator.isValidDDFormatWithRegex("90degrees, 180degrees"));
        assertFalse(GeoCoordinateValidator.isValidDDFormatWithRegex("90.000001, 0"));
    }

    @Test
    public void givenValidDMSCoordinates_whenValidatingWithRegex_thenReturnsTrue() {
        assertTrue(GeoCoordinateValidator.isValidDMSFormatWithRegex("45°30'15.5\"S  ,    123°45'59.999\"W"));
        assertTrue(GeoCoordinateValidator.isValidDMSFormatWithRegex("45°30'N, 123°45'23.2\"W"));
        assertTrue(GeoCoordinateValidator.isValidDMSFormatWithRegex("45°30'23.2\"N, 123°45'W"));
        assertTrue(GeoCoordinateValidator.isValidDMSFormatWithRegex("45°30'N, 123°45'W"));
        assertTrue(GeoCoordinateValidator.isValidDMSFormatWithRegex("34°12'34\"N, 118°14'37\"W"));
        assertTrue(GeoCoordinateValidator.isValidDMSFormatWithRegex("34°12'34\"s, 118°14'37\"e"));
    }

    @Test
    public void givenInvalidDMSCoordinates_whenValidatingWithRegex_thenReturnsFalse() {
        assertFalse(GeoCoordinateValidator.isValidDMSFormatWithRegex("invalid"));
        assertFalse(GeoCoordinateValidator.isValidDMSFormatWithRegex("91degress12'34\"W, 118degress14'37\"W"));
        assertFalse(GeoCoordinateValidator.isValidDMSFormatWithRegex("1000°12'34\"N, 118°14'37\"W"));
        assertFalse(GeoCoordinateValidator.isValidDMSFormatWithRegex("34°12'34\"W, 118°14'37\"N"));
    }

    @Test
    public void givenValidMGRSCoordinates_whenValidatingWithRegex_thenReturnsTrue() {
        assertTrue(GeoCoordinateValidator.isValidMGRSFormatWithRegex("33TWN1234567890"));
        assertTrue(GeoCoordinateValidator.isValidMGRSFormatWithRegex("33TWN12346789"));
        assertTrue(GeoCoordinateValidator.isValidMGRSFormatWithRegex("33TWN123678"));
        assertTrue(GeoCoordinateValidator.isValidMGRSFormatWithRegex("33TWN1267"));
    }

    @Test
    public void givenInvalidMGRSCoordinates_whenValidatingWithRegex_thenReturnsFalse() {
        assertFalse(GeoCoordinateValidator.isValidMGRSFormatWithRegex("invalid"));
        assertFalse(GeoCoordinateValidator.isValidMGRSFormatWithRegex("33TIO1234567890"));
        assertFalse(GeoCoordinateValidator.isValidMGRSFormatWithRegex("1000TWN1234567890"));
    }

    @Test
    public void givenValidDDCoordinates_whenValidatingWithCustomValidation_thenReturnsTrue() {
        assertTrue(GeoCoordinateValidator.isValidDDFormatWithCustomValidation("34.0522, -118.2437"));
        assertTrue(GeoCoordinateValidator.isValidDDFormatWithCustomValidation("-34.0522, 118.2437"));
        assertTrue(GeoCoordinateValidator.isValidDDFormatWithCustomValidation("-90.0, 180.0"));
        assertTrue(GeoCoordinateValidator.isValidDDFormatWithCustomValidation("90.0, -180.0"));
    }

    @Test
    public void givenInvalidDDCoordinates_whenValidatingWithCustomValidation_thenReturnsFalse() {
        assertFalse(GeoCoordinateValidator.isValidDDFormatWithCustomValidation("90degrees, 180degrees"));
        assertFalse(GeoCoordinateValidator.isValidDDFormatWithCustomValidation("invalid"));
        assertFalse(GeoCoordinateValidator.isValidDDFormatWithCustomValidation("91.0, -118.2437"));
        assertFalse(GeoCoordinateValidator.isValidDDFormatWithCustomValidation("34.0522, -181.0"));
    }

    @Test
    public void givenValidDMSCoordinates_whenValidatingWithCustomValidation_thenReturnsTrue() {
        assertTrue(GeoCoordinateValidator.isValidDMSFormatWithCustomValidation("34°12'34\"N, 118°14'37\"W"));
        assertTrue(GeoCoordinateValidator.isValidDMSFormatWithCustomValidation("34°12'34\"s, 118°14'37\"e"));
        assertTrue(GeoCoordinateValidator.isValidDMSFormatWithCustomValidation("45°30'N, 123°45'23.2\"W"));
        assertTrue(GeoCoordinateValidator.isValidDMSFormatWithCustomValidation("45°30'23.2\"N, 123°45'W"));
        assertTrue(GeoCoordinateValidator.isValidDMSFormatWithCustomValidation("45°30'N, 123°45'W"));
    }

    @Test
    public void givenInvalidDMSCoordinates_whenValidatingWithCustomValidation_thenReturnsFalse() {
        assertFalse(GeoCoordinateValidator.isValidDMSFormatWithCustomValidation("invalid"));
        assertFalse(GeoCoordinateValidator.isValidDMSFormatWithCustomValidation("91°12'34\"N, 118°14'37\"W"));
        assertFalse(GeoCoordinateValidator.isValidDMSFormatWithCustomValidation("34°60'34\"N, 118°14'37\"W"));
        assertFalse(GeoCoordinateValidator.isValidDMSFormatWithCustomValidation("34°12'60\"N, 118°14'37\"W"));
        assertFalse(GeoCoordinateValidator.isValidDMSFormatWithCustomValidation("34°12'34\"N, 181°14'37\"W"));
    }
}