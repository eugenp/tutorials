package com.baeldung.utility;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyDateConverter implements Converter {

    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public boolean canConvert(Class clazz) {
        return Date.class.isAssignableFrom(clazz);
    }

    @Override
    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext arg2) {
        Date date = (Date) value;
        writer.setValue(formatter.format(date));
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext arg1) {
        GregorianCalendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(formatter.parse(reader.getValue()));
        } catch (ParseException e) {
            throw new ConversionException(e.getMessage(), e);
        }
        return calendar;
    }
}
