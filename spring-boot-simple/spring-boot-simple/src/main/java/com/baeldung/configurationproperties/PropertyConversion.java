package com.baeldung.configurationproperties;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

@Configuration
@PropertySource("classpath:conversion.properties")
@ConfigurationProperties(prefix = "conversion")
public class PropertyConversion {
    private Duration timeInDefaultUnit;

    private Duration timeInNano;

    @DurationUnit(ChronoUnit.DAYS)
    private Duration timeInDays;

    private DataSize sizeInDefaultUnit;

    private DataSize sizeInGB;

    @DataSizeUnit(DataUnit.TERABYTES)
    private DataSize sizeInTB;

    private Employee employee;

    // Getters and setters

    public Duration getTimeInDefaultUnit() {
        return timeInDefaultUnit;
    }

    public void setTimeInDefaultUnit(Duration timeInDefaultUnit) {
        this.timeInDefaultUnit = timeInDefaultUnit;
    }

    public Duration getTimeInNano() {
        return timeInNano;
    }

    public void setTimeInNano(Duration timeInNano) {
        this.timeInNano = timeInNano;
    }

    public Duration getTimeInDays() {
        return timeInDays;
    }

    public void setTimeInDays(Duration timeInDays) {
        this.timeInDays = timeInDays;
    }

    public DataSize getSizeInDefaultUnit() {
        return sizeInDefaultUnit;
    }

    public void setSizeInDefaultUnit(DataSize sizeInDefaultUnit) {
        this.sizeInDefaultUnit = sizeInDefaultUnit;
    }

    public DataSize getSizeInGB() {
        return sizeInGB;
    }

    public void setSizeInGB(DataSize sizeInGB) {
        this.sizeInGB = sizeInGB;
    }

    public DataSize getSizeInTB() {
        return sizeInTB;
    }

    public void setSizeInTB(DataSize sizeInTB) {
        this.sizeInTB = sizeInTB;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
