package testjUnit;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Scope(value="global-session", proxyMode =ScopedProxyMode.TARGET_CLASS)
@Component

public class Cities{
    
    private String city = "New York";
    
    public Cities(){
        System.out.println("My city:" + city);
    }
    
    public String getCity(){
        return city;
    }
}

