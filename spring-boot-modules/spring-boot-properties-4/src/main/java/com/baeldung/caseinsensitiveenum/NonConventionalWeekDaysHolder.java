package com.baeldung.caseinsensitiveenum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NonConventionalWeekDaysHolder {

    @Value("${non_conventional.monday}")
    private NonConventionalWeekDays monday;

    @Value("${non_conventional.tuesday}")
    private NonConventionalWeekDays tuesday;

    @Value("${non_conventional.wednesday}")
    private NonConventionalWeekDays wednesday;

    @Value("${non_conventional.thursday}")
    private NonConventionalWeekDays thursday;

    @Value("${non_conventional.friday}")
    private NonConventionalWeekDays friday;

    @Value("${non_conventional.saturday}")
    private NonConventionalWeekDays saturday;

    @Value("${non_conventional.sunday}")
    private NonConventionalWeekDays sunday;

    public NonConventionalWeekDays getMonday() {
        return monday;
    }

    public void setMonday(final NonConventionalWeekDays monday) {
        this.monday = monday;
    }

    public NonConventionalWeekDays getTuesday() {
        return tuesday;
    }

    public void setTuesday(final NonConventionalWeekDays tuesday) {
        this.tuesday = tuesday;
    }

    public NonConventionalWeekDays getWednesday() {
        return wednesday;
    }

    public void setWednesday(final NonConventionalWeekDays wednesday) {
        this.wednesday = wednesday;
    }

    public NonConventionalWeekDays getThursday() {
        return thursday;
    }

    public void setThursday(final NonConventionalWeekDays thursday) {
        this.thursday = thursday;
    }

    public NonConventionalWeekDays getFriday() {
        return friday;
    }

    public void setFriday(final NonConventionalWeekDays friday) {
        this.friday = friday;
    }

    public NonConventionalWeekDays getSaturday() {
        return saturday;
    }

    public void setSaturday(final NonConventionalWeekDays saturday) {
        this.saturday = saturday;
    }

    public NonConventionalWeekDays getSunday() {
        return sunday;
    }

    public void setSunday(final NonConventionalWeekDays sunday) {
        this.sunday = sunday;
    }
}
