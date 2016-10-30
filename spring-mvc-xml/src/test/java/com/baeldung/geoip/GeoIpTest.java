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
        File database = new File("C:\\Users\\Parth Joshi\\Desktop\\GeoLite2-City.mmdb\\GeoLite2-City.mmdb");
        DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
        
        InetAddress ipAddress = InetAddress.getByName("202.47.112.9");
        CityResponse response = dbReader.city(ipAddress);
        
        System.out.println(response.getCountry().getName());
        System.out.println(response.getCity().getName());
        
    }
    
}
