package com.baeldung.cn1tutorial.testsupport;

import com.baeldung.cn1tutorial.model.PlaceSuggestion;
import com.baeldung.cn1tutorial.service.AppContext;
import com.baeldung.cn1tutorial.service.NetworkSupport;
import com.baeldung.cn1tutorial.service.PlaceSearchService;
import com.codename1.ui.CN;
import com.codename1.util.FailureCallback;
import com.codename1.util.SuccessCallback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fake place-search service used by UI tests.
 */
public class FakePlaceSearchService implements PlaceSearchService {
    /**
     * Supported test modes.
     */
    public enum Mode {
        SUCCESS,
        EMPTY,
        TRANSPORT_FAILURE,
        SERVER_FAILURE
    }

    private final List<PlaceSuggestion> suggestions = new ArrayList<PlaceSuggestion>();
    private Mode mode = Mode.SUCCESS;
    private String lastQuery = "";
    private int serverCode = 503;
    private String serverMessage = "Service Unavailable";

    /**
     * Changes the fake behavior mode.
     *
     * @param mode new fake mode
     */
    public void setMode(Mode mode) {
        this.mode = mode == null ? Mode.SUCCESS : mode;
    }

    /**
     * Replaces the suggestions returned in {@link Mode#SUCCESS}.
     *
     * @param suggestions suggestions to return
     */
    public void setSuggestions(List<PlaceSuggestion> suggestions) {
        this.suggestions.clear();
        if (suggestions != null) {
            this.suggestions.addAll(suggestions);
        }
    }

    /**
     * Configures the HTTP code and message used in {@link Mode#SERVER_FAILURE}.
     *
     * @param serverCode fake server code
     * @param serverMessage fake server message
     */
    public void setServerFailure(int serverCode, String serverMessage) {
        this.serverCode = serverCode;
        this.serverMessage = serverMessage;
    }

    /**
     * @return last query received by the fake service
     */
    public String getLastQuery() {
        return lastQuery;
    }

    /**
     * Executes the fake request asynchronously on the EDT, mirroring how real CN1 callbacks are
     * typically observed by the forms.
     */
    @Override
    public SearchHandle search(
            AppContext context,
            String query,
            SuccessCallback<List<PlaceSuggestion>> onSuccess,
            FailureCallback<List<PlaceSuggestion>> onFailure
    ) {
        Handle handle = new Handle();
        lastQuery = query == null ? "" : query;
        CN.callSerially(() -> {
            if (handle.cancelled) {
                return;
            }
            switch (mode) {
                case EMPTY:
                    if (onSuccess != null) {
                        onSuccess.onSucess(new ArrayList<PlaceSuggestion>());
                    }
                    break;
                case TRANSPORT_FAILURE:
                    IOException transportError = new IOException("Simulated offline mode");
                    NetworkSupport.showTransportFailure(context, "https://test.local/geoapify", transportError);
                    if (onFailure != null) {
                        onFailure.onError(handle, transportError, -1, transportError.getMessage());
                    }
                    break;
                case SERVER_FAILURE:
                    NetworkSupport.showServerError(context, "https://test.local/geoapify", serverCode, serverMessage, null);
                    if (onFailure != null) {
                        onFailure.onError(handle, null, serverCode, serverMessage);
                    }
                    break;
                case SUCCESS:
                default:
                    if (onSuccess != null) {
                        onSuccess.onSucess(new ArrayList<PlaceSuggestion>(suggestions));
                    }
                    break;
            }
        });
        return handle;
    }

    /**
     * Cancellation handle for the fake request.
     */
    private static final class Handle implements SearchHandle {
        private boolean cancelled;

        /**
         * Marks the fake request as cancelled.
         */
        @Override
        public void cancel() {
            cancelled = true;
        }
    }
}
