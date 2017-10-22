package com.baeldung.bizzkit;

public class BusinessService {

    public boolean showBusiness(long id, String name) {
        String format = String.format("Business ID => %s\nBusiness Name => %s", id, name);
        System.out.println(format);
        return true;
    }

}
