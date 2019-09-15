package com.baeldung.category;

class BaeldungCategory {

    public static String capitalize(String self) {
        String capitalizedStr = self;
        if (self.size() > 0) {
            capitalizedStr = self.substring(0, 1).toUpperCase() + self.substring(1);
        }
        return capitalizedStr
    }
    
    public static Number square(Number self) {
        return self*self;
    }

}
