package com.baeldung.jsonjava;

import org.json.JSONObject;

public class ObjectToFromJSON {

	public static void main(String args[]) throws Exception {
        System.out.println("\n5.1. Creating JSONObject from Java Bean: ");
        jsonFromDemoBean();
	}
    
    public static void jsonFromDemoBean() {
        DemoBean demo = new DemoBean();
        demo.setId(1);
        demo.setName("lorem ipsum");
        demo.setActive(true);
         
        JSONObject jo = new JSONObject(demo);
        System.out.println(jo);
    }
}
