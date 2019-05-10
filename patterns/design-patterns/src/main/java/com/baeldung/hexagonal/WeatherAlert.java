package com.baeldung.hexagonal;

import java.util.Date;
import java.util.Objects;

public class WeatherAlert {

    public enum WeatherAlertType {
        RAIN, HAIL, HOT, COLD, TORNADO;
    }

    private WeatherAlertType weatherAlertType;

    private Date weatherAlertDate;

    public WeatherAlertType getWeatherAlertType() {
        return weatherAlertType;
    }

    public void setWeatherAlertType(WeatherAlertType weatherAlertType) {
        this.weatherAlertType = weatherAlertType;
    }

    public Date getWeatherAlertDate() {
        return weatherAlertDate;
    }

    public void setWeatherAlertDate(Date weatherAlertDate) {
        this.weatherAlertDate = weatherAlertDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WeatherAlert that = (WeatherAlert) o;
        return weatherAlertType == that.weatherAlertType &&
                Objects.equals(weatherAlertDate, that.weatherAlertDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weatherAlertType, weatherAlertDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WeatherAlert [");
        sb.append("weatherAlertType=").append(weatherAlertType);
        sb.append(", weatherAlertDate=").append(weatherAlertDate);
        sb.append(']');
        return sb.toString();
    }
}
