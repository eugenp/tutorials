package com.baeldung.exceptions.nosuchmethoderror;

import java.util.StringJoiner;

public class MainMenu {
	public static void main(String[] args) {
		System.out.println("Today's Specials: " + getSpecials());
	}

	public static StringJoiner getSpecials() {
		StringJoiner specials = new StringJoiner(", ");
		try {
			specials.add(SpecialToday.getStarter());
			specials.add(SpecialToday.getDesert());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return specials;
	}
}
