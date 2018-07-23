package com.baeldung.nested

import org.slf4j.LoggerFactory

class Computer(val model: String) {

    companion object {
        const val originCountry = "China"
        fun getBuiltDate(): String {
            return "2018-05-23"
        }

        val log = LoggerFactory.getLogger(Computer.javaClass)
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
        var defaultColor = "Blue"

        class Led(val color: String) {
            fun blink(): String {
                return "blinking $color"
            }

            fun changeDefaultPowerOnColor() {
                defaultColor = "Violet"
            }
        }

        val powerLed = Led("Green")
        log.debug("defaultColor is $defaultColor")
        powerLed.changeDefaultPowerOnColor()
        log.debug("defaultColor changed inside Led class to $defaultColor")
        //Anonymous class
        val powerSwitch = object : Switcher {
            override fun on(): String {
                return powerLed.blink()
            }
            fun changeDefaultPowerOnColor() {
                defaultColor = "Yellow"
            }
        }
        powerSwitch.changeDefaultPowerOnColor()
        log.debug("defaultColor changed inside powerSwitch anonymous class to $defaultColor")
        return powerSwitch.on()
    }

    override fun toString(): String {
        return "Computer(model=$model)"
    }
}