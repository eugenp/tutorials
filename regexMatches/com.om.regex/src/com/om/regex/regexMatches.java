package com.om.regex;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
	
public class regexMatches {
		public static void main(String[] args) {
			List<String> matchesList = new ArrayList<String>();
		    // specify that we want to search for in a String variable
		    String stringToSearch = "7801111111fsdafasdfa  780222222  sadfsadfsda7803333333 sadfdasfasd 7804444444";
		    Pattern p1 = Pattern.compile("780{1}\\d{7}"); // compile method is a static factory method in Pattern class; returns equivalent java Pattern object of the regexp
		    Matcher m1 = p1.matcher(stringToSearch); // Matcher object is created by using matcher method of Pattern class

		    // if our pattern matches the string, we can extract our groups
		    int i=0;
		    while (m1.find()) // find method is in Matcher class, returns boolean if match found
		    {
		    	System.out.println("Match " + (i+1) + ": " + m1.group()); // group method is in Matcher class, returns String. Outputs the matched pattern.
		    	matchesList.add(m1.group());
		    	i++;
		    }
		    /*
		    //as List
		    System.out.println("In List Form:");
		    for(String m:matchesList)
		    {
		    	System.out.println(m);
		    }
		    */
		    int sizeOfNewArray = matchesList.size();
		    String newArrayOfMatches[] = new String[sizeOfNewArray];
		    matchesList.toArray(newArrayOfMatches);
		    System.out.println("From variable of type: " + newArrayOfMatches.getClass().getSimpleName());
		    for(String m:newArrayOfMatches)
		    {
		    	System.out.println(m);
		    }
		}
}
