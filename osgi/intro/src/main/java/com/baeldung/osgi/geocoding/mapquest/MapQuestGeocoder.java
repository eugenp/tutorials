package com.baeldung.osgi.geocoding.mapquest;

import com.baeldung.osgi.geocoding.service.Coord;
import com.baeldung.osgi.geocoding.service.GeocodeException;
import com.baeldung.osgi.geocoding.service.GeocodingService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MapQuestGeocoder implements GeocodingService {

    private final OkHttpClient client;

    public MapQuestGeocoder() {
        this.client = new OkHttpClient();
    }

    // Consumer Key 	Dt1zZlW5HRrRfGI2nMqEyO0wlayqDozp
    // Consumer Secret 	1RzGSx0cWokI0kxz
    @Override public Coord geocode(String address) throws GeocodeException {
        //

        URL url = null;
        try {
            url = new URL(
                    String.format("http://www.mapquestapi.com/geocoding/v1/address?"
                            + "key=%s"
                            + "&location=%s"
                            + "&thumbMaps=false&outFormat=json",
                            URLEncoder.encode("Dt1zZlW5HRrRfGI2nMqEyO0wlayqDozp", "UTF-8"),
                            URLEncoder.encode(address, "UTF-8")));
        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
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
