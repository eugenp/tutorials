package com.baeldung.caseinsensitiveenum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LenientWeekDaysHolder implements WeekDaysHolder {

    @Value("${lenient.monday}")
    private WeekDays monday;

    @Value("${lenient.tuesday}")
    private WeekDays tuesday;

    @Value("${lenient.wednesday}")
    private WeekDays wednesday;

    @Value("${lenient.thursday}")
    private WeekDays thursday;

    @Value("${lenient.friday}")
    private WeekDays friday;

    @Value("${lenient.saturday}")
    private WeekDays saturday;

    @Value("${lenient.sunday}")
    private WeekDays sunday;

    @Override
    public WeekDays getMonday() {
        return monday;
    }

    @Override
    public void setMonday(final WeekDays monday) {
        this.monday = monday;
    }

    @Override
    public WeekDays getTuesday() {
        return tuesday;
    }

    @Override
    public void setTuesday(final WeekDays tuesday) {
        this.tuesday = tuesday;
    }

    @Override
    public WeekDays getWednesday() {
        return wednesday;
    }

    @Override
    public void setWednesday(final WeekDays wednesday) {
        this.wednesday = wednesday;
    }

    @Override
    public WeekDays getThursday() {
        return thursday;
    }

    @Override
    public void setThursday(final WeekDays thursday) {
        this.thursday = thursday;
    }

    @Override
    public WeekDays getFriday() {
        return friday;
    }

    @Override
    public void setFriday(final WeekDays friday) {
        this.friday = friday;
    }

    @Override
    public WeekDays getSaturday() {
        return saturday;
    }

    @Override
    public void setSaturday(final WeekDays saturday) {
        this.saturday = saturday;
    }

    @Override
    public WeekDays getSunday() {
        return sunday;
    }

    @Override
    public void setSunday(final WeekDays sunday) {
        this.sunday = sunday;
    }
}
