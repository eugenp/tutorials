/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.infastructure.WorldListServices;
import java.util.HashMap;
import java.util.Map;


public class GetCountries {
    public String doExposeGetCountriesPort(String key)  {

        return doGetCountriesPort(key);
    }
    
    public String doCountries(String key)  {
       
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("key", key);
        WorldListServices worldlistservices = new WorldListServices();

        String response = worldlistservices.doGetCountriesAdapter(sParaTemp);
        
        if(response == null){
            response = "please check that buttata is up or that your key is correct";
        }
        return response;
    }
    
     public String doGetCountriesPort(String key)  {

        return doCountries(key);
    }
    
}
