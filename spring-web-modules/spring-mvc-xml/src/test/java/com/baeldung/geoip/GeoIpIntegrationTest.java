package com.baeldung.geoip;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import org.junit.Test;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

public class GeoIpIntegrationTest {

    @Test
    public void givenIP_whenFetchingCity_thenReturnsCityData() throws IOException, GeoIp2Exception {
        
        ClassLoader classLoader = getClass().getClassLoader();
        File database = new File("your-path-to-db-file");
        DatabaseReader dbReader = new DatabaseReader.Builder(database).build();

        InetAddress ipAddress = InetAddress.getByName("google.com");
        CityResponse response = dbReader.city(ipAddress);

        String countryName = response.getCountry().getName();
        String cityName = response.getCity().getName();
        String postal = response.getPostal().getCode();
        String state = response.getLeastSpecificSubdivision().getName();

    }

}
