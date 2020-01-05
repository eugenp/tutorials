package com.baeldung.hexagonalarchitecture.driverports;

import com.baeldung.hexagonalarchitecture.core.MovieApp;
import com.baeldung.hexagonalarchitecture.drivenports.IFetchMovieReviews;
import com.baeldung.hexagonalarchitecture.drivenports.IPrintMovieReviews;
import com.baeldung.hexagonalarchitecture.model.CommandMapperModel;
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
