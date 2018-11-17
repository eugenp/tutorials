package com.baeldung.hexagon;

public class HolidayCoreApp {

	public String isHoliday(String dateInStringFormat, HolidayService holidayService) {
		return holidayService.dateExists(dateInStringFormat) ? "Yes! It's a holiday!!" : "Ohh! It is not a holiday!!";
	}
}
