package com.gateway.repository.rowmapper;

import io.r2dbc.spi.Row;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import tech.jhipster.service.ColumnConverterReactive;

/**
 * This service provides helper function dealing with the low level {@link Row} and Spring's {@link R2dbcCustomConversions}, so type conversions can be applied.
 */
@Component
public class ColumnConverter implements ColumnConverterReactive {

    private final ConversionService conversionService;
    private final R2dbcCustomConversions conversions;

    public ColumnConverter(R2dbcCustomConversions conversions, R2dbcConverter r2dbcConverter) {
        this.conversionService = r2dbcConverter.getConversionService();
        this.conversions = conversions;
    }

    /**
     * Converts the value to the target class with the help of the {@link ConversionService}.
     * @param value to convert.
     * @param target class.
     * @param <T> the parameter for the intended type.
     * @return the value which can be constructed from the input.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(@Nullable Object value, @Nullable Class<T> target) {
        if (value == null || target == null || ClassUtils.isAssignableValue(target, value)) {
            return (T) value;
        }

        if (conversions.hasCustomReadTarget(value.getClass(), target)) {
            return conversionService.convert(value, target);
        }

        if (Enum.class.isAssignableFrom(target)) {
            return (T) Enum.valueOf((Class<Enum>) target, value.toString());
        }

        return conversionService.convert(value, target);
    }

    /**
     * Convert a value from the {@link Row} to a type - throws an exception, if it's impossible.
     * @param row which contains the column values.
     * @param target class.
     * @param columnName the name of the column which to convert.
     * @param <T> the parameter for the intended type.
     * @return the value which can be constructed from the input.
     */
    public <T> T fromRow(Row row, String columnName, Class<T> target) {
        try {
            // try, directly the driver
            return row.get(columnName, target);
        } catch (Exception e) {
            Object obj = row.get(columnName);
            return convert(obj, target);
        }
    }
}
