package com.baeldung.hibernate.types;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.time.YearMonth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.vladmihalcea.hibernate.type.basic.YearMonthIntegerType;

@Entity(name = "Song")
@Table(name = "song")
@TypeDef(
    typeClass = YearMonthIntegerType.class,
    defaultForType = YearMonth.class
)
public class Song extends BaseEntity {

    private Long length = 0L;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Artist artist;

    @Column(
        name = "recorded_on",
        columnDefinition = "mediumint"
    )
    private YearMonth recordedOn = YearMonth.now();

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Artist getArtist() {
        return artist;
    }
 
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public YearMonth getRecordedOn() {
        return recordedOn;
    }

    public void setRecordedOn(YearMonth recordedOn) {
        this.recordedOn = recordedOn;
    }
}