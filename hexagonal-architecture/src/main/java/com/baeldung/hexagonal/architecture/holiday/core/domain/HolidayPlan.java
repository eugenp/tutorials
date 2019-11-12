package com.baeldung.hexagonal.architecture.holiday.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HolidayPlan {

    private List<Holiday> holidays;
}
