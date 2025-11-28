import java.util.Scanner;

public class EOFExample {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        try {
            System.out.println("Enter text (press CTRL+D on Unix/Mac or CTRL+Z on Windows to end):");

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                System.out.println("You entered: " + line);
            }

            System.out.println("End of input detected. Program terminated.");
        } finally {
            scan.close();
        }
    }
}
