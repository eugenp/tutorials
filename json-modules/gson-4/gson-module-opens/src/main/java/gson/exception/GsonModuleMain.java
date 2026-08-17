package gson.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class GsonModuleMain {

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(GsonModuleMain.class);
        String moduleName = GsonModuleMain.class.getModule()
            .getName();

        if (moduleName == null) {
            log.info("Mode: [ Class Path ] (Class in the Unnamed Module)");
        } else {
            log.info("Mode: [ Module Path ] - Module name: " + moduleName);
        }

        Gson gson = new Gson();
        String json = "{\"name\":\"Java Conference\"}";

        try {
            ConferencePojo pojo = gson.fromJson(json, ConferencePojo.class);
            log.info("Deserialization successful! Object " + pojo);
        } catch (Exception e) {
            log.info("Expected exception caught!");
            e.printStackTrace();
        }
    }
}
