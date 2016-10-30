package com.baeldung.spring.form;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

public class GeoIP {
    private String ipAddress;
    private String city;
    private String latitude;
    private String longitude;
    
    public GeoIP() {
        
    }
    
    public GeoIP(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    public void getLocationFromIp () throws IOException, GeoIp2Exception {
        File database = new File("C:\\Users\\Parth Joshi\\Desktop\\GeoLite2-City.mmdb\\GeoLite2-City.mmdb");
        DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
        
        
        InetAddress ipAddress = InetAddress.getByName(getIpAddress());
        CityResponse response = dbReader.city(ipAddress);
        
        setCity(response.getCity().getName());
        setLatitude(response.getLocation().getLatitude().toString());
        setLongitude(response.getLocation().getLongitude().toString());
    }
    
    

}
