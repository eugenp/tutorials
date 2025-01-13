package com.baeldung.hibernate.customtypes;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.time.LocalDate;

public class LocalDateStringType extends AbstractSingleColumnStandardBasicType<LocalDate> {

    public static final LocalDateStringType INSTANCE = new LocalDateStringType();

    public LocalDateStringType() {
        super(VarcharJdbcType.INSTANCE, LocalDateStringJavaDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "LocalDateString";
    }

    public LocalDate stringToObject(String xml) {
        return fromString(xml);
    }

    public String objectToSQLString(LocalDate value, Dialect dialect) {
        return '\'' + LocalDateStringJavaDescriptor.INSTANCE.toString(value) + '\'';
    }

}
