package com.baeldung.jackson.objectmapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperBuilder {
    private boolean enableIndentation;
    private boolean preserveOrder;
    private DateFormat dateFormat;

    public ObjectMapperBuilder enableIndentation() {
        this.enableIndentation = true;
        return this;
    }

    public ObjectMapperBuilder dateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm a z", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Kolkata")));
        this.dateFormat = simpleDateFormat;
        return this;
    }

    public ObjectMapperBuilder preserveOrder(boolean order) {
        this.preserveOrder = order;
        return this;
    }

    public ObjectMapper build() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, this.enableIndentation);
        objectMapper.setDateFormat(this.dateFormat);
        if (this.preserveOrder) {
            objectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        }

        return objectMapper;
    }

}