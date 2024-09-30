package com.baeldung.teavm;

import org.teavm.jso.JSExport;
import org.teavm.jso.dom.html.*;

public class Calculator {

    public static void main(String[] args) {
        HTMLDocument document = HTMLDocument.current();
        HTMLElement container = document.getElementById("calculator-container");

        // Create input fields
        HTMLInputElement input1 = (HTMLInputElement) document.createElement("input");
        input1.setType("number");
        container.appendChild(input1);

        HTMLInputElement input2 = (HTMLInputElement) document.createElement("input");
        input2.setType("number");
        container.appendChild(input2);

        // Create a button
        HTMLButtonElement button = (HTMLButtonElement) document.createElement("button");
        button.appendChild(document.createTextNode("Calculate Sum"));
        container.appendChild(button);

        // Create a div to display the result
        HTMLElement resultDiv = document.createElement("div");
        container.appendChild(resultDiv);

        // Add click event listener to the button
        button.addEventListener("click", (evt) -> {
            try {
                long num1 = Long.parseLong(input1.getValue());
                long num2 = Long.parseLong(input2.getValue());
                long sum = num1 + num2;
                resultDiv.setTextContent("Result: " + sum);
            } catch (NumberFormatException e) {
                resultDiv.setTextContent("Please enter valid integer numbers.");
            }
        });
    }

    @JSExport
    public static int sum(int a, int b) {
        return a + b;
    }
}