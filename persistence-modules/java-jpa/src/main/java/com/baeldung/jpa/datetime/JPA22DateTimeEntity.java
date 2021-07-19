package com.baeldung.jpa.datetime;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Calendar;

@Entity
public class JPA22DateTimeEntity {

    @Id
    private Long id;

    //java.sql types
    private Time sqlTime;
    private Date sqlDate;
    private Timestamp sqlTimestamp;

    //java.util types
    @Temporal(TemporalType.TIME)
    private java.util.Date utilTime;

    @Temporal(TemporalType.DATE)
    private java.util.Date utilDate;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date utilTimestamp;

    //Calendar
    @Temporal(TemporalType.TIME)
    private Calendar calendarTime;

    @Temporal(TemporalType.DATE)
    private Calendar calendarDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar calendarTimestamp;

    // java.time types
    @Column(name = "local_time", columnDefinition = "TIME")
    private LocalTime localTime;

    @Column(name = "local_date", columnDefinition = "DATE")
    private LocalDate localDate;

    @Column(name = "local_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;

    @Column(name = "offset_time", columnDefinition = "TIME WITH TIME ZONE")
    private OffsetTime offsetTime;

    @Column(name = "offset_date_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime offsetDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Time getSqlTime() {
        return sqlTime;
    }

    public void setSqlTime(Time sqlTime) {
        this.sqlTime = sqlTime;
    }

    public Date getSqlDate() {
        return sqlDate;
    }

    public void setSqlDate(Date sqlDate) {
        this.sqlDate = sqlDate;
    }

    public Timestamp getSqlTimestamp() {
        return sqlTimestamp;
    }

    public void setSqlTimestamp(Timestamp sqlTimestamp) {
        this.sqlTimestamp = sqlTimestamp;
    }

    public java.util.Date getUtilTime() {
        return utilTime;
    }

    public void setUtilTime(java.util.Date utilTime) {
        this.utilTime = utilTime;
    }

    public java.util.Date getUtilDate() {
        return utilDate;
    }

    public void setUtilDate(java.util.Date utilDate) {
        this.utilDate = utilDate;
    }

    public java.util.Date getUtilTimestamp() {
        return utilTimestamp;
    }

    public void setUtilTimestamp(java.util.Date utilTimestamp) {
        this.utilTimestamp = utilTimestamp;
    }

    public Calendar getCalendarTime() {
        return calendarTime;
    }

    public void setCalendarTime(Calendar calendarTime) {
        this.calendarTime = calendarTime;
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

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public OffsetTime getOffsetTime() {
        return offsetTime;
    }

    public void setOffsetTime(OffsetTime offsetTime) {
        this.offsetTime = offsetTime;
    }

    public OffsetDateTime getOffsetDateTime() {
        return offsetDateTime;
    }

    public void setOffsetDateTime(OffsetDateTime offsetDateTime) {
        this.offsetDateTime = offsetDateTime;
    }
}
