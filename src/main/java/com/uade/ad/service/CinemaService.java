package com.uade.ad.service;

import com.uade.ad.model.Cinema;
import com.uade.ad.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CinemaService {

    private final CinemaRepository cinemaRepository;
    @Autowired
    public CinemaService(CinemaRepository cinemaRepository){

        this.cinemaRepository = cinemaRepository;
    }

    public List<Cinema> getAll(Long movieId, Long ownerId) {
        if (ownerId != null) {
            return cinemaRepository.findAllByOwnedId(ownerId);
        }

        if (movieId != null) {
            List<Cinema> cinemas = cinemaRepository.findAll();
            return cinemas.stream()
                    .filter(cinema -> cinema.getHalls().stream()
                            .flatMap(hall -> hall.getShows().stream())
                            .anyMatch(show -> Objects.equals(show.getMovieId(), movieId)))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
