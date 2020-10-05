package baeldung.hexagonal_architecture_java;

import java.util.Scanner;

import baeldung.hexagonal_architecture_java.business_logic.BookShelf;
import baeldung.hexagonal_architecture_java.business_logic.IBookShelf;

public class App {
    private static IBookShelf bookshelf;

    public static void main(String[] args) {
        try {
            
            bookshelf = new BookShelf(BookShelf.H2_STORE);

            System.out.println("Please Select an Option" + System.lineSeparator());
            System.out.println("Press 1 to Insert book to bookshelf" + System.lineSeparator());
            System.out.println("Press 2 to get book from bookshelf" + System.lineSeparator());
            System.out.println("Press 3 return book to bookshelf" + System.lineSeparator());
            System.out.println("Press 4 to remove books from bookshelf" + System.lineSeparator());

            Scanner userInput = new Scanner(System.in);
            userInput.useDelimiter("[\\r\\n]");
            int response = userInput.nextInt();
            if (response == 1){
                System.out.println("Input book name" + System.lineSeparator());
                userInput.skip("[\r\n]+");
                String bookName = userInput.nextLine();

                System.out.println("Input author name" + System.lineSeparator());
                String authorName = userInput.nextLine();

                System.out.println("Input isbn" + System.lineSeparator());
                String isbn = userInput.nextLine();
                

                System.out.println("Input number of books" + System.lineSeparator());
                int numberOfCopies = userInput.nextInt();

                bookshelf.createBookEntry(bookName, isbn, authorName, numberOfCopies);
                System.out.println("Book has been entered");
            }else if(response == 2){

            }else if(response == 3){

            }else if(response == 4){

            }

         
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Unable to start application because could not connect to data store");
        }

    }
}
