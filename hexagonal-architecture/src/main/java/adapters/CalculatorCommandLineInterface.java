package adapters;

import domain.CalculatorService;
import ports.Calculator;

import java.io.Console;

public class CalculatorCommandLineInterface {
    private Calculator calculator;

    public CalculatorCommandLineInterface(Calculator calculator) {
        this.calculator = calculator;
    }

    public void runCalculator() {
        Console console = System.console();

        String factorInput1 = console.readLine("Enter the first factor: ");
        int factor1 = Integer.valueOf(factorInput1);

        String factorInput2 = console.readLine("Enter the second factor: ");
        int factor2 = Integer.valueOf(factorInput2);

        int product = calculator.multiply(factor1, factor2);

        console.writer()
            .println("The product is: " + product);
    }

    public static void main(String args[]) {
        CalculatorCommandLineInterface calculatorCli = new CalculatorCommandLineInterface(new CalculatorService());
        calculatorCli.runCalculator();
    }
}
