package com.baeldung.batch.understanding.exception;

import javax.batch.api.chunk.listener.RetryProcessListener;
import javax.inject.Named;

@Named
public class MyRetryProcessorListener implements RetryProcessListener {
    @Override
    public void onRetryProcessException(Object item, Exception ex) throws Exception {
    }
}
