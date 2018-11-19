package com.hexa.example.domain;

public enum Food {

	PIZZA, BURGER,BREAD, PINANI;
	
	public String toString(){
        switch(this){
        case PIZZA :
            return "Pizza";
        case BURGER :
            return "Burger";
        case BREAD :
            return "Bread";
        case PINANI :
            return "Pinani";
        }
        return null;
    }

/*    public static Food valueOf(String value){
        if(value.equalsIgnoreCase(PIZZA.toString()))
            return Food.PIZZA;
        else if(value.equalsIgnoreCase(BURGER.toString()))
            return Food.BURGER;
        else if(value.equalsIgnoreCase(BREAD.toString()))
            return Food.BREAD;
        else if(value.equalsIgnoreCase(PINANI.toString()))
            return Food.PINANI;
        else
            return null;
    }*/
}
