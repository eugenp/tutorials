package com.baeldung.hexagonal;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;  
  
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CoffeeRecepieData {

    @XmlElement(name="coffeeRecepie")
    List<CoffeeRecepie> coffeeRecepies;

    
    public List<CoffeeRecepie> getCoffeeRecepies() {
        return coffeeRecepies;
    }

    public void setCoffeeRecepies(List<CoffeeRecepie> coffeeRecepies) {
        this.coffeeRecepies = coffeeRecepies;
    }

    @Override
    public String toString() {
        return "CoffeeRecepieData [coffeeRecepies=" + coffeeRecepies + "]";
    } 
    
}
