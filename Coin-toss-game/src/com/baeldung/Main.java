package com.baeldung;

import com.baeldung.hexagonaldesign.Display;
import com.baeldung.hexagonaldesign.HexagonalCoinToss;
import com.baeldung.hexagonaldesign.UserInput;
import com.baeldung.hexagonaldesign.impl.ConsoleDisplay;
import com.baeldung.hexagonaldesign.impl.KeyboardInput;

public class Main {

    public static void main(String[] args) {
//        CoinToss game = new CoinToss();
//        game.play();

        Display display = new ConsoleDisplay();
        UserInput userInput = new KeyboardInput();
        new HexagonalCoinToss(display, userInput).play();
    }
}
