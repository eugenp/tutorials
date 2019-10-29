package com.baeldung.batch.understanding.exception;

import javax.batch.api.chunk.listener.SkipWriteListener;
import javax.inject.Named;
import java.util.List;

@Named
public class MySkipWriteListener implements SkipWriteListener {
    @Override
    public void onSkipWriteItem(List list, Exception e) throws Exception {
        list.remove(new MyOutputRecord(2));
    }
}
