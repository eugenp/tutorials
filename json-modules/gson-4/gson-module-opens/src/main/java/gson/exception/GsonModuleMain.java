package gson.exception;

import com.google.gson.Gson;

public class GsonModuleMain {

    public static void main(String[] args) {

        String moduleName = GsonModuleMain.class.getModule()
            .getName();

        System.out.println("--- Checking execution path ---");
        if (moduleName == null) {
            System.out.println("Mode: [ Class Path ] (Class in the Unnamed Module)");
        } else {
            System.out.println("Mode: [ Module Path ] - Module name: " + moduleName);
        }
        System.out.println("----------------------------------------");
        System.out.println("--- Checking JPMS reflection ---");

        Gson gson = new Gson();
        String json = "{\"name\":\"Java Conference\"}";

        try {
            ConferencePojo pojo = gson.fromJson(json, ConferencePojo.class);
            System.out.println("Deserialization successful! Object " + pojo);
        } catch (Exception e) {
            System.out.println("Expected exception caught!");
            e.printStackTrace();
        }
    }
}
