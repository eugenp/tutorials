package com.baeldung.cn1tutorial.service;

import com.baeldung.cn1tutorial.model.PlaceSuggestion;
import com.codename1.util.FailureCallback;
import com.codename1.util.SuccessCallback;
import java.util.List;

/**
 * Abstraction over the remote place search backend.
 * <p>
 * Keeping this behind an interface makes the UI testable without network access and keeps the
 * tutorial open to swapping providers.
 */
public interface PlaceSearchService {
    /**
     * Starts an asynchronous place lookup.
     *
     * @param context runtime context used for localization and error handling
     * @param query user-entered query string
     * @param onSuccess callback invoked with the suggestion list
     * @param onFailure callback invoked when the lookup fails
     * @return handle that can cancel the in-flight request
     */
    SearchHandle search(
            AppContext context,
            String query,
            SuccessCallback<List<PlaceSuggestion>> onSuccess,
            FailureCallback<List<PlaceSuggestion>> onFailure
    );

    /**
     * Handle returned for an in-flight search request.
     */
    interface SearchHandle {
        /**
         * Cancels the underlying request if it is still active.
         */
        void cancel();
    }
}
