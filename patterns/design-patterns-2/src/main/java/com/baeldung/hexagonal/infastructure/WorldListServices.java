/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.hexagonal.infastructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author cleopatradouglas
 */
public class WorldListServices {
    public String doGetCountriesAdapter(Map<String, String> sParaTemp) {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            String key = sParaTemp.get("key");
            HttpGet httpget = new HttpGet((BattutaConfig.URL + "country/all/?key=" + key + ""));
            httpget.setHeader("Content-Type", "application/x-www-form-urlencoded");
            System.out.println(httpget);

            HttpResponse response = client.execute(httpget);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2") && !response.getEntity().getContentType().getValue().contains("json")) {
                return null;
            }
            return result.toString();

        } catch (UnsupportedEncodingException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        } catch (IOException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        } catch (Exception ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
        return null;
    }

}
