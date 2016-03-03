package com.baeldung.data.complex;

import java.util.Map;

import org.apache.log4j.Logger;
import com.baeldung.data.utility.Utility;
import com.baeldung.pojo.complex.CustomerPortfolioComplex;

import com.google.gson.Gson;

/**
 * 
 * This class is responsible for performing functions for Complex type 
 * 		GSON like 
 * 		Java to Json 
 * 		Json to Map 
 * 		Json to Java Object
 */

public class ComplexDataGson {

	private static final Logger logger = Logger.getLogger(ComplexDataGson.class);

	static Gson gson = new Gson();

	static long generateJsonAvgTime = 0L;

	static long parseJsonToMapAvgTime = 0L;

	static long parseJsonToActualObjectAvgTime = 0L;

	public static void main(String[] args) {
		CustomerPortfolioComplex customerPortfolioComplex = ComplexDataGeneration.generateData();
		int j = 50;
		for (int i = 0; i < j; i++) {
			logger.info("-------Round " + (i + 1));
			String jsonStr = generateJson(customerPortfolioComplex);
			logger.info("Size of Complex content Jackson :: " + Utility.bytesIntoMB(jsonStr.getBytes().length));
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

	private static String generateJson(CustomerPortfolioComplex customerPortfolioComplex) {
		Runtime.getRuntime().gc();
		long startParsTime = System.nanoTime();
		String json = gson.toJson(customerPortfolioComplex);
		long endParsTime = System.nanoTime();
		long elapsedTime = endParsTime - startParsTime;
		generateJsonAvgTime = generateJsonAvgTime + elapsedTime;
		logger.info("Json Generation Time(ms):: " + elapsedTime);
		return json;
	}

	private static void parseJsonToMap(String jsonStr) {
		long startParsTime = System.nanoTime();
		Map parsedMap = gson.fromJson(jsonStr , Map.class);
		long endParsTime = System.nanoTime();
		long elapsedTime = endParsTime - startParsTime;
		parseJsonToMapAvgTime = parseJsonToMapAvgTime + elapsedTime;
		logger.info("Generating Map from json Time(ms):: " + elapsedTime);
		logger.info("--------------------------------------------------------------------------");
		parsedMap = null;
		Runtime.getRuntime().gc();
	}

	private static void parseJsonToActualObject(String jsonStr) {
		long startParsTime = System.nanoTime();
		CustomerPortfolioComplex customerPortfolioComplex = gson.fromJson(jsonStr , CustomerPortfolioComplex.class);
		long endParsTime = System.nanoTime();
		long elapsedTime = endParsTime - startParsTime;
		parseJsonToActualObjectAvgTime = parseJsonToActualObjectAvgTime + elapsedTime;
		logger.info("Generating Actual Object from json Time(ms):: " + elapsedTime);
		logger.info("--------------------------------------------------------------------------");
		customerPortfolioComplex = null;
		Runtime.getRuntime().gc();

	}
}
