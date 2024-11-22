package com.baeldung.postgres.datetime;

import jakarta.persistence.*;

import java.time.*;
import java.util.Date;

@Entity
public class DateTimeValues {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Date date;
    private LocalDate localDate;
    private LocalDateTime localDateTime;
    private Instant instant;
    private ZonedDateTime zonedDateTime;
    private LocalTime localTime;
    private OffsetDateTime offsetDateTime;
    private java.sql.Date sqlDate;

    @Column(columnDefinition = "date")
    private Instant instantAsDate;

    private String zoneId;

    public DateTimeValues() {
        Clock clock = Clock.fixed(Instant.parse("2024-08-01T14:15:00Z"), ZoneId.of("UTC"));

        this.date = new Date(clock.millis());
        this.localDate = LocalDate.now(clock);
        this.localDateTime = LocalDateTime.now(clock);
        this.zonedDateTime = ZonedDateTime.now(clock);
        this.instant = Instant.now(clock);
        this.localTime = LocalTime.now(clock);
        this.offsetDateTime = OffsetDateTime.now(clock);
        this.instantAsDate = Instant.now(clock);
        this.sqlDate = java.sql.Date.valueOf(LocalDate.now(clock));

        this.zoneId = ZoneId.systemDefault().getId();
    }

    public Integer getId() {
        return id;
    }

    public Instant getInstantAsDate() {
        return instantAsDate;
    }

}
