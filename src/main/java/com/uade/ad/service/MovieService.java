package com.uade.ad.service;

import com.uade.ad.controller.dto.ReviewCreateDto;
import com.uade.ad.model.*;
import com.uade.ad.repository.CinemaRepository;
import com.uade.ad.repository.GenreRepository;
import com.uade.ad.repository.MovieRepository;
import com.uade.ad.repository.ReviewRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final MapService mapService;
    private final CinemaRepository cinemaRepository;

    public MovieService(GenreRepository genreRepository,
                        MovieRepository movieRepository,
                        ReviewRepository reviewRepository,
                        MapService mapService,
                        CinemaRepository cinemaRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.mapService = mapService;
        this.cinemaRepository = cinemaRepository;
    }

    public List<Movie> getMoviesBy(Optional<Long> cinema,
                                   Optional<Double> latitude, Optional<Double> longitude,
                                   Optional<Double> distance,
                                   Optional<String> title,
                                   Optional<String> genre,
                                   Optional<Double> rating) {

        if (cinema.isPresent()) return filterMoviesByCinema(cinema.get());
        if (latitude.isPresent() && longitude.isPresent() && distance.isPresent()) return filterMoviesByDistance(latitude.get(), longitude.get(), distance.get());
        if (title.isPresent()) return filterMoviesByTitle(title.get());
        if (genre.isPresent()) return filterMoviesByGenre(genre.get());
        if (rating.isPresent()) return filterMoviesByRating(rating.get());
        return movieRepository.findAll();
    }

    public Review createReview(final Integer movieId, final ReviewCreateDto reviewCreateDto, final User user) {
        Optional<Movie> movieOptional = findById(movieId);
        if (movieOptional.isPresent()) {
            Instant time = Instant.now();
            Movie movie = movieOptional.get();
            Review review = Review.builder()
                    .user(user)
                    .rating(reviewCreateDto.getRating())
                    .comment(reviewCreateDto.getComment())
                    .date(time)
                    .build();

            Review savedReview = reviewRepository.save(review);
            movie.getReviews().add(review);
            movieRepository.save(movie);

            return savedReview;
        } else {
            return null;
        }
    }

    public Optional<Movie> findById(Integer movieId) {
        return movieRepository.findById(movieId);

    }

    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    private List<Movie> filterMoviesByCinema(final Long cinemaId) {
        List<Movie> movies = new ArrayList<>();
        Optional<Cinema> cinemaOpt = cinemaRepository.findById(cinemaId);
        cinemaOpt.ifPresent(cinema -> movies.addAll(cinema.getMoviesInTheaters()));
        return movies;
    }

    private List<Movie> filterMoviesByDistance(final double latitude, final double longitude, final double distance) {
        List<Cinema> cinemas = cinemaRepository.findAll();
        List<Movie> movies = new ArrayList<>();
        for(Cinema cinema : cinemas) {
            if(mapService.calculateDistance(cinema.getLatitude(), cinema.getLongitude(), latitude, longitude) < distance * 1000) {
                movies.addAll(cinema.getMoviesInTheaters());
            }
        }
        return movies;
    }

    private List<Movie> filterMoviesByTitle(final String title) {
        return movieRepository.findMoviesByTitleContainingIgnoreCase(title);
    }

    private List<Movie> filterMoviesByGenre(final String genre) {
        List<Movie> movies = movieRepository.findAll();
        movies.removeIf(movie -> !movie.containsGenre(genre));
        return movies;
    }

    private List<Movie> filterMoviesByRating(final double rating) {
        return movieRepository.findMoviesByRatingGreaterThanEqual(rating);
    }
}
