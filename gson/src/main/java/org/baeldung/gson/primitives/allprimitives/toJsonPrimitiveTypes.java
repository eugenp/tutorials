package org.baeldung.gson.primitives.allprimitives;

import org.baeldung.gson.primitives.models.GsonBundle;
import com.google.gson.Gson;

public class toJsonPrimitiveTypes {
    public static void main(String[] args) {
        GsonBundle gsonBundle = new GsonBundle();
        Gson gson = new Gson();

        System.out.println(gson.toJson(gsonBundle));
    }
}
