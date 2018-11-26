package com.baeldung.batch.understanding.exception;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Named;
import java.io.Serializable;
import java.util.StringTokenizer;

@Named
public class MyItemReader extends AbstractItemReader {
    private StringTokenizer tokens;
    private MyInputRecord lastElement;
    private boolean alreadyFailed;

    @Override
    public void open(Serializable checkpoint) {
        tokens = new StringTokenizer("1,2,3,4,5,6,7,8,9,10", ",");
        if (checkpoint != null) {
            while (!Integer.valueOf(tokens.nextToken())
                .equals(((MyInputRecord) checkpoint).getId())) {
            }
        }
    }

    @Override
    public Object readItem() {
        if (tokens.hasMoreTokens()) {
            int token = Integer.valueOf(tokens.nextToken());
            if (token == 5 && !alreadyFailed) {
                alreadyFailed = true;
                throw new IllegalArgumentException("Could not read record");
            }
            lastElement = new MyInputRecord(token);
            return lastElement;
        }
        return null;
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return lastElement;
    }
}
