package com.baeldung.java9.httpclient;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.HttpStatusCode.OK_200;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Assert;

public class HttpCutomCookieTest {

	private static final Logger logger = LoggerFactory.getLogger(HttpCutomCookieTest.class);
	private static final String serverHost = "localhost";
	private static final int serverPort = 8080;
	private static final String baseURL = "http://" + serverHost + ":" + serverPort;
	private URI httpURI;
	private ClientAndServer mockServer;

	private String cookieName = "Foo";
	private String cookieValue = "Bar";

	@Before
	public void init() throws URISyntaxException {
		httpURI = new URI(baseURL);

		setupMockHttpServer();
	}

	@Test
	public void customCookie() throws IOException, InterruptedException, URISyntaxException {
		HttpCookie cookie = new HttpCookie(cookieName, cookieValue);
		cookie.setMaxAge(100);
		cookie.setDomain("localhost");
		CookieManager cManager = new CookieManager();
		cManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
//		List<String> list = new ArrayList<String>();
//		list.add("Foo=Bar; Max-Age=100");
//		HashMap<String, List <String>> map = new HashMap<>();
//		map.put("Set-Cookie", list);
		//cManager.put(httpURI, map);
		//cManager.getCookieStore().add(new URI(baseURL), cookie);
		
		
		logCookies(cManager);
		HttpClient.Builder hcBuilder = HttpClient.create().cookieManager(cManager);
		HttpRequest.Builder reqBuilder = hcBuilder.build().request(httpURI);

		HttpRequest request = reqBuilder.followRedirects(HttpClient.Redirect.ALWAYS).GET();
		HttpRequest request2 = reqBuilder.GET();
		HttpResponse response = request.response();
		HttpResponse response2 = request2.response();
		logCookies(cManager);
		Assert.assertEquals("HTTp response code ", OK_200.code(), response2.statusCode());
	}

	private void setupMockHttpServer() {
		mockServer = startClientAndServer(serverPort);

		MockServerClient client = new MockServerClient(serverHost, serverPort);
		client.when(request().withMethod("GET")//.withCookie(cookieName, cookieValue)
				)
				.respond(response().withStatusCode(OK_200.code())
						.withHeaders(new Header("Content-Type", "application/json; charset=utf-8"),
								new Header("Cache-Control", "public, max-age=86400"))
						.withCookie(cookieName, cookieValue)
						.withBody("{ message: 'The Java Stuff' }").withDelay(new Delay(MILLISECONDS, 30)));
	}

	
	private void logCookies(CookieManager cm) throws IOException{
		CookieStore cs = cm.getCookieStore();
		logger.debug("Cookie in CM {}", cm);
		for(HttpCookie c : cs.getCookies()){
			logger.debug("Cookie: [{}] with max age {}", c, c.getMaxAge());
		}
	}
	
	@After
	public void stopProxy() {
		mockServer.stop();
	}

}
