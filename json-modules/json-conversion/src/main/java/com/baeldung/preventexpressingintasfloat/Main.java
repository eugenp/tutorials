package com.baeldung.preventexpressingintasfloat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;

public class Main {
    public static String draft = "[{\"id\":4077395,\"field_id\":242566,\"body\":\"\"}, " +
            "{\"id\":4077398,\"field_id\":242569,\"body\":[[273019,0],[273020,1],[273021,0]]}, " +
            "{\"id\":4077399,\"field_id\":242570,\"body\":[[273022,0],[273023,1],[273024,0]]}]";

    public static void main(String[] args) {
        ArrayList<Hashtable<String, Object>> responses;
        Type ResponseList = new TypeToken<ArrayList<Hashtable<String, Object>>>() {
        }.getType();
        responses = new Gson().fromJson(draft, ResponseList);
        System.out.println(responses);
    }
}