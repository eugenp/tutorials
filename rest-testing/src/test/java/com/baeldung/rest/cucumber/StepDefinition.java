package com.baeldung.rest.cucumber;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.github.tomakehurst.wiremock.WireMockServer;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinition {
    InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream("cucumber.json");
    String jsonString = new Scanner(jsonInputStream, "UTF-8").useDelimiter("\\Z").next();

    WireMockServer wireMockServer = new WireMockServer();
    CloseableHttpClient httpClient = HttpClients.createDefault();

    @When("^users upload data on a project$")
    public void usersUploadDataOnAProject() throws IOException {
        wireMockServer.start();

        configureFor("localhost", 8080);
        stubFor(post(urlEqualTo("/create")).withHeader("content-type", equalTo("application/json")).withRequestBody(containing("testing-framework")).willReturn(aResponse().withStatus(200)));

        HttpPost request = new HttpPost("http://localhost:8080/create");
        StringEntity entity = new StringEntity(jsonString);
        request.addHeader("content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);

        assertEquals(200, response.getStatusLine().getStatusCode());
        verify(postRequestedFor(urlEqualTo("/create")).withHeader("content-type", equalTo("application/json")));

        wireMockServer.stop();
    }

    @When("^users want to get information on the (.+) project$")
    public void usersGetInformationOnAProject(String projectName) throws IOException {
        wireMockServer.start();

        configureFor("localhost", 8080);
        stubFor(get(urlEqualTo("/projects/cucumber")).withHeader("accept", equalTo("application/json")).willReturn(aResponse().withBody(jsonString)));

        HttpGet request = new HttpGet("http://localhost:8080/projects/" + projectName.toLowerCase());
        request.addHeader("accept", "application/json");
        HttpResponse httpResponse = httpClient.execute(request);
        String responseString = convertResponseToString(httpResponse);

        assertThat(responseString, containsString("\"testing-framework\": \"cucumber\""));
        assertThat(responseString, containsString("\"website\": \"cucumber.io\""));
        verify(getRequestedFor(urlEqualTo("/projects/cucumber")).withHeader("accept", equalTo("application/json")));

        wireMockServer.stop();
    }

    @Then("^the server should handle it and return a success status$")
    public void theServerShouldReturnASuccessStatus() {
    }

    @Then("^the requested data is returned$")
    public void theRequestedDataIsReturned() {
    }

    private String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }
}