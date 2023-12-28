package com.baeldung.caseinsensitiveenum.week;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimpleWeekDaysHolder implements WeekDaysHolder {

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

    @Override
    public SimpleWeekDays getMonday() {
        return monday;
    }

    @Override
    public void setMonday(final SimpleWeekDays monday) {
        this.monday = monday;
    }

    @Override
    public SimpleWeekDays getTuesday() {
        return tuesday;
    }

    @Override
    public void setTuesday(final SimpleWeekDays tuesday) {
        this.tuesday = tuesday;
    }

    @Override
    public SimpleWeekDays getWednesday() {
        return wednesday;
    }

    @Override
    public void setWednesday(final SimpleWeekDays wednesday) {
        this.wednesday = wednesday;
    }

    @Override
    public SimpleWeekDays getThursday() {
        return thursday;
    }

    @Override
    public void setThursday(final SimpleWeekDays thursday) {
        this.thursday = thursday;
    }

    @Override
    public SimpleWeekDays getFriday() {
        return friday;
    }

    @Override
    public void setFriday(final SimpleWeekDays friday) {
        this.friday = friday;
    }

    @Override
    public SimpleWeekDays getSaturday() {
        return saturday;
    }

    @Override
    public void setSaturday(final SimpleWeekDays saturday) {
        this.saturday = saturday;
    }

    @Override
    public SimpleWeekDays getSunday() {
        return sunday;
    }

    @Override
    public void setSunday(final SimpleWeekDays sunday) {
        this.sunday = sunday;
    }
}
