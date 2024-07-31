package com.baeldung.restexpress.serialization;

import com.strategicgains.repoexpress.util.UuidConverter;
import com.thoughtworks.xstream.converters.SingleValueConverter;

import java.util.UUID;

/**
 * @author toddf
 * @since Feb 16, 2011
 */
public class XstreamUuidConverter
        implements SingleValueConverter {
    @SuppressWarnings("rawtypes")
    @Override
    public boolean canConvert(Class aClass) {
        return UUID.class.isAssignableFrom(aClass);
    }

    @Override
    public Object fromString(String value) {
        return UuidConverter.parse(value);
    }

    @Override
    public String toString(Object objectId) {
        return UuidConverter.format((UUID) objectId);
    }
}
