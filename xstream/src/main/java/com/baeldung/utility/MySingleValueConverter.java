package com.baeldung.utility;

import com.baeldung.pojo.Customer;
import com.thoughtworks.xstream.converters.SingleValueConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MySingleValueConverter implements SingleValueConverter {

    @Override
    public boolean canConvert(Class clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public Object fromString(String arg0) {
        return null;
    }

    @Override
    public String toString(Object obj) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = ((Customer) obj).getDob();
        return ((Customer) obj).getFirstName() + "," + ((Customer) obj).getLastName() + "," + formatter.format(date);
    }

}
