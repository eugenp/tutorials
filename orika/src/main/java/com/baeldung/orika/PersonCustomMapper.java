package com.baeldung.orika;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class PersonCustomMapper extends CustomMapper<Personne3, Person3> {

    @Override
    public void mapAtoB(Personne3 a, Person3 b, MappingContext context) {
        Date date = new Date(a.getDtob());
        DateFormat format = new SimpleDateFormat(
          "yyyy-MM-dd'T'HH:mm:ss'Z'");
        String isoDate = format.format(date);
        b.setDtob(isoDate);
    }

    @Override
    public void mapBtoA(Person3 b, Personne3 a, MappingContext context) {
        DateFormat format = new SimpleDateFormat(
          "yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;
        try {
            date = format.parse(b.getDtob());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timestamp = date.getTime();
        a.setDtob(timestamp);
    }
}
