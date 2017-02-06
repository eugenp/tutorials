package com.baeldung.struts;

public class CarAction {

    private String carName;
    private String carMessage;
    private CarMessageService carMessageService = new CarMessageService();

    public String execute() {
        System.out.println("inside execute(): carName is" + carName);
        this.setCarMessage(this.carMessageService.getMessage(carName));
        return "success";
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarMessage() {
        return carMessage;
    }

    public void setCarMessage(String carMessage) {
        this.carMessage = carMessage;
    }

}
