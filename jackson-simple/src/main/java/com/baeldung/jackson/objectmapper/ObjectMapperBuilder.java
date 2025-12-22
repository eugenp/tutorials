package com.baeldung.jackson.objectmapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

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
        ObjectMapper objectMapper = JsonMapper.builder()
            .configure(SerializationFeature.INDENT_OUTPUT, this.enableIndentation)
            .defaultDateFormat(this.dateFormat)
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, this.preserveOrder)
            .build();

        return objectMapper;
    }

}