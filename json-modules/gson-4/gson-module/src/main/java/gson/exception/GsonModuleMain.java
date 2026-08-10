package gson.exception;

import com.google.gson.Gson;

public class GsonModuleMain {

    public static void main(String[] args) {

        String moduleName = GsonModuleMain.class.getModule()
            .getName();

        if (moduleName == null) {
            System.out.println("Mode: [ Class Path ] (Class in the Unnamed Module)");
        } else {
            System.out.println("Mode: [ Module Path ] - Module name: " + moduleName);
        }

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
