package com.baeldung.trimstrings;


public class StringTrimmer {
	private static final String TRIMSTART="TRIMSTART";
	private static final String TRIMEND="TRIMEND";
	private static final String TRIMBOTH="TRIMBOTH";
	
	public StringTrimmer() {}
	
	public String trimString(String stringIn, String desiredTrim){
		
		if(stringIn==null){ 
			 return"";
		}else {
			switch (desiredTrim.toUpperCase()) {
			case TRIMSTART:
				final String trimmedString=(stringIn+"X").trim();
				return trimmedString.substring(0,trimmedString.length()-1);
			case TRIMEND:
				return ("X" + stringIn).trim().substring(1);
			case TRIMBOTH:
				return stringIn.trim();
			default:
				return stringIn.trim();
			}
		}
	}
	public String trimString(String stringIn){
		return trimString(stringIn,"TRIMBOTH");
	}

}
