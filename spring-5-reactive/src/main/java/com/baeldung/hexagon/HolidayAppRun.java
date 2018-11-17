package com.baeldung.hexagon;

public class HolidayAppRun {

	// For testing purposes
	public static void main(String[] args) {
		HolidayCoreApp core = new HolidayCoreApp();
		HolidayService holidayFileService = new HolidayFileService();
		core.isHoliday("01-Jan-2018", holidayFileService);

		HolidayService holidayDBService = new HolidayDBService();
		core.isHoliday("01-Jan-2018", holidayDBService);
	}
}
