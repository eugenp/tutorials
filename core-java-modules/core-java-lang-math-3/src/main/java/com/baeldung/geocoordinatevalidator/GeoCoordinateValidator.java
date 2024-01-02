package com.baeldung.geocoordinatevalidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeoCoordinateValidator {

    public static final String DD_COORDINATE_REGEX = "^(-?\\d+\\.\\d+)(?:\\s*,\\s?)?(-?\\d+\\.\\d+)$";

    public static final String DMS_COORDINATE_REGEX = "^(\\d{1,3})°(\\d{1,2})'(\\d{1,2}(\\.\\d+)?)?\"?([NSns])(?:\\s*,\\s?)?(\\d{1,3})°(\\d{1,2})'(\\d{1,2}(\\.\\d+)?)?\"?([WEwe])$";

    public static final String MGRS_COORDINATE_REGEX = "^\\d{1,2}[^IO]{3}(\\d{10}|\\d{8}|\\d{6}|\\d{4}|\\d{2})$";

    public boolean isValidDDFormatWithRegex(String coordinateString) {
        Pattern pattern = Pattern.compile(DD_COORDINATE_REGEX);
        Matcher matcher = pattern.matcher(coordinateString);
        return matcher.matches();
    }

    public boolean isValidDMSFormatWithRegex(String coordinateString) {
        Pattern pattern = Pattern.compile(DMS_COORDINATE_REGEX);
        Matcher matcher = pattern.matcher(coordinateString);
        return matcher.matches();
    }

    public boolean isValidMGRSFormatWithRegex(String coordinateString) {
        Pattern pattern = Pattern.compile(MGRS_COORDINATE_REGEX);
        Matcher matcher = pattern.matcher(coordinateString);
        return matcher.matches();
    }

    public boolean isValidDDFormatWithCustom(String coordinateString) {
        try {
            String[] parts = coordinateString.split(",");
            if (parts.length != 2) {
                return false;
            }
            double latitude = Double.parseDouble(parts[0].trim());
            double longitude = Double.parseDouble(parts[1].trim());
            return !(latitude < -90) && !(latitude > 90) && !(longitude < -180) && !(longitude > 180);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidDMSFormatWithCustom(String coordinateString) {
        try {
            String[] dmsParts = coordinateString.split("[°',]");
            if (dmsParts.length > 6) {
                return false;
            }
            int degreesLatitude = Integer.parseInt(dmsParts[0].trim());
            int minutesLatitude = Integer.parseInt(dmsParts[1].trim());
            String[] secondParts = dmsParts[2].split("\"");
            double secondsLatitude = secondParts.length > 1 ? Double.parseDouble(secondParts[0].trim()) : 0.0;
            String hemisphereLatitude = secondParts.length > 1 ? secondParts[1] : dmsParts[2];

            int degreesLongitude = Integer.parseInt(dmsParts[3].trim());
            int minutesLongitude = Integer.parseInt(dmsParts[4].trim());
            secondParts = dmsParts[5].split("\"");
            double secondsLongitude = secondParts.length > 1 ? Double.parseDouble(secondParts[0].trim()) : 0.0;
            String hemisphereLongitude = secondParts.length > 1 ? secondParts[1] : dmsParts[5];

            if (degreesLatitude < 0 || degreesLatitude > 90 || minutesLatitude < 0 || minutesLatitude >= 60 || secondsLatitude < 0 || secondsLatitude >= 60 ||
                (!hemisphereLatitude.equalsIgnoreCase("N") && !hemisphereLatitude.equalsIgnoreCase("S"))) {
                return false;
            }
            return degreesLongitude >= 0 && degreesLongitude <= 180 && minutesLongitude >= 0 && minutesLongitude < 60 && !(secondsLongitude < 0) &&
                !(secondsLongitude >= 60) && (hemisphereLongitude.equalsIgnoreCase("E") || hemisphereLongitude.equalsIgnoreCase("W"));
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
