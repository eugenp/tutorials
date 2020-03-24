package org.baeldung.batch.service.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v, DATE_TIME_FORMATTER);
    }

    public String marshal(LocalDateTime v) throws Exception {
        return DATE_TIME_FORMATTER.format(v);
    }
}