package com.baeldung.hibernate.customtypes;

import org.hibernate.type.LocalDateType;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.hibernate.type.descriptor.java.MutabilityPlan;

import java.time.LocalDate;

public class LocalDateStringJavaDescriptor extends AbstractTypeDescriptor<LocalDate> {

    public static final LocalDateStringJavaDescriptor INSTANCE = new LocalDateStringJavaDescriptor();

    public LocalDateStringJavaDescriptor() {
        super(LocalDate.class, ImmutableMutabilityPlan.INSTANCE);
    }

    @Override
    public String toString(LocalDate value) {
        return LocalDateType.FORMATTER.format(value);
    }

    @Override
    public LocalDate fromString(String string) {
        return LocalDate.from(LocalDateType.FORMATTER.parse(string));
    }

    @Override
    public <X> X unwrap(LocalDate value, Class<X> type, WrapperOptions options) {

        if (value == null)
            return null;

        if (String.class.isAssignableFrom(type))
            return (X) LocalDateType.FORMATTER.format(value);

        throw unknownUnwrap(type);
    }

    @Override
    public <X> LocalDate wrap(X value, WrapperOptions options) {
        if (value == null)
            return null;

        if(String.class.isInstance(value))
            return LocalDate.from(LocalDateType.FORMATTER.parse((CharSequence) value));

        throw unknownWrap(value.getClass());
    }
}
