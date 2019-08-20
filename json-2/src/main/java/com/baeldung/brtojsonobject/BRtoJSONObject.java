package com.baeldung.brtojsonobject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class BRtoJSONObject {
public static void main(String args[])
{

try {

URL url = new URL("https://baeldung.com/api/getUser");
HttpURLConnection con = (HttpURLConnection) url.openConnection();

BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));

// Java 7
StringBuilder response = new StringBuilder();
String responseLine = null;
while ((responseLine = br.readLine()) != null) {
response.append(responseLine.trim());
}
br.close();
JSONObject json = new JSONObject(response.toString());

String name = json.getString("name");

//Java 8
String line = br.lines().collect(Collectors.joining());
JSONObject json2 = new JSONObject(line); 

// Using IOUtils

String responseNew= IOUtils.toString(br);

JSONObject json3 = new JSONObject(responseNew.toString());

}
catch(Exception e)
{
e.printStackTrace();    
}

}


}
