package com.baeldung.hibernate.customtypes;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.hibernate.dialect.Dialect;
import org.hibernate.tool.schema.extract.spi.ColumnTypeInformation;
import org.hibernate.type.BasicType;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;
import org.hibernate.type.spi.TypeConfiguration;

import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayTypeDescriptor;

public class LocalDateStringJavaDescriptor extends AbstractArrayTypeDescriptor<LocalDate> {

    public static final LocalDateStringJavaDescriptor INSTANCE = new LocalDateStringJavaDescriptor();

    public LocalDateStringJavaDescriptor() {
        super(LocalDate.class, ImmutableMutabilityPlan.INSTANCE);
    }

    @Override
    public String toString(LocalDate value) {
        return DateTimeFormatter.ISO_LOCAL_DATE.format(value);
    }

    @Override
    public LocalDate fromString(CharSequence string) {
        return LocalDate.from( DateTimeFormatter.ISO_LOCAL_DATE.parse(string));
    }

    @Override
    public <X> X unwrap(LocalDate value, Class<X> type, WrapperOptions options) {

        if (value == null)
            return null;

        if (String.class.isAssignableFrom(type))
            return (X) DateTimeFormatter.ISO_LOCAL_DATE.format(value);

        throw unknownUnwrap(type);
    }

    @Override
    public <X> LocalDate wrap(X value, WrapperOptions options) {
        if (value == null)
            return null;

        if(value instanceof String)
            return LocalDate.from( DateTimeFormatter.ISO_LOCAL_DATE.parse((CharSequence) value));

        throw unknownWrap(value.getClass());
    }

    @Override
    public BasicType resolveType(TypeConfiguration typeConfiguration, Dialect dialect, BasicType elementType, ColumnTypeInformation columnTypeInformation, JdbcTypeIndicators stdIndicators) {
        // TODO Auto-generated method stub
        return null;
    }
}
