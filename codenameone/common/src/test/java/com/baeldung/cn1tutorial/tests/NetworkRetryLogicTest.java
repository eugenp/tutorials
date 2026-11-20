package com.baeldung.cn1tutorial.tests;

import com.baeldung.cn1tutorial.service.AppContext;
import com.baeldung.cn1tutorial.service.NetworkSupport;
import com.baeldung.cn1tutorial.testsupport.FakePlaceSearchService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Verifies retry scheduling and failure escalation for transport errors.
 */
public class NetworkRetryLogicTest extends AbstractDailyRoutineUiTest {
    /**
     * Simulates repeated transport failures and checks the retry/backoff behavior.
     */
    @Override
    public boolean runTest() {
        List<Integer> delays = new ArrayList<Integer>();
        RecordingUiDelegate ui = new RecordingUiDelegate();
        NetworkSupport.setUiDelegate(ui);
        NetworkSupport.setRetryScheduler((delayMillis, retryAction) -> {
            delays.add(Integer.valueOf(delayMillis));
            retryAction.run();
        });
        ProbeRequest request = new ProbeRequest(this.context, "https://test.local/offline", 2);

        request.simulateTransportFailure(new IOException("offline-1"));
        request.simulateTransportFailure(new IOException("offline-2"));
        com.codename1.ui.CN.callSerially(() -> request.simulateTransportFailure(new IOException("offline-3")));
        waitFor(250);

        assertEqual(2, request.resubmitCount, "Exactly two retry submissions should occur before giving up");
        assertEqual(1, request.transportFailureCount, "The request should surface a transport failure after exhausting retries");
        assertEqual(2, delays.size(), "Two retry delays should be scheduled");
        assertEqual(1000, delays.get(0).intValue());
        assertEqual(2000, delays.get(1).intValue());
        assertEqual(2, ui.retryMessages.size(), "Two retry toast messages should be emitted");
        assertEqual(1, ui.transportFailures, "A transport failure dialog should be emitted after retries are exhausted");
        return true;
    }

    /**
     * Test request that exposes retry callbacks without performing real I/O.
     */
    private static final class ProbeRequest extends NetworkSupport.RetryingJsonRequest {
        private int resubmitCount;
        private int transportFailureCount;

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
         * Counts the final surfaced transport failure.
         */
        @Override
        protected void onTransportFailure(Exception err) {
            transportFailureCount++;
        }

        /**
         * Unused in this transport-failure-specific probe.
         */
        @Override
        protected void onServerFailure(int code, String message) {
        }

        /**
         * Manually triggers the transport failure path.
         *
         * @param err simulated failure
         */
        private void simulateTransportFailure(Exception err) {
            handleException(err);
        }
    }

    /**
     * Test double that records which UI callbacks were requested.
     */
    private static final class RecordingUiDelegate implements NetworkSupport.UiDelegate {
        private final List<String> retryMessages = new ArrayList<String>();
        private int transportFailures;

        /**
         * Ignored in this test.
         */
        @Override
        public void showUnexpectedNetworkError(String message) {
        }

        /**
         * Records retry toast messages.
         */
        @Override
        public void showTransportRetry(String message) {
            retryMessages.add(message);
        }

        /**
         * Records the transport failure dialog without copying diagnostics.
         */
        @Override
        public boolean showTransportFailure(String title, String body, String copyText, String okText) {
            transportFailures++;
            return false;
        }

        /**
         * Ignored in this test.
         */
        @Override
        public boolean showServerError(String title, String body, String copyText, String okText) {
            return false;
        }
    }
}
