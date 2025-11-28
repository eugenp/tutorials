import java.util.Scanner;

public class SampleScanner {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        try {
            while (scan.hasNextLine()) {
                String line = scan.nextLine().toLowerCase();
                System.out.println(line);
            }
        } finally {
            scan.close();
        }
    }
}
