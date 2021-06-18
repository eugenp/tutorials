package com.example.demo.view

import com.example.demo.app.Styles
import tornadofx.*

class MainView : View("Hello World") {
    override val root = hbox {
        label(title) {
            addClass(Styles.label)
        }
    }
}