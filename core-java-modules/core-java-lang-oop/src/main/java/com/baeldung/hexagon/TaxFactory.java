package com.baeldung.hexagon;

public class TaxFactory {
	
	public static TaxService getTaxService() {
		return new TaxCalculator(getTaxRepository());
	}
	
	public static TaxRateRepository getTaxRepository() {
		return new ConstantTaxRateRepository();
	}

}
