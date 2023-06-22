package com.uade.ad.service;

import com.uade.ad.controller.dto.ReviewCreateDto;
import com.uade.ad.model.*;
import com.uade.ad.repository.GenreRepository;
import com.uade.ad.repository.MovieRepository;
import com.uade.ad.repository.ReviewRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final MapService mapService;
    private final CinemaService cinemaService;

    public MovieService(GenreRepository genreRepository, MovieRepository movieRepository, ReviewRepository reviewRepository, MapService mapService, CinemaService cinemaService) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.mapService = mapService;
        this.cinemaService = cinemaService;
    }

    public List<Movie> getMoviesBy(Optional<String> cinema,
                                   Optional<Double> latitude, Optional<Double> longitude,
                                   Optional<String> title,
                                   Optional<String> genre,
                                   Optional<Double> rating) {
        // TODO Agregar filtros
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


}
