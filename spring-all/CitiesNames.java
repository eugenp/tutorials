package testjUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
    public class CitiesNames{
    
    @Autowired
    private Cities Names;
    
    public Cities getCitiesNames(){
        return Names;
    }
} 
