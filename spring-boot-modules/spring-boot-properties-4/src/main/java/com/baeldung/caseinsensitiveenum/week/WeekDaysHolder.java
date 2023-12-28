package com.baeldung.caseinsensitiveenum.week;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeekDaysHolder {

    @Value("${monday}")
    private SimpleWeekDays monday;

    @Value("${tuesday}")
    private SimpleWeekDays tuesday;

    @Value("${wednesday}")
    private SimpleWeekDays wednesday;

    @Value("${thursday}")
    private SimpleWeekDays thursday;

    @Value("${friday}")
    private SimpleWeekDays friday;

    @Value("${saturday}")
    private SimpleWeekDays saturday;

    @Value("${sunday}")
    private SimpleWeekDays sunday;

    public SimpleWeekDays getMonday() {
        return monday;
    }

    public void setMonday(final SimpleWeekDays monday) {
        this.monday = monday;
    }

    public SimpleWeekDays getTuesday() {
        return tuesday;
    }

    public void setTuesday(final SimpleWeekDays tuesday) {
        this.tuesday = tuesday;
    }

    public SimpleWeekDays getWednesday() {
        return wednesday;
    }

    public void setWednesday(final SimpleWeekDays wednesday) {
        this.wednesday = wednesday;
    }

    public SimpleWeekDays getThursday() {
        return thursday;
    }

    public void setThursday(final SimpleWeekDays thursday) {
        this.thursday = thursday;
    }

    public SimpleWeekDays getFriday() {
        return friday;
    }

    public void setFriday(final SimpleWeekDays friday) {
        this.friday = friday;
    }

    public SimpleWeekDays getSaturday() {
        return saturday;
    }

    public void setSaturday(final SimpleWeekDays saturday) {
        this.saturday = saturday;
    }

    public SimpleWeekDays getSunday() {
        return sunday;
    }

    public void setSunday(final SimpleWeekDays sunday) {
        this.sunday = sunday;
    }
}
