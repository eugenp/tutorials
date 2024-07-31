package com.baeldung.skippingfirstelement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class TestableSkipFirstElement extends SkipFirstElementExample implements TestableSkip {


    private List<String> processedList = new ArrayList<>();
    private List<Entry<String, String>> processedEntryList = new ArrayList<>();

    @Override
    public void process(String string) {
        processedList.add(string);
    }

    @Override
    public void process(Entry<String, String> stringEntry) {
        processedEntryList.add(stringEntry);
    }

    @Override
    public void reset() {
        processedList.clear();
        processedEntryList.clear();
    }

    @Override
    public List<?> getResult() {
        if (!processedList.isEmpty())
            return processedList;
        return processedEntryList;
    }


}
