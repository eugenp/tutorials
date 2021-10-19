import java.util.List;

public class CarManufacturer {
    
    protected String originCountry;
    protected Integer foundedYear;
    protected Double netWorth;
    protected List<String> availableCarModels;
    
    public CarManufacturer(String originCountry, Integer foundedYear, Double netWorth, List<String> availableCarModels) {
        this.originCountry = originCountry;
        this.foundedYear = foundedYear;
        this.netWorth = netWorth;
        this.availableCarModels = availableCarModels;
    }
}
