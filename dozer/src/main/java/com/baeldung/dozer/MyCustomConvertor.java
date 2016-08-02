package com.baeldung.dozer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

public class MyCustomConvertor implements CustomConverter {

    @Override
    public Object convert(Object dest, Object source, Class<?> arg2, Class<?> arg3) {
        if (source == null) {
            return null;
        }
        if (source instanceof Personne3) {
            Personne3 person = (Personne3) source;
            Date date = new Date(person.getDtob());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String isoDate = format.format(date);
            return new Person3(person.getName(), isoDate);

        } else if (source instanceof Person3) {
            Person3 person = (Person3) source;
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date = null;
            try {
                date = format.parse(person.getDtob());

            } catch (ParseException e) {
                throw new MappingException("Converter MyCustomConvertor " + "used incorrectly:" + e.getMessage());
            }
            long timestamp = date.getTime();
            return new Personne3(person.getName(), timestamp);

        } else {
            throw new MappingException("Converter MyCustomConvertor " + "used incorrectly. Arguments passed in were:" + dest + " and " + source);

        }
    }

}
