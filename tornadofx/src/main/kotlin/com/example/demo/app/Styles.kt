package com.example.demo.app

import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px

class Styles : Stylesheet() {
    companion object {
        val label by cssclass()
    }

    init {
        label{
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }
    }
}