//> using dep com.google.code.gson:gson:2.8.9
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

public class DependencyApp {
    public static void main(String args[]) {
        String jsonString = "{\"country\": \"Germany\", \"language\": \"German\", \"currency\": \"Euro\"}";
        var countryJson = JsonParser.parseString(jsonString);
        var country = countryJson.getAsJsonObject().get("country").getAsString();
        System.out.println("Selected country: " + country);
    }
}