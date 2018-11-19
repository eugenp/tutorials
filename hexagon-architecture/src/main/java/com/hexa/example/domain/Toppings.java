package com.hexa.example.domain;

public enum Toppings {
	MUSHROOM,OLIVE,TOMATO,CHEESE,HAM,PEPPERONI,CHICKEN,CORN;
	
	public String toString(){
        switch(this){
        case MUSHROOM :
            return "Mushroom";
        case OLIVE :
            return "Olive";
        case TOMATO :
            return "Tomato";
        case CHEESE :
            return "Cheese";
        case HAM :
            return "Ham";
        case PEPPERONI :
            return "Pepperoni";
        case CHICKEN :
            return "Chicken";
        case CORN :
            return "Corn";
        }
        return null;
    }

/*    public static Toppings valueOf(Class<Toppings> enumType, String value){
        if(value.equalsIgnoreCase(MUSHROOM.toString()))
            return Toppings.MUSHROOM;
        else if(value.equalsIgnoreCase(OLIVE.toString()))
            return Toppings.OLIVE;
        else if(value.equalsIgnoreCase(TOMATO.toString()))
            return Toppings.TOMATO;
        else if(value.equalsIgnoreCase(CHEESE.toString()))
            return Toppings.CHEESE;
        if(value.equalsIgnoreCase(HAM.toString()))
            return Toppings.HAM;
        else if(value.equalsIgnoreCase(PEPPERONI.toString()))
            return Toppings.PEPPERONI;
        else if(value.equalsIgnoreCase(CHICKEN.toString()))
            return Toppings.CHICKEN;
        else if(value.equalsIgnoreCase(CORN.toString()))
            return Toppings.CORN;
        else
            return null;
    }*/
}
