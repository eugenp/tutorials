/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.GetCountries;


public class CountryDemoApplication {
    
    public String getCountriesService(String key) {
        
        key  = "your buttata key here";
        
        GetCountries GetCountries = new GetCountries();
        return GetCountries.doExposeGetCountriesPort(key); 
    }
    
}
