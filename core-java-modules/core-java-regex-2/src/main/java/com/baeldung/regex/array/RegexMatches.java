package com.baeldung.regex.array;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
	
class RegexMatches {
	
    String[] regexMatch(String strSearch)
	{
	    List<String> matchesList = new ArrayList<String>();
	    String stringToSearch = strSearch;
	    Pattern p1 = Pattern.compile("780{1}\\d{7}");
	    Matcher m1 = p1.matcher(stringToSearch);
	    while (m1.find()) 
	    {
	    	matchesList.add(m1.group());
	    }
	    int sizeOfNewArray = matchesList.size();
	    String newArrayOfMatches[] = new String[sizeOfNewArray];
	    matchesList.toArray(newArrayOfMatches);
	    return newArrayOfMatches;
	}
}
