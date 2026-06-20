package com.baeldung.cn1tutorial.model;

/**
 * Normalized place information attached to an activity.
 */
public record PlaceInfo(
        String placeId,
        String name,
        String address,
        double latitude,
        double longitude
) {
    /**
     * Returns whether a meaningful address line is available.
     *
     * @return {@code true} when the address is not blank
     */
    public boolean hasAddress() {
        return address != null && address.trim().length() > 0;
    }

    /**
     * Builds an OpenStreetMap URL suitable for external navigation.
     *
     * @return browser-friendly OSM link
     */
    public String openStreetMapUrl() {
        return "https://www.openstreetmap.org/?mlat=" + latitude + "&mlon=" + longitude + "#map=16/" + latitude + "/" + longitude;
    }
}
