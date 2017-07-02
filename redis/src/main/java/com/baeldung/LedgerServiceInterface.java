package com.baeldung;

import java.util.List;

/**
 * Created by johnson on 3/9/17.
 */
interface LedgerServiceInterface {
    List<String> getEntries(int count);
}
