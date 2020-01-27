package com.baeldung.trimstrings;


//import org.apache.commons.lang3.StringUtils;

public class StringUtilsStringTrimmer {
	private static final String TRIMSTART = "TRIMSTART";
	private static final String TRIMEND = "TRIMEND";
	private static final String TRIMBOTH = "TRIMBOTH";

	public StringUtilsStringTrimmer() {
	}

	public String trimString(String stringIn, String desiredTrim) {

		if (stringIn == null) {
			return "";
		} else {
			switch (desiredTrim.toUpperCase()) {
			case TRIMSTART:
				return org.apache.commons.lang3.StringUtils.stripStart(stringIn, " ");
			case TRIMEND:
				return org.apache.commons.lang3.StringUtils.stripEnd(stringIn, " ");
			case TRIMBOTH:
				return org.apache.commons.lang3.StringUtils.trim(stringIn);
			default:
				return org.apache.commons.lang3.StringUtils.trim(stringIn);
			}
		}
	}
	public String trimString(String stringIn){
		return trimString(stringIn,"TRIMBOTH");
	}
}