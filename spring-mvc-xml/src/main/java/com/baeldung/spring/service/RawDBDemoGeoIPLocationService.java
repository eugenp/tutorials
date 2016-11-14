package com.baeldung.spring.service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import com.baeldung.spring.form.GeoIP;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

public class RawDBDemoGeoIPLocationService{
    public GeoIP getLocation(String ip) throws IOException, GeoIp2Exception {
        File database = new File("C:\\Users\\Parth Joshi\\Desktop\\GeoLite2-City.mmdb\\GeoLite2-City.mmdb");
        DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
                
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);
        
        String cityName = response.getCity().getName();
        String latitude = response.getLocation().getLatitude().toString();
        String longitude = response.getLocation().getLongitude().toString();
        return new GeoIP(ip, cityName, latitude, longitude);
    }
}
