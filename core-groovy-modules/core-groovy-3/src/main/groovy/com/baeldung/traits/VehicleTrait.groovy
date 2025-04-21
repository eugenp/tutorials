package com.baeldung.traits

trait VehicleTrait extends WheelTrait {

    String showWheels() {
        return "Num of Wheels $noOfWheels"
    }

}
