package com.baeldung.holder;

public class SupplierService {
    public void getSupplierByZipCode(String zip, Holder<Boolean> resultHolder) {
        // Let's pretend we did some work here to get the supplier
        // And let's say all zip codes starting with "9" are valid, just for this example
        if (zip.startsWith("9")) {
            resultHolder.value = true;
        } else {
            resultHolder.value = false;
        }
    }
}
