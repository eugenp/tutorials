package com.baeldung.hexagon;

public class HolidayApp {
	
	public String isHoliday(String dateInStringFormat, HolidayService holidayService) {
		
		return holidayService.dateExists(dateInStringFormat) ? "Yes! It's a holiday!!" : "Ohh! It is not a holiday!!";
		
	}

	// For testing purposes	
	public static void main(String[] args) {
		
		HolidayService  hsFile = new HolidayFileService();
		boolean b1 = hsFile.dateExists("01-Jan-2018");
		System.out.println(b1);
		
		HolidayService hsDB = new HolidayDBService();
		boolean b2 = hsDB.dateExists("01-Jan-2018");
		System.out.println(b2);
	}
}
