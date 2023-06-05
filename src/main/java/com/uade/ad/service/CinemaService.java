package com.uade.ad.service;

import com.uade.ad.controller.dto.CinemaCreateDto;
import com.uade.ad.controller.dto.CinemaUpdateDto;
import com.uade.ad.controller.dto.HallCreateDto;
import com.uade.ad.model.Cinema;
import com.uade.ad.model.Hall;
import com.uade.ad.repository.CinemaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CinemaService {

    private final CinemaRepository cinemaRepository;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository) {

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

    public Optional<Cinema> findById(Long id) {
        return cinemaRepository.findById(id);
    }

    public Cinema updateCinema(CinemaUpdateDto cinemaDTO, Cinema existingCinema) {
        BeanUtils.copyProperties(cinemaDTO, existingCinema, "id");
        return cinemaRepository.save(existingCinema);
    }

    public boolean deleteCinemaById(Long id) {
        Optional<Cinema> cinema = cinemaRepository.findById(id);
        if (cinema.isPresent()) {
            cinemaRepository.delete(cinema.get());
            return true;
        }
        return false;
    }

    public Cinema createCinema(Long userId, CinemaCreateDto cinemaDto) {
        Cinema newCinema = Cinema
                .builder()
                .ownedId(userId)
                .name(cinemaDto.getName())
                .company(cinemaDto.getCompany())
                .calle(cinemaDto.getCalle())
                .numero(cinemaDto.getNumero())
                .barrio(cinemaDto.getBarrio())
                .localidad(cinemaDto.getLocalidad())
                .provincia(cinemaDto.getProvincia())
                .pais(cinemaDto.getPais())
                .latitude(cinemaDto.getLatitude())
                .longitude(cinemaDto.getLongitude())
                .seatCosts(cinemaDto.getSeatCosts())
                .available(cinemaDto.isAvailable())
                .build();
        return cinemaRepository.save(newCinema);
    }

    public Hall createHall(Long cinemaId, HallCreateDto hallDto) throws Exception {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new Exception("Cinema not found."));

        List<Hall> halls = cinema.getHalls();
        Hall newHall = Hall
                .builder()
                .name(hallDto.getName())
                .width(hallDto.getWidth())
                .height(hallDto.getHeight())
                .available(hallDto.isAvailable())
                .build();

        halls.add(newHall);
        cinemaRepository.save(cinema);

        return newHall;
    }
}
