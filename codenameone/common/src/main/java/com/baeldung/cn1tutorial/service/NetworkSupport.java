package com.baeldung.cn1tutorial.service;

import com.baeldung.cn1tutorial.util.ThrowableUtil;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.CN;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import java.util.Date;

/**
 * Shared helpers for network failures, retries and diagnostics.
 * <p>
 * Putting this logic in one place avoids duplicating error dialogs across forms and keeps the
 * network tutorial concerns separate from individual feature screens.
 */
public final class NetworkSupport {
    private static final int[] RETRY_DELAYS_MILLIS = {1000, 2000, 4000, 8000};
    private static final int CLIPBOARD_TOAST_TIMEOUT = 5000;
    private static final RetryScheduler DEFAULT_RETRY_SCHEDULER = new RetryScheduler() {
        @Override
        public void schedule(int delayMillis, Runnable retryAction) {
            CN.setTimeout(delayMillis, retryAction);
        }
    };
    private static RetryScheduler retryScheduler = DEFAULT_RETRY_SCHEDULER;
    private static final UiDelegate DEFAULT_UI_DELEGATE = new UiDelegate() {
        @Override
        public void showUnexpectedNetworkError(String message) {
            ToastBar.showErrorMessage(message);
        }

        @Override
        public void showTransportRetry(String message) {
            ToastBar.showInfoMessage(message);
        }

        @Override
        public boolean showTransportFailure(String title, String body, String copyText, String okText) {
            Command share = new Command(copyText);
            Command ok = new Command(okText);
            return Dialog.show(title, body, share, ok) == share;
        }

        @Override
        public boolean showServerError(String title, String body, String copyText, String okText) {
            Command share = new Command(copyText);
            Command ok = new Command(okText);
            return Dialog.show(title, body, share, ok) == share;
        }
    };
    private static UiDelegate uiDelegate = DEFAULT_UI_DELEGATE;

    /**
     * Utility class; do not instantiate.
     */
    private NetworkSupport() {
    }

    /**
     * Handles unexpected framework-level network errors.
     *
     * @param context runtime context for localization
     * @param event network error event raised by CN1
     */
    public static void handleUnexpectedNetworkError(AppContext context, NetworkEvent event) {
        if (event == null || event.isConsumed()) {
            return;
        }
        event.consume();
        uiDelegate.showUnexpectedNetworkError(context == null ? "Unexpected network error" : context.text("network.unexpected.error"));
    }

    /**
     * Shows the transient retry message for a failed request.
     *
     * @param context runtime context for localization
     * @param attempt zero-based retry attempt
     */
    public static void showTransportRetry(AppContext context, int attempt) {
        String message = context.format("network.retry.message", Integer.valueOf(attempt + 1));
        uiDelegate.showTransportRetry(message);
    }

    /**
     * Shows a server-side error dialog and offers to copy diagnostics.
     *
     * @param context runtime context for localization
     * @param url request URL
     * @param code HTTP status code
     * @param message server message
     * @param throwable optional exception
     */
    public static void showServerError(AppContext context, String url, int code, String message, Throwable throwable) {
        String title = context.text("server.error.title");
        String body = context.format("server.error.message", Integer.valueOf(code), message == null ? "" : message);
        if (uiDelegate.showServerError(title, body, context.text("copy.details"), context.text("ok"))) {
            copyDiagnostics(context, buildNetworkDiagnostic(url, code, message, throwable));
        }
    }

    /**
     * Shows a transport failure dialog and offers to copy diagnostics.
     *
     * @param context runtime context for localization
     * @param url request URL
     * @param throwable transport-layer exception
     */
    public static void showTransportFailure(AppContext context, String url, Throwable throwable) {
        String title = context.text("network.transport.error.title");
        String body = context.text("network.transport.error.message");
        if (uiDelegate.showTransportFailure(title, body, context.text("copy.details"), context.text("ok"))) {
            copyDiagnostics(context, buildNetworkDiagnostic(url, -1, body, throwable));
        }
    }

    /**
     * Returns the backoff delay for one retry attempt.
     *
     * @param attempt zero-based retry attempt
     * @return delay in milliseconds
     */
    public static int retryDelayMillis(int attempt) {
        if (attempt < 0) {
            return RETRY_DELAYS_MILLIS[0];
        }
        if (attempt >= RETRY_DELAYS_MILLIS.length) {
            return RETRY_DELAYS_MILLIS[RETRY_DELAYS_MILLIS.length - 1];
        }
        return RETRY_DELAYS_MILLIS[attempt];
    }

    /**
     * Schedules a retry using the currently configured scheduler.
     *
     * @param attempt zero-based retry attempt
     * @param retryAction action to run later
     */
    public static void scheduleRetry(int attempt, Runnable retryAction) {
        retryScheduler.schedule(retryDelayMillis(attempt), retryAction);
    }

    /**
     * Replaces the retry scheduler, mainly for tests.
     *
     * @param scheduler replacement scheduler
     */
    public static void setRetryScheduler(RetryScheduler scheduler) {
        retryScheduler = scheduler == null ? DEFAULT_RETRY_SCHEDULER : scheduler;
    }

    /**
     * Restores the default retry scheduler.
     */
    public static void resetRetryScheduler() {
        retryScheduler = DEFAULT_RETRY_SCHEDULER;
    }

    /**
     * Replaces the UI delegate, mainly for tests.
     *
     * @param delegate replacement UI delegate
     */
    public static void setUiDelegate(UiDelegate delegate) {
        uiDelegate = delegate == null ? DEFAULT_UI_DELEGATE : delegate;
    }

