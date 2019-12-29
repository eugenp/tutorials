import core.MovieApp;
import drivenports.ConsolePrinter;
import drivenports.IFetchMovieReviews;
import drivenports.IPrintMovieReviews;
import drivenports.MovieReviewsRepo;
import driverports.IUserInput;
import driverports.UserInputHandler;

public class Main {

    public static void main(String[] args) {
        IFetchMovieReviews fetchMovieReviews = new MovieReviewsRepo();
        IPrintMovieReviews printMovieReviews = new ConsolePrinter();
        MovieApp movieApp = new MovieApp(fetchMovieReviews, printMovieReviews);
        IUserInput userInput = new UserInputHandler(movieApp);
        String user_input_1 = "movieName StarWars";
        String user_input_2 = "movieName StarTreck";
        userInput.handleUserInput(user_input_1.split(" "));
        userInput.handleUserInput(user_input_2.split(" "));
    }

}
