package com.baeldung.nested

class Computer(val model: String) {

    companion object {
        const val originCountry = "China"
        fun getBuiltDate(): String {
            return "2018-05-23"
        }
    }

    //Nested class
    class MotherBoard(val manufacturer: String) {
        fun getInfo() = "Made by $manufacturer installed in $originCountry - ${getBuiltDate()}"
    }

    //Inner class
    inner class HardDisk(val sizeInGb: Int) {
        fun getInfo() = "Installed on ${this@Computer} with $sizeInGb GB"
    }

    interface Switcher {
        fun on(): String
    }

    fun powerOn(): String {
        //Local class
        class Led(val color: String) {
            fun blink(): String {
                return "blinking $color"
            }
        }

        val powerLed = Led("Green")
        //Anonymous class
        val powerSwitch = object : Switcher {
            override fun on(): String {
                return powerLed.blink()
            }
        }
        return powerSwitch.on()
    }

    override fun toString(): String {
        return "Computer(model=$model)"
    }
}