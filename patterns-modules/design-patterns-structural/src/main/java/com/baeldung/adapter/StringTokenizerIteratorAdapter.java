package com.baeldung.adapter;

import java.util.Iterator;
import java.util.StringTokenizer;

public class StringTokenizerIteratorAdapter extends StringTokenizer implements Iterator<String> {

    public StringTokenizerIteratorAdapter(final String str, final String delim, final boolean returnDelims) {
        super(str, delim, returnDelims);
    }

    public StringTokenizerIteratorAdapter(final String str, final String delim) {
        super(str, delim);
    }

    public StringTokenizerIteratorAdapter(final String str) {
        super(str);
    }

    @Override
    public boolean hasNext() {
        return hasMoreTokens();
    }

    @Override
    public String next() {
        return nextToken();
    }
}
