package com.baeldung.trimstrings;


import com.baeldung.trimstrings.StringTrimmer;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StringTrimmerUnitTest {
	static Logger logger = LoggerFactory.getLogger("log");
@Test
public void testTrimming() {
	  StringTrimmer stringTrimmer=new StringTrimmer();
	  
	  logger.info("Starting test of StringTrimmer"); 
	  
	  assertEquals("TRIMSTART should trim '  CAN' to 'CAN'","CAN",stringTrimmer.trimString("  CAN","TRIMSTART"));  
	  assertEquals("TRIMSTART should trim '  CAN  ' to 'CAN  '","CAN  ",stringTrimmer.trimString("  CAN  ","TRIMSTART"));  
	  assertEquals("TRIMEND should trim 'CAN' to 'CAN'","CAN",stringTrimmer.trimString("CAN","TRIMEND"));  
	  assertEquals("TRIMEND should trim '  CAN  ' to 'CAN'","  CAN",stringTrimmer.trimString("  CAN  ","TRIMEND"));  
	  assertEquals("TRIMBOTH should trim 'CAN' to 'CAN'","CAN",stringTrimmer.trimString("CAN","TRIMBOTH"));  
	  assertEquals("TRIMBOTH should trim '  CAN  ' to 'CAN'","CAN",stringTrimmer.trimString("  CAN  ","TRIMBOTH"));  
	  assertEquals("Trim with 1 arg should trim 'CAN' to 'CAN'","CAN",stringTrimmer.trimString("CAN"));  
	  assertEquals("Trim with 1 arg should trim '  CAN  ' to 'CAN'","CAN",stringTrimmer.trimString("  CAN  "));  
	  assertEquals("Trim with 1 arg should trim 'CAN  ' to 'CAN'","CAN",stringTrimmer.trimString("CAN  "));
	  assertEquals("Trim with bad argument should trim '  CAN  ' to 'CAN'","CAN",stringTrimmer.trimString("  CAN  ","garbage"));  

	  
	  logger.info("*****StringTrimmer tests finished************"); 

	  }

}
