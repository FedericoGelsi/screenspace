package com.uade.ad.service;

import com.google.maps.model.LatLng;
import com.uade.ad.controller.dto.CinemaCreateDto;
import com.uade.ad.controller.dto.CinemaUpdateDto;
import com.uade.ad.controller.dto.HallCreateDto;
import com.uade.ad.controller.dto.ShowCreateDto;
import com.uade.ad.model.Cinema;
import com.uade.ad.model.Hall;
import com.uade.ad.model.Show;
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

    private final MapService mapService;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository, MapService mapService) {

        this.cinemaRepository = cinemaRepository;
        this.mapService = mapService;
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
        LatLng coordinates = mapService.getLocationFromAddress(cinemaDTO.getCalle(), cinemaDTO.getNumero(),
                cinemaDTO.getLocalidad(), cinemaDTO.getProvincia(), cinemaDTO.getPais());
        BeanUtils.copyProperties(cinemaDTO, existingCinema, "id");
        existingCinema.setLatitude(coordinates.lat);
        existingCinema.setLongitude(coordinates.lng);
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

    public Cinema createCinema(CinemaCreateDto cinemaDto) {
        LatLng coordinates = mapService.getLocationFromAddress(cinemaDto.getCalle(), cinemaDto.getNumero(),
                cinemaDto.getLocalidad(), cinemaDto.getProvincia(), cinemaDto.getPais());
        Cinema newCinema = Cinema
                .builder()
                .ownedId(cinemaDto.getUserId())
                .name(cinemaDto.getName())
                .company(cinemaDto.getCompany())
                .calle(cinemaDto.getCalle())
                .numero(cinemaDto.getNumero())
                .localidad(cinemaDto.getLocalidad())
                .provincia(cinemaDto.getProvincia())
                .pais(cinemaDto.getPais())
                .latitude(coordinates.lat)
                .longitude(coordinates.lng)
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

    public Hall updateHall(Long cinemaId, Long hallId, HallCreateDto hallDto) throws Exception {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new Exception("Cinema not found."));

        Hall hallToUpdate = cinema.getHalls().stream()
                .filter(x -> Objects.equals(x.getId(), hallId)).findFirst()
                .orElseThrow(() -> new Exception("Hall not found."));

        BeanUtils.copyProperties(hallDto, hallToUpdate, "id");
        cinemaRepository.save(cinema);
        return hallToUpdate;
    }

    public boolean deleteHall(Long cinemaId, Long hallId) throws Exception {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new Exception("Cinema not found."));

        boolean isDeleted = cinema.getHalls().removeIf(hall -> Objects.equals(hall.getId(), hallId));
        cinemaRepository.save(cinema);

        return isDeleted;
    }

    public Show createShow(Long cinemaId, Long hallId, ShowCreateDto showDto) throws Exception {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new Exception("Cinema not found."));

        Hall hall = cinema.getHalls().stream()
                .filter(x -> Objects.equals(x.getId(), hallId)).findFirst()
                .orElseThrow(() -> new Exception("Hall not found."));

        Show newShow = Show.builder()
                .movieId(showDto.getMovieId())
                .name(showDto.getName())
                .datetime(showDto.getDatetime())
                .build();
        newShow.initSeats(hall.getHeight(),hall.getWidth());

        hall.getShows().add(newShow);
        cinemaRepository.save(cinema);
        return newShow;
    }
}