import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarManufacturerExample {
    
    public static void main(String[] args) {
        
        // Creating a HashMap
        Map<String, Integer> carManufacturers = new HashMap<>();
        
        carManufacturers.put("Porsche", 1931);
        carManufacturers.put("Mercedes-Benz", 1926);
        carManufacturers.put("Ford", 1903);
        carManufacturers.put("Toyota", 1937);
        carManufacturers.put("Tesla", 2003);
        
        System.out.println(carManufacturers);
        
        // HashMap with Different Value Types
        
        // Data Class Method
        Map<String, CarManufacturer> carManufacturersWithClass = new HashMap<>();
        
        List<String> toyotaCarModels = new ArrayList<>();
        toyotaCarModels.add("Camry");
        toyotaCarModels.add("86");
        toyotaCarModels.add("Avalon");
        toyotaCarModels.add("Corolla");
        toyotaCarModels.add("GR Supra");
        toyotaCarModels.add("Prius");
        
        CarManufacturer carManufacturerToyota = new CarManufacturer("Japan", 1937, 236.5, toyotaCarModels);
        
        carManufacturersWithClass.put("Toyota", carManufacturerToyota);
        
        // Non-Generic Way
        
        Map carManufacturersWithNonGeneric = new HashMap();
        
        List<String> teslaModels = new ArrayList<>();
        teslaModels.add("Model S");
        teslaModels.add("Model 3");
        teslaModels.add("Model X");
        teslaModels.add("Model Y");
        teslaModels.add("Cybertruck");
        
        carManufacturersWithNonGeneric.put("Tesla_foundedYear", 2003);
        carManufacturersWithNonGeneric.put("Tesla_originCountry", "USA");
        carManufacturersWithNonGeneric.put("Tesla_netWorth", 574);
        carManufacturersWithNonGeneric.put("Tesla_availableCarModels", teslaModels);
        
        // Generic Way
        
        Map<String, Object> carManufacturersWithGeneric = new HashMap<>();
        
        List<String> benzCarModels = new ArrayList<>();
        benzCarModels.add("GLC");
        benzCarModels.add("S-Class");
        benzCarModels.add("SL");
        benzCarModels.add("SLC");
        benzCarModels.add("A-Class");
        benzCarModels.add("C-Class");
        
        carManufacturersWithGeneric.put("Mercedes-Benz_foundedYear", 1926);
        carManufacturersWithGeneric.put("Mercedes-Benz_originCountry", "Germany");
        carManufacturersWithGeneric.put("Mercedes-Benz_netWorth", 271.63);
        carManufacturersWithGeneric.put("Mercedes-Benz_availableCarModels", benzCarModels);
    }
}
