package com.baeldung;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;


//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringDemoApplication.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
public class SpringIntegrationTest {
	protected static ResponseResults latestResponse = null;

	protected RestTemplate restTemplate = null;

	protected void executeGet(String url) throws IOException{
		final Map<String,String> headers = new HashMap<>();
		headers.put("Accept","application/json");
		final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
		final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

		if (restTemplate == null){
			restTemplate = new RestTemplate();
		}

		restTemplate.setErrorHandler(errorHandler);
		latestResponse = restTemplate.execute(url,
				HttpMethod.GET,
				requestCallback,
				new ResponseExtractor<ResponseResults>(){
			@Override
			public ResponseResults extractData(ClientHttpResponse response) throws IOException {
				if (errorHandler.hadError){
					return (errorHandler.getResults());
				} else{
					return (new ResponseResults(response));
				}
			}
		});

	}
	
	protected void executePost(String url) throws IOException{
		final Map<String,String> headers = new HashMap<>();
		headers.put("Accept","application/json");
		final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
		final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

		if (restTemplate == null){
			restTemplate = new RestTemplate();
		}

		restTemplate.setErrorHandler(errorHandler);
		latestResponse = restTemplate.execute(url,
				HttpMethod.POST,
				requestCallback,
				new ResponseExtractor<ResponseResults>(){
			@Override
			public ResponseResults extractData(ClientHttpResponse response) throws IOException {
				if (errorHandler.hadError){
					return (errorHandler.getResults());
				} else{
					return (new ResponseResults(response));
				}
			}
		});

	}

	private class ResponseResultErrorHandler implements ResponseErrorHandler{
		private ResponseResults results = null;
		private Boolean hadError = false;

		private ResponseResults getResults(){
			return results;
		}

		@Override
		public boolean hasError(ClientHttpResponse response) throws IOException{
			hadError = response.getRawStatusCode() >= 400;
			return hadError;
		}

		@Override
		public void handleError(ClientHttpResponse response) throws IOException {
			results =  new ResponseResults(response);
		}
	}
}