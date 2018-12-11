package com.baeldung.batch.understanding.exception;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

@Named
public class MyItemProcessor implements ItemProcessor {
    @Override
    public Object processItem(Object t) {
        if (((MyInputRecord) t).getId() == 6) {
            throw new NullPointerException();
        }
        return new MyOutputRecord(((MyInputRecord) t).getId());
    }
}
