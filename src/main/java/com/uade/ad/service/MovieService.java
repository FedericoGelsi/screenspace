package com.uade.ad.service;

import com.uade.ad.controller.dto.GenreResponseDto;
import com.uade.ad.controller.dto.MovieResponseDto;
import com.uade.ad.controller.dto.ReviewCreateDto;
import com.uade.ad.model.*;
import com.uade.ad.repository.GenreRepository;
import com.uade.ad.repository.MovieRepository;
import com.uade.ad.repository.ReviewRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<MovieResponseDto> getMoviesBy(Optional<String> cinema,
                                   Optional<Double> latitude, Optional<Double> longitude,
                                   Optional<String> title,
                                   Optional<String> genre,
                                   Optional<Double> rating,
                                   String language) {
        // TODO Agregar filtros
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movie -> mapToMovieResponseDto(movie, language)).toList();
    }

    public Review createReview(final Integer movieId, final ReviewCreateDto reviewCreateDto, final User user) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
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

    public Optional<MovieResponseDto> findById(final Integer movieId, final String language) throws Exception {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new Exception("Movie not found"));
        return Optional.of(mapToMovieResponseDto(movie, language));

    }

    public List<GenreResponseDto> getGenres(final String language) {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream().map(genre -> mapToGenreResponseDto(genre, language)).toList();
    }

    private MovieResponseDto mapToMovieResponseDto(Movie movie, String language) {
        return MovieResponseDto.builder()
                .id(movie.getId())
                .title(movie.getTitle(language))
                .duration(movie.getDuration())
                .imageUrl(movie.getImageUrl())
                .genres(movie.getGenres().stream().map(genre -> mapToGenreResponseDto(genre, language)).collect(Collectors.toSet()))
                .synopsis(movie.getSynopsis(language))
                .rating(movie.getRating())
                .isShowing(movie.isShowing())
                .releaseDate(movie.getReleaseDate())
                .reviews(movie.getReviews())
                .build();
    }

    private GenreResponseDto mapToGenreResponseDto(Genre genre, String language) {
        return GenreResponseDto.builder()
                .id(genre.getId())
                .genre(genre.getGenre(language))
                .build();
    }


}
