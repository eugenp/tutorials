/**
 * 
 */
package com.baledung.webflux.pricingpublisher.PricingPublisher;

import java.util.Random;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

/**
 * @author Bujji
 *
 */
@RestController
public class PricingController {

	
@GetMapping (value="/prices", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Object> getPrices(){
		return Flux.create(emitter -> {
			while(isPriceUpdateAvailable()){
				emitter.next(getNewPrices());
			}
		}).share();
	}
	
	private String getNewPrices() {
		Random rand = new Random();
		double app_min=220.15,app_max=230.78,amz_min=1618.87,amz_max=1689.76;
		
		double appValue = app_min + (app_max - app_min) * rand.nextDouble();
		double amzValue = amz_min + (amz_max - amz_min) * rand.nextDouble();
		
		return "AAPL: "+appValue+"|AMZN: "+amzValue+"|";
	}
	
	private boolean isPriceUpdateAvailable() {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// Do nothing
		}
		return true;
	}
}
