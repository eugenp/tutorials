package com.baeldung.moshi;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;
import org.junit.Test;

public class ComplexAdapterUnitTest {
    @Test
    public void whenSerializing_thenCorrectJsonProduced() {
        Moshi moshi = new Moshi.Builder()
          .add(new JsonDateTimeAdapter())
          .build();
        JsonAdapter<ZonedDateTime> jsonAdapter = moshi.adapter(ZonedDateTime.class);

        String json = jsonAdapter.toJson(ZonedDateTime.now());
        System.out.println(json);
    }

    @Test
    public void whenDeserializing_thenCorrectJsonConsumed() throws IOException {
        Moshi moshi = new Moshi.Builder()
          .add(new JsonDateTimeAdapter())
          .build();
        JsonAdapter<ZonedDateTime> jsonAdapter = moshi.adapter(ZonedDateTime.class);

        String json = "{\"date\":\"2020-02-17\",\"time\":\"07:53:27.064\",\"timezone\":\"Europe/London\"}";
        ZonedDateTime now = jsonAdapter.fromJson(json);
        System.out.println(now);

    }

    public static class JsonDateTimeAdapter {
        @ToJson
        public JsonDateTime toJson(ZonedDateTime input) {
            String date = input.toLocalDate().toString();
            String time = input.toLocalTime().toString();
            String timezone = input.getZone().toString();
            return new JsonDateTime(date, time, timezone);
        }
        @FromJson
        public ZonedDateTime fromJson(JsonDateTime input) {
            LocalDate date = LocalDate.parse(input.getDate());
            LocalTime time = LocalTime.parse(input.getTime());
            ZoneId timezone = ZoneId.of(input.getTimezone());
            return ZonedDateTime.of(date, time, timezone);
        }
    }
    public static class JsonDateTime {
        private String date;
        private String time;
        private String timezone;

        public JsonDateTime() {
        }

        public JsonDateTime(String date, String time, String timezone) {
            this.date = date;
            this.time = time;
            this.timezone = timezone;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }
    }
}
