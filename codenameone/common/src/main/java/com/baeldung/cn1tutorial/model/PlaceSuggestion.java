package com.baeldung.cn1tutorial.model;

/**
 * Lightweight search result returned by the remote place API.
 * <p>
 * The search service returns suggestions first, then the selected suggestion is converted into the
 * richer {@link PlaceInfo} attached to an activity.
 */
public record PlaceSuggestion(
        String placeId,
        String primaryText,
        String secondaryText,
        double latitude,
        double longitude
) {
    /**
     * Converts a suggestion into the normalized place model used by the rest of the app.
     *
     * @return place information ready to be attached to an activity
     */
    public PlaceInfo toPlaceInfo() {
        StringBuilder address = new StringBuilder();
        if (primaryText != null) {
            address.append(primaryText);
        }
        if (secondaryText != null && secondaryText.trim().length() > 0) {
            if (address.length() > 0) {
                address.append(", ");
            }
            address.append(secondaryText);
        }
        return new PlaceInfo(placeId, primaryText, address.toString(), latitude, longitude);
    }
}