    /**
     * Restores the default UI delegate.
     */
    public static void resetUiDelegate() {
        uiDelegate = DEFAULT_UI_DELEGATE;
    }

    /**
     * Copies diagnostics to the clipboard and shows a toast confirmation.
     *
     * @param context runtime context for localization
     * @param body diagnostic text
     */
    public static void copyDiagnostics(AppContext context, String body) {
        Log.p(body);
        Display.getInstance().copyToClipboard(body);
        ToastBar.showMessage(
                context == null ? "Text copied to clipboard." : context.text("clipboard.copied"),
                FontImage.MATERIAL_CONTENT_COPY,
                CLIPBOARD_TOAST_TIMEOUT
        );
    }

    /**
     * Builds a textual diagnostic block for clipboard export.
     *
     * @param url request URL
     * @param code HTTP status code, or {@code -1}
     * @param message human-readable message
     * @param throwable optional exception
     * @return diagnostic text
     */
    public static String buildNetworkDiagnostic(String url, int code, String message, Throwable throwable) {
        StringBuilder builder = new StringBuilder();
        builder.append("Timestamp: ").append(new Date()).append('\n');
        builder.append("URL: ").append(url).append('\n');
        if (code >= 0) {
            builder.append("HTTP code: ").append(code).append('\n');
        }
        if (message != null && message.length() > 0) {
            builder.append("Message: ").append(message).append('\n');
        }
        if (throwable != null) {
            builder.append('\n').append(ThrowableUtil.stackTraceToString(throwable));
        }
        return builder.toString();
    }

    /**
     * Base request that adds retry/backoff/error-dialog logic on top of a plain CN1
     * {@link ConnectionRequest}.
     */
    public static abstract class RetryingJsonRequest extends ConnectionRequest {
        private final AppContext context;
        private final String requestUrl;
        private final int maxRetries;
        private int attempt;
        private boolean cancelled;

        /**
         * Configures a GET request with shared failure handling.
         *
         * @param context runtime context for localization and dialogs
         * @param requestUrl request URL
         * @param maxRetries maximum number of transport retries
         */
        protected RetryingJsonRequest(AppContext context, String requestUrl, int maxRetries) {
            this.context = context;
            this.requestUrl = requestUrl;
            this.maxRetries = maxRetries;
            setFailSilently(true);
            setPost(false);
            setReadResponseForErrors(true);
            setHttpMethod("GET");
            setUrl(requestUrl);
        }

        /**
         * Retries transport failures with exponential backoff, then escalates to the UI.
         */
        @Override
        protected void handleException(Exception err) {
            if (cancelled) {
                return;
            }
            Log.e(err);
            if (attempt < maxRetries) {
                NetworkSupport.showTransportRetry(context, attempt);
                final int retryAttempt = attempt;
                attempt++;
                NetworkSupport.scheduleRetry(retryAttempt, () -> {
                    if (!cancelled) {
                        resubmit();
                    }
                });
            } else {
                NetworkSupport.showTransportFailure(context, requestUrl, err);
                onTransportFailure(err);
            }
        }

        /**
         * Escalates HTTP error codes directly to the UI.
         */
        @Override
        protected void handleErrorResponseCode(int code, String message) {
            if (cancelled) {
                return;
            }
            NetworkSupport.showServerError(context, requestUrl, code, message, null);
            onServerFailure(code, message);
        }

        /**
         * Requeues the request on the CN1 networking thread pool.
         */
        protected void resubmit() {
            CN.addToQueue(this);
        }

        /**
         * Cancels the request and prevents any scheduled retry from resubmitting it.
         */
        public void cancelRequest() {
            cancelled = true;
            kill();
        }

        /**
         * @return whether the request has been cancelled
         */
        protected boolean isCancelled() {
            return cancelled;
        }

        /**
         * Handles the final transport-layer failure after retries are exhausted.
         *
         * @param err failure cause
         */
        protected abstract void onTransportFailure(Exception err);

        /**
         * Handles a server-side HTTP error.
         *
         * @param code HTTP status code
         * @param message response message
         */
        protected abstract void onServerFailure(int code, String message);
    }

    /**
     * Pluggable scheduler used to make retry timing testable.
     */
    public interface RetryScheduler {
        /**
         * Schedules a retry action.
         *
         * @param delayMillis delay in milliseconds
         * @param retryAction action to execute
         */
        void schedule(int delayMillis, Runnable retryAction);
    }

    /**
     * Pluggable UI surface used to keep dialogs/toasts testable.
     */
    public interface UiDelegate {
        /**
         * Shows a generic network error message.
         *
         * @param message localized message
         */
        void showUnexpectedNetworkError(String message);

        /**
         * Shows a transient retry message.
         *
         * @param message localized message
         */
        void showTransportRetry(String message);

        /**
         * Shows the transport failure dialog.
         *
         * @param title dialog title
         * @param body dialog body
         * @param copyText copy button label
         * @param okText ok button label
         * @return {@code true} when the user asked to copy diagnostics
         */
        boolean showTransportFailure(String title, String body, String copyText, String okText);

        /**
         * Shows the server error dialog.
         *
         * @param title dialog title
         * @param body dialog body
         * @param copyText copy button label
         * @param okText ok button label
         * @return {@code true} when the user asked to copy diagnostics
         */
        boolean showServerError(String title, String body, String copyText, String okText);
    }
}
