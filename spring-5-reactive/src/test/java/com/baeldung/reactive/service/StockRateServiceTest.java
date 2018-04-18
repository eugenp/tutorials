package com.baeldung.reactive.service;

import org.apache.commons.lang3.Range;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.reactive.Spring5ReactiveApplication;
import com.baeldung.reactive.model.Stock;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring5ReactiveApplication.class)
public class StockRateServiceTest {

	@Autowired
	StockRateService stockRateService;
	
	@Test
	public void whenStockIsAPPL_thenReturnBetween170And176() {
		String stockName = "APPL";
		Stock stockRateResult = stockRateService.getLiveStockRates(stockName);
		Range<Double> myRange = Range.between(170.0, 176.0);
		assert(myRange.contains(stockRateResult.getStockRate()));
	}
	
	@Test
	public void whenStockIsGOOGL_thenReturnBetween1030And1036() {
		String stockName = "GOOGL";
		Stock stockRateResult = stockRateService.getLiveStockRates(stockName);
		Range<Double> myRange = Range.between(1030.0, 1036.0);
		assert(myRange.contains(stockRateResult.getStockRate()));
	}
	
	@Test
	public void whenStockIsRandom_thenReturnBetween1And100() {
		String stockName = "Foo";
		Stock stockRateResult = stockRateService.getLiveStockRates(stockName);
		Range<Double> myRange = Range.between(1.0, 100.0);
		assert(myRange.contains(stockRateResult.getStockRate()));
	}
	
}
