package com.baeldung.batch.understanding.exception;

import javax.batch.api.chunk.listener.SkipProcessListener;
import javax.inject.Named;

@Named
public class MySkipProcessorListener implements SkipProcessListener {
    @Override
    public void onSkipProcessItem(Object t, Exception e) throws Exception {
    }
}
