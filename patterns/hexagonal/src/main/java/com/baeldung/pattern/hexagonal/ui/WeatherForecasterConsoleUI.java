package com.baeldung.pattern.hexagonal.ui;

import com.baeldung.pattern.hexagonal.service.WeatherForecasterService;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WeatherForecasterConsoleUI implements WeatherForecasterUI {
    private WeatherForecasterService service;
    private Scanner in;
    private PrintStream out;

    public WeatherForecasterConsoleUI(WeatherForecasterService service) {
        this.service = service;
        this.in = new Scanner(System.in);
        this.out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    }

    @Override
    public void start() {
        String input = getInput();
        while (isNotExit(input)) {
            showTemperature(input);
            input = getInput();
        }
        stop();
    }

    private String getInput() {
        out.print("Location: ");
        return in.nextLine();
    }

    private boolean isNotExit(String input) {
        return !input.equalsIgnoreCase("exit");
    }

    private void showTemperature(String input) {
        double temperature = service.forecast(input);
        out.printf("%.2f\u00B0C%n", temperature);
    }

    private void stop() {
        in.close();
        out.close();
    }
}
