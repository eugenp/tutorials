package com.baeldung.hexagon;

public class HexagonApp {

	public static void main(String[] args) {
		
		HolidayService  hsFile = new HolidayFileService();
		boolean b1 = hsFile.isHoliday("01-Jan-2018");
		System.out.println(b1);
		
		HolidayService hsDB = new HolidayDBService();
		boolean b2 = hsDB.isHoliday("01-Jan-2018");
		System.out.println(b2);
	}

}
