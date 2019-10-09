package com.baeldung.application.adapter;

import com.baeldung.domain.port.DataFeeder;

public class OracleDataFeeder implements DataFeeder {

    @Override
    public Object feedData() {
        return "Data from Oracle database";
    }

}
