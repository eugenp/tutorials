package com.baeldung.hibernate.pojo;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Calendar;

@Entity
public class TemporalValues {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private java.sql.Date sqlDate;

    @Basic
    private java.sql.Time sqlTime;

    @Basic
    private java.sql.Timestamp sqlTimestamp;

    @Basic
    @Temporal(TemporalType.DATE)
    private java.util.Date utilDate;

    @Basic
    @Temporal(TemporalType.TIME)
    private java.util.Date utilTime;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date utilTimestamp;

    @Basic
    @Temporal(TemporalType.DATE)
    private java.util.Calendar calendarDate;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar calendarTimestamp;

    @Basic
    private java.time.LocalDate localDate;

    @Basic
    private java.time.LocalTime localTimeField;

    @Basic
    private java.time.OffsetTime offsetTime;

    @Basic
    private java.time.Instant instant;

    @Basic
    private java.time.LocalDateTime localDateTime;

    @Basic
    private java.time.OffsetDateTime offsetDateTime;

    @Basic
    private java.time.ZonedDateTime zonedDateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getSqlDate() {
        return sqlDate;
    }

    public void setSqlDate(Date sqlDate) {
        this.sqlDate = sqlDate;
    }

    public Time getSqlTime() {
        return sqlTime;
    }

    public void setSqlTime(Time sqlTime) {
        this.sqlTime = sqlTime;
    }

    public Timestamp getSqlTimestamp() {
        return sqlTimestamp;
    }

    public void setSqlTimestamp(Timestamp sqlTimestamp) {
        this.sqlTimestamp = sqlTimestamp;
    }

    public java.util.Date getUtilDate() {
        return utilDate;
    }

    public void setUtilDate(java.util.Date utilDate) {
        this.utilDate = utilDate;
    }

    public java.util.Date getUtilTime() {
        return utilTime;
    }

    public void setUtilTime(java.util.Date utilTime) {
        this.utilTime = utilTime;
    }

    public java.util.Date getUtilTimestamp() {
        return utilTimestamp;
    }

    public void setUtilTimestamp(java.util.Date utilTimestamp) {
        this.utilTimestamp = utilTimestamp;
    }

    public Calendar getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(Calendar calendarDate) {
        this.calendarDate = calendarDate;
    }

    public Calendar getCalendarTimestamp() {
        return calendarTimestamp;
    }

    public void setCalendarTimestamp(Calendar calendarTimestamp) {
        this.calendarTimestamp = calendarTimestamp;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTimeField;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTimeField = localTime;
    }

    public OffsetTime getOffsetTime() {
        return offsetTime;
    }

    public void setOffsetTime(OffsetTime offsetTime) {
        this.offsetTime = offsetTime;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public OffsetDateTime getOffsetDateTime() {
        return offsetDateTime;
    }

    public void setOffsetDateTime(OffsetDateTime offsetDateTime) {
        this.offsetDateTime = offsetDateTime;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }
}
