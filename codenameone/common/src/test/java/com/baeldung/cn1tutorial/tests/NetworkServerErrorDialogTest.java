package com.baeldung.cn1tutorial.tests;

import com.baeldung.cn1tutorial.service.AppContext;
import com.baeldung.cn1tutorial.service.NetworkSupport;

/**
 * Verifies server-side HTTP error handling without retries.
 */
public class NetworkServerErrorDialogTest extends AbstractDailyRoutineUiTest {
    /**
     * Simulates an HTTP server failure and checks diagnostics plus UI callbacks.
     */
    @Override
    public boolean runTest() {
        RecordingUiDelegate ui = new RecordingUiDelegate();
        NetworkSupport.setUiDelegate(ui);
        ProbeRequest request = new ProbeRequest(context, "https://test.local/server", 2);

        com.codename1.ui.CN.callSerially(() -> request.simulateServerFailure(503, "Service Unavailable"));
        waitFor(250);

        assertEqual(503, request.lastServerCode);
        assertEqual("Service Unavailable", request.lastServerMessage);
        assertEqual(0, request.resubmitCount, "Server failures should not retry automatically");
        assertEqual(1, ui.serverErrors, "A server-side dialog should be requested exactly once");
        String diagnostic = NetworkSupport.buildNetworkDiagnostic("https://test.local/server", 503, "Service Unavailable", null);
        assertTrue(diagnostic.indexOf("HTTP code: 503") >= 0, "Diagnostics should include the HTTP code");
        assertTrue(diagnostic.indexOf("URL: https://test.local/server") >= 0, "Diagnostics should include the request URL");
        return true;
    }

    /**
     * Test request that exposes server-failure callbacks without performing real I/O.
     */
    private static final class ProbeRequest extends NetworkSupport.RetryingJsonRequest {
        private int resubmitCount;
        private int lastServerCode = -1;
        private String lastServerMessage = "";

        /**
         * @param context runtime context
         * @param requestUrl synthetic request URL
         * @param maxRetries maximum retries to allow
         */
        private ProbeRequest(AppContext context, String requestUrl, int maxRetries) {
            super(context, requestUrl, maxRetries);
        }

        /**
         * Counts resubmissions instead of requeueing a real request.
         */
        @Override
        protected void resubmit() {
            resubmitCount++;
        }

        /**
         * Unused in this server-failure-specific probe.
         */
        @Override
        protected void onTransportFailure(Exception err) {
        }

        /**
         * Records the server error information.
         */
        @Override
        protected void onServerFailure(int code, String message) {
            lastServerCode = code;
            lastServerMessage = message;
        }

        /**
         * Manually triggers the HTTP error path.
         *
         * @param code HTTP status code
         * @param message response message
         */
        private void simulateServerFailure(int code, String message) {
            handleErrorResponseCode(code, message);
        }
    }

    /**
     * Test double that records which UI callbacks were requested.
     */
    private static final class RecordingUiDelegate implements NetworkSupport.UiDelegate {
        private int serverErrors;

        /**
         * Ignored in this test.
         */
        @Override
        public void showUnexpectedNetworkError(String message) {
        }

        /**
         * Ignored in this test.
         */
        @Override
        public void showTransportRetry(String message) {
        }

        /**
         * Ignored in this test.
         */
        @Override
        public boolean showTransportFailure(String title, String body, String shareText, String okText) {
            return false;
        }

        /**
         * Records the server error dialog without copying diagnostics.
         */
        @Override
        public boolean showServerError(String title, String body, String shareText, String okText) {
            serverErrors++;
            return false;
        }
    }
}
