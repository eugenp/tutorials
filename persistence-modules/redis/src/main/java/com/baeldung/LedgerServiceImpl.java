package com.baeldung;

import java.util.Arrays;
import java.util.List;

/**
 * Created by johnson on 3/9/17.
 */
public class LedgerServiceImpl implements LedgerServiceInterface {

    String[] returnArray = {"entry1","entry2","entry3"};

    @Override
    public List<String> getEntries(int count) {
        return Arrays.asList(returnArray);
    }
}
