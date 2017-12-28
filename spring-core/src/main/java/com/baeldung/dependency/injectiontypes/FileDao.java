package com.baeldung.dependency.injectiontypes;

class FileDao implements Dao {

    @Override
    public String save(String customerName) {
        // dummy method which just replays the customerName back as a response
        return customerName;
    }

}
