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

    public HttpResponseWrapper sendRequest(String urlString, String method, Map<String, String> parameters, Map<String, String> properties) throws IOException{
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
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
            
        int status = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        HttpResponseWrapper responseWrapper = new HttpResponseWrapper();
        responseWrapper.setStatus(status);
        responseWrapper.setContent(content.toString());
        
		return responseWrapper;
    }

}
