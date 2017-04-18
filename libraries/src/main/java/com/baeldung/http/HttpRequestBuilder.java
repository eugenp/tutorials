package com.baeldung.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

public class HttpRequestBuilder {

    private static final Logger LOG = Logger.getLogger(HttpRequestBuilder.class);

    public HttpResponseWrapper sendRequest(String urlString, String method, Map<String, String> parameters, Map<String, String> properties) {

        HttpResponseWrapper responseWrapper = new HttpResponseWrapper();

        try {

            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            if (properties != null) {
                properties.forEach((key, value) -> con.setRequestProperty(key, value));
            }
            if (parameters != null) {
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();
            }

            responseWrapper.setStatus(con.getResponseCode());

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            responseWrapper.setContent(content.toString());
        } catch (IOException exc) {
            LOG.error(exc.getMessage());
        }
        return responseWrapper;
    }

}
