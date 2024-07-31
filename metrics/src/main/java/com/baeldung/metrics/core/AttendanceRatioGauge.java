package com.baeldung.metrics.core;

import com.codahale.metrics.RatioGauge;

public class AttendanceRatioGauge extends RatioGauge {
    private int attendanceCount;
    private int courseCount;

    public AttendanceRatioGauge(int attendanceCount, int courseCount) {
        this.attendanceCount = attendanceCount;
        this.courseCount = courseCount;
    }

    @Override
    protected Ratio getRatio() {
        return Ratio.of(attendanceCount, courseCount);
    }
}
