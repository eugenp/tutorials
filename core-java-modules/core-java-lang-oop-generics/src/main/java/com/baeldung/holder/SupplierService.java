package com.baeldung.holder;

public class SupplierService {
    public void getSupplierByZipCode(String zip, Holder<Boolean> resultHolder) {
        if (zip.startsWith("9")) {
            resultHolder.value = true;
        } else {
            resultHolder.value = false;
        }
    }
}
