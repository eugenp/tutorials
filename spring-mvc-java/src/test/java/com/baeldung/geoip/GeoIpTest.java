package com.baeldung.geoip;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import org.junit.Test;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;


public class GeoIpTest {
    
    @Test
    public void givenIPandDB_findingCity() throws IOException, GeoIp2Exception {
        String ip = "202.47.112.9";
        String dbLocation = "C:\\Users\\Parth Joshi\\Desktop\\GeoLite2-City.mmdb\\GeoLite2-City.mmdb";
        
        File database = new File(dbLocation);
        DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
        
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);
        
        String countryName = response.getCountry().getName();
        String cityName = response.getCity().getName();
    }
    
}
