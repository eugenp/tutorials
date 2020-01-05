package driverports;

import core.MovieApp;
import drivenports.IFetchMovieReviews;
import drivenports.IPrintMovieReviews;
import model.CommandMapperModel;
import org.requirementsascode.Model;
import org.requirementsascode.ModelRunner;

public class UserCommandBoundary implements IUserInput {
    private Model model;

    public UserCommandBoundary(IFetchMovieReviews fetchMovieReviews, IPrintMovieReviews printMovieReviews) {
        MovieApp movieApp = new MovieApp(fetchMovieReviews, printMovieReviews);
        model = CommandMapperModel.build(movieApp);
    }

    public void handleUserInput(Object userCommand) {
        new ModelRunner()
                .run(model)
                .reactTo(userCommand);
    }
}
