package com.baeldung.application.adapter;

import com.baeldung.domain.port.DataFeeder;

public class DB2DataFeeder implements DataFeeder {

    @Override
    public Object feedData() {
        return "Data from DB2 database";
    }

}
