package com.baeldung.struts;

class CarMessageService {

    public String getMessage(String carName) {
        System.out.println("inside getMessage()" + carName);
        if (carName.equalsIgnoreCase("ferrari")){
            return "Ferrari Fan!";
        }
        else if (carName.equalsIgnoreCase("bmw")){
            return "BMW Fan!";
        }
        else{
            return "please choose ferrari Or bmw";
        }
    }

}
