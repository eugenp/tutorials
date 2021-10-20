package com.example.hexagonal.port;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.hexagonal.domain.Customer;
import com.example.hexagonal.repository.CustomerRepository;
import com.example.hexagonal.domain.Movie;
import com.example.hexagonal.repository.MovieRepository;

@Service
public class MovieHubImpl implements MovieHubService {

    @Autowired
    MovieRepository movieRepo;
    @Autowired
    CustomerRepository customerRepo;

    @Override
    public Movie getMovieDetails(String movieTitle) {
        return movieRepo.findMovieByTitle(movieTitle);
    }

    @Override
    public String registerNewUser(String userName, String userLocation) {
        Customer customer = customerRepo.save(new Customer(userName, userLocation));
        return "User successfully created with id = " + customer.getUserid();
    }

    @Override
    public String addMovie(int id, String title, int releaseYear, double imdbRating, int rottenTomatoesScore) {
        return movieRepo.save(new Movie(id, title, releaseYear, imdbRating, rottenTomatoesScore))
            .toString();
    }

}
