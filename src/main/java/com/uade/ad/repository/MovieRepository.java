package com.uade.ad.repository;

import com.uade.ad.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findMoviesByTitleContainingIgnoreCase(final String title);

    List<Movie> findMoviesByRatingGreaterThanEqual(final double rating);
}
