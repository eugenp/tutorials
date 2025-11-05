package com.gateway.repository.rowmapper;

import com.gateway.domain.User;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link User}, with proper type conversions.
 */
@Service
public class UserRowMapper implements BiFunction<Row, String, User> {

    private final ColumnConverter converter;

    public UserRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link User} stored in the database.
     */
    @Override
    public User apply(Row row, String prefix) {
        User entity = new User();
        entity.setId(row.get(prefix + "_id", Long.class));
        entity.setLogin(converter.fromRow(row, prefix + "_login", String.class));
        entity.setPassword(converter.fromRow(row, prefix + "_password", String.class));
        entity.setFirstName(converter.fromRow(row, prefix + "_first_name", String.class));
        entity.setLastName(converter.fromRow(row, prefix + "_last_name", String.class));
        entity.setEmail(converter.fromRow(row, prefix + "_email", String.class));
        entity.setActivated(Boolean.TRUE.equals(converter.fromRow(row, prefix + "_activated", Boolean.class)));
        entity.setLangKey(converter.fromRow(row, prefix + "_lang_key", String.class));
        entity.setImageUrl(converter.fromRow(row, prefix + "_image_url", String.class));
        entity.setActivationKey(converter.fromRow(row, prefix + "_activation_key", String.class));
        entity.setResetKey(converter.fromRow(row, prefix + "_reset_key", String.class));
        entity.setResetDate(converter.fromRow(row, prefix + "_reset_date", Instant.class));
        return entity;
    }
}
