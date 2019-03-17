package framework;

import java.util.Scanner;
import port.IMoveDecider;

public class HumanMoveDecider implements IMoveDecider {
    @Override
    public int[] decide() {
        Scanner scanner = new Scanner(System.in);
        String in = "";

        boolean validInput;
        do {
            in = scanner.next();
            validInput = in.matches("[0-2]+,[0-2]+");
            if (!validInput) {
                System.out.println(String.format("%s is not a valid input. Input in the following format: 1,1.", in));
            }
        } while (!validInput);

        String[] split = in.split(",");
        int row = Integer.parseInt(split[0]);
        int col = Integer.parseInt(split[1]);
        return new int[] {row, col};
    }
}
