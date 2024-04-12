package com.baeldung.batch.understanding;

import javax.batch.api.chunk.listener.SkipReadListener;
import javax.inject.Named;

@Named
public class ChunkExceptionSkipReadListener implements SkipReadListener {
    @Override
    public void onSkipReadItem(Exception e) throws Exception {
    }
}