package com.baeldung.data.simple;

import java.io.IOException;
import java.util.Map;

import com.baeldung.data.utility.Utility;
import org.apache.log4j.Logger;
import com.baeldung.pojo.simple.CustomerPortfolioSimple;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * This class is responsible for performing functions for Simple type 
 * 		Jackson like
 * 		Java to Json 
 * 		Json to Map 
 * 		Json to Java Object
 */

public class SimpleDataJackson {

	private static final Logger logger = Logger.getLogger(SimpleDataJackson.class);

	static ObjectMapper mapper = new ObjectMapper();

	static long generateJsonAvgTime = 0L;

	static long parseJsonToMapAvgTime = 0L;

	static long parseJsonToActualObjectAvgTime = 0L;

	public static void main(String[] args) throws IOException {
		CustomerPortfolioSimple customerPortfolioSimple = SimpleDataGeneration.generateData();
		int j = 50;
		for (int i = 0; i < j; i++) {
			logger.info("-------Round " + (i + 1));
			String jsonStr = generateJson(customerPortfolioSimple);
			logger.info("Size of Simple content Jackson :: " + Utility.bytesIntoMB(jsonStr.getBytes().length));
			logger.info("--------------------------------------------------------------------------");

			parseJsonToMap(jsonStr);
			parseJsonToActualObject(jsonStr);
			jsonStr = null;
		}

		generateJsonAvgTime = generateJsonAvgTime / j;
		parseJsonToMapAvgTime = parseJsonToMapAvgTime / j;
		parseJsonToActualObjectAvgTime = parseJsonToActualObjectAvgTime / j;

		logger.info("--------------------------------------------------------------------------");
		logger.info("Average Time to Generate JSON : " + generateJsonAvgTime);
		logger.info("Average Time to Parse JSON To Map : " + parseJsonToMapAvgTime);
		logger.info("Average Time to Parse JSON To Actual Object : " + parseJsonToActualObjectAvgTime);
		logger.info("--------------------------------------------------------------------------");
	}

	private static String generateJson(CustomerPortfolioSimple customerPortfolioSimple) throws JsonProcessingException {
		Runtime.getRuntime().gc();
		long startParsTime = System.nanoTime();
		String json = mapper.writeValueAsString(customerPortfolioSimple);
		long endParsTime = System.nanoTime();
		long elapsedTime = endParsTime - startParsTime;
		generateJsonAvgTime = generateJsonAvgTime + elapsedTime;
		logger.info("Json Generation Time(ms):: " + elapsedTime);
		return json;
	}

	private static void parseJsonToMap(String jsonStr) throws JsonParseException , JsonMappingException , IOException {
		long startParsTime = System.nanoTime();
		Map parsedMap = mapper.readValue(jsonStr , Map.class);
		long endParsTime = System.nanoTime();
		long elapsedTime = endParsTime - startParsTime;
		parseJsonToMapAvgTime = parseJsonToMapAvgTime + elapsedTime;
		logger.info("Generating Map from json Time(ms):: " + elapsedTime);
		logger.info("--------------------------------------------------------------------------");
		parsedMap = null;
		Runtime.getRuntime().gc();
	}

	private static void parseJsonToActualObject(String jsonStr) throws JsonParseException , JsonMappingException ,
			IOException {
		long startParsTime = System.nanoTime();
		CustomerPortfolioSimple customerPortfolioSimple = mapper.readValue(jsonStr , CustomerPortfolioSimple.class);
		long endParsTime = System.nanoTime();
		long elapsedTime = endParsTime - startParsTime;
		parseJsonToActualObjectAvgTime = parseJsonToActualObjectAvgTime + elapsedTime;
		logger.info("Generating Actual Object from json Time(ms):: " + elapsedTime);
		customerPortfolioSimple = null;
		Runtime.getRuntime().gc();
	}
}
