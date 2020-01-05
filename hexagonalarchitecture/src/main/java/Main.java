import core.MovieApp;
import drivenports.ConsolePrinter;
import drivenports.IFetchMovieReviews;
import drivenports.IPrintMovieReviews;
import drivenports.MovieReviewsRepo;
import driverports.IUserInput;
import driverports.UserCommandBoundary;
import model.MovieSearchRequest;
import model.MovieUser;

public class Main {

    public static void main(String[] args) {
        IFetchMovieReviews fetchMovieReviews = new MovieReviewsRepo();
        IPrintMovieReviews printMovieReviews = new ConsolePrinter();
        IUserInput userCommandBoundary = new UserCommandBoundary(fetchMovieReviews, printMovieReviews);
        MovieUser movieUser = new MovieUser(userCommandBoundary);
        MovieSearchRequest starWarsRequest = new MovieSearchRequest("StarWars");
        MovieSearchRequest starTreckRequest = new MovieSearchRequest("StarTreck");

        System.out.println("Displaying reviews for movie " + starTreckRequest.getMovieName());
        movieUser.processInput(starTreckRequest);
        System.out.println("Displaying reviews for movie " + starWarsRequest.getMovieName());
        movieUser.processInput(starWarsRequest);
    }

}
