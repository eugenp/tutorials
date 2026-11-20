package com.baeldung.cn1tutorial.service;

import com.baeldung.cn1tutorial.model.PlaceSuggestion;
import com.baeldung.cn1tutorial.util.AppConfig;
import com.baeldung.cn1tutorial.util.IOUtil;
import com.codename1.io.JSONParser;
import com.codename1.util.FailureCallback;
import com.codename1.util.SuccessCallback;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Geoapify-backed implementation of {@link PlaceSearchService}.
 * <p>
 * The service intentionally returns lightweight suggestion objects and leaves UI choices such as
 * selection, confirmation and map preview to the forms.
 */
public class GeoapifyPlaceSearchService implements PlaceSearchService {
    /**
     * Starts a Geoapify autocomplete request.
     */
    @Override
    public SearchHandle search(
            AppContext context,
            String query,
            SuccessCallback<List<PlaceSuggestion>> onSuccess,
            FailureCallback<List<PlaceSuggestion>> onFailure
    ) {
        SearchHandle handle = new SearchHandle();
        String requestUrl = buildAutocompleteUrl(context, query);
        SearchRequest request = new SearchRequest(context, requestUrl, onSuccess, onFailure, handle);
        handle.request = request;
        request.resubmit();
        return handle;
    }

    /**
     * Builds the Geoapify autocomplete URL according to the current language preference.
     *
     * @param context runtime context
     * @param query user-entered query
     * @return full request URL
     */
    private String buildAutocompleteUrl(AppContext context, String query) {
        StringBuilder builder = new StringBuilder("https://api.geoapify.com/v1/geocode/autocomplete?");
        builder.append("text=").append(com.codename1.io.Util.encodeUrl(query));
        builder.append("&format=geojson");
        builder.append("&limit=").append(AppConfig.GEOAPIFY_RESULT_LIMIT);
        builder.append("&lang=").append(context.getSettings().languageCode());
        builder.append("&apiKey=").append(AppConfig.geoapifyApiKey());
        return builder.toString();
    }

    /**
     * Concrete request object that plugs Geoapify parsing into the shared retry/error logic.
     */
    private static final class SearchRequest extends NetworkSupport.RetryingJsonRequest {
        private final SuccessCallback<List<PlaceSuggestion>> onSuccess;
        private final FailureCallback<List<PlaceSuggestion>> onFailure;
        private final SearchHandle handle;
        private String responseBody = "";

        /**
         * @param context runtime context
         * @param requestUrl request URL
         * @param onSuccess success callback
         * @param onFailure failure callback
         * @param handle exposed cancellation handle
         */
        private SearchRequest(
                AppContext context,
                String requestUrl,
                SuccessCallback<List<PlaceSuggestion>> onSuccess,
                FailureCallback<List<PlaceSuggestion>> onFailure,
                SearchHandle handle
        ) {
            super(context, requestUrl, 3);
            this.onSuccess = onSuccess;
            this.onFailure = onFailure;
            this.handle = handle;
        }

        /**
         * Reads the raw JSON response body.
         */
        @Override
        protected void readResponse(InputStream input) throws IOException {
            responseBody = IOUtil.readUtf8(input);
        }

        /**
         * Parses the response after the transport layer has succeeded.
         */
        @Override
        protected void postResponse() {
            if (isCancelled()) {
                return;
            }
            try {
                if (onSuccess != null) {
                    onSuccess.onSucess(parseSuggestions(responseBody));
                }
            } catch (IOException ex) {
                onTransportFailure(ex);
            }
        }

        /**
         * Adapts transport failures to the callback API expected by the form.
         */
        @Override
        protected void onTransportFailure(Exception err) {
            if (onFailure != null) {
                onFailure.onError(handle, err, -1, err == null ? "" : err.getMessage());
            }
        }

        /**
         * Adapts HTTP error responses to the callback API expected by the form.
         */
        @Override
        protected void onServerFailure(int code, String message) {
            if (onFailure != null) {
                onFailure.onError(handle, null, code, message);
            }
        }

        /**
         * Parses GeoJSON features into the app's suggestion model.
         *
         * @param json raw GeoJSON response
         * @return parsed suggestions
         * @throws IOException if the JSON is malformed
         */
        @SuppressWarnings("rawtypes")
        private List<PlaceSuggestion> parseSuggestions(String json) throws IOException {
            JSONParser parser = new JSONParser();
            Map<String, Object> root = parser.parseJSON(new StringReader(json));
            List<PlaceSuggestion> suggestions = new ArrayList<PlaceSuggestion>();
            Object featuresObject = root.get("features");
            if (!(featuresObject instanceof List)) {
                return suggestions;
            }
            List features = (List) featuresObject;
            for (Object featureObject : features) {
                if (!(featureObject instanceof Map)) {
                    continue;
                }
                Map feature = (Map) featureObject;
                Map properties = feature.get("properties") instanceof Map ? (Map) feature.get("properties") : null;
                Map geometry = feature.get("geometry") instanceof Map ? (Map) feature.get("geometry") : null;
                if (properties == null || geometry == null) {
                    continue;
                }
                List coordinates = geometry.get("coordinates") instanceof List ? (List) geometry.get("coordinates") : null;
                double lon = coordinates != null && coordinates.size() > 0 && coordinates.get(0) instanceof Number
                        ? ((Number) coordinates.get(0)).doubleValue() : 0d;
                double lat = coordinates != null && coordinates.size() > 1 && coordinates.get(1) instanceof Number
                        ? ((Number) coordinates.get(1)).doubleValue() : 0d;
                String placeId = stringValue(properties.get("place_id"));
                String line1 = stringValue(properties.get("address_line1"));
                if (line1.length() == 0) {
                    line1 = stringValue(properties.get("formatted"));
                }
                String line2 = stringValue(properties.get("address_line2"));
                suggestions.add(new PlaceSuggestion(placeId, line1, line2, lat, lon));
            }
            return suggestions;
        }

        /**
         * Normalizes possibly-null values into strings.
         *
         * @param value raw JSON field value
         * @return string form, never {@code null}
         */
        private String stringValue(Object value) {
            return value == null ? "" : String.valueOf(value);
        }
    }

    /**
     * Mutable request handle returned to the UI.
     */
    public static final class SearchHandle implements PlaceSearchService.SearchHandle {
        private SearchRequest request;

        /**
         * Cancels the current request, if any.
         */
        @Override
        public void cancel() {
            if (request != null) {
                request.cancelRequest();
            }
        }
    }
}
