package com.baeldung.metaprogramming.extension

import com.baeldung.metaprogramming.Employee

class BasicExtensions {
    
    static int getYearOfBirth(Employee self) {
        return (new Date().getYear() + 1900) - self.age;
    }
    
    static String capitalize(String self) {
        return self.substring(0, 1).toUpperCase() + self.substring(1)
    }

    static void printCounter(Integer self) {
        while (self>0) {
            println self
            self--
        }
    }
    
    static Long square(Long self) {
        return self*self
    }
    
    static BigDecimal cube(BigDecimal self) {
        return self*self*self
    }
    
}