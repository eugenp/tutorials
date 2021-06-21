package com.example.demo.view

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class SampleView : View() {
    private val controller: SampleController by inject()
    private val input = SimpleStringProperty()

    override val root = form {
        fieldset {
            field() {
                textfield(input)
            }

            button("Post") {
                action {
                    controller.postApi(input.value)
                    input.value = ""
                }
            }
        }
    }
}


class SampleController: Controller() {
    fun postApi(inputValue: String) {
        println("Doing backend stuff with $inputValue")
    }
}