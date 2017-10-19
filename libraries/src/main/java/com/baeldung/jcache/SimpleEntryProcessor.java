package com.baeldung.jcache;

import java.io.Serializable;

import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

public class SimpleEntryProcessor implements EntryProcessor<String, String, String>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5616476363722945132L;

    public String process(MutableEntry<String, String> entry, Object... args) throws EntryProcessorException {

        if (entry.exists()) {
            String current = entry.getValue();
            entry.setValue(current + " - modified");
            return current;
        }
        return null;
    }
}
