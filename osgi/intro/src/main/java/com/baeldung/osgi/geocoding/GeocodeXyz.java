package com.baeldung.osgi.geocoding;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GeocodeXyz {

    private final OkHttpClient client;

    public GeocodeXyz() {
        this.client = new OkHttpClient();
    }

    public Coord geocode(String address) throws GeocodeException {
        String url = null;
        try {
            url = String.format("https://geocode.xyz/?locate=%s&geoit=JSON", URLEncoder.encode(address, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // should really never happen
        }
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            System.out.println(url);
            response = client.newCall(request).execute();
            String responseAsStr = response.body().string();
            System.out.println(responseAsStr);

            JsonReader jsonReader = Json.createReader(new StringReader(responseAsStr));
            JsonObject jobj = jsonReader.readObject();
            System.out.println(jobj);

            double latt = Double.parseDouble( jobj.getString("latt") );
            double longt = Double.parseDouble( jobj.getString("longt") );
            return new Coord(latt, longt);
        } catch (IOException e) {
            throw new GeocodeException(e);
        }

    }
}
