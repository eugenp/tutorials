package com.baeldung.reactive.model;

import java.util.Date;

public class Stock {

	private String stockName;
	private double stockRate;
	private Date stockRateDate;

	public Stock() {
		super();
	}

	public Stock(String stockName, double stockRate, Date stockRateDate) {
		super();
		this.stockName = stockName;
		this.stockRate = stockRate;
		this.stockRateDate = stockRateDate;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public double getStockRate() {
		return stockRate;
	}

	public void setStockRate(double stockRate) {
		this.stockRate = stockRate;
	}

	public Date getStockRateDate() {
		return stockRateDate;
	}

	public void setStockRateDate(Date stockRateDate) {
		this.stockRateDate = stockRateDate;
	}

}
