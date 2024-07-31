package com.baeldung.jsonjava;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONTokener;

public class CDLDemo {
    public static void main(String[] args) {
        System.out.println("8.1. Producing JSONArray Directly from Comma Delimited Text: ");
        jsonArrayFromCDT();
        
        System.out.println("\n8.2. Producing Comma Delimited Text from JSONArray: ");
        cDTfromJSONArray();
        
        System.out.println("\n8.3.1. Producing JSONArray of JSONObjects Using Comma Delimited Text: ");
        jaOfJOFromCDT2();
        
        System.out.println("\n8.3.2. Producing JSONArray of JSONObjects Using Comma Delimited Text: ");
        jaOfJOFromCDT2();
    }
    
    public static void jsonArrayFromCDT() {
        JSONArray ja = CDL.rowToJSONArray(new JSONTokener("England, USA, Canada"));
        System.out.println(ja);
    }
    
    public static void cDTfromJSONArray() {
        JSONArray ja = new JSONArray("[\"England\",\"USA\",\"Canada\"]");
        String cdt = CDL.rowToString(ja);
        System.out.println(cdt);
    }
    
    public static void jaOfJOFromCDT() {
        String string = 
          "name, city, age \n" +
          "john, chicago, 22 \n" +
          "gary, florida, 35 \n" +
          "sal, vegas, 18";
         
        JSONArray result = CDL.toJSONArray(string);
        System.out.println(result.toString());
    }
    
    public static void jaOfJOFromCDT2() {
        JSONArray ja = new JSONArray();
        ja.put("name");
        ja.put("city");
        ja.put("age");
         
        String string = 
          "john, chicago, 22 \n" +
          "gary, florida, 35 \n" +
          "sal, vegas, 18";
         
        JSONArray result = CDL.toJSONArray(ja, string);
        System.out.println(result.toString());
    }
    
}
