package com.uade.ad.service;

import com.google.maps.model.LatLng;
import com.uade.ad.controller.dto.CinemaCreateDto;
import com.uade.ad.controller.dto.CinemaUpdateDto;
import com.uade.ad.controller.dto.HallCreateDto;
import com.uade.ad.controller.dto.ShowCreateDto;
import com.uade.ad.model.Cinema;
import com.uade.ad.model.Hall;
import com.uade.ad.model.CinemaShow;
import com.uade.ad.model.Movie;
import com.uade.ad.repository.CinemaRepository;
import com.uade.ad.repository.CinemaShowRepository;
import com.uade.ad.repository.HallRepository;
import com.uade.ad.repository.MovieRepository;
import jakarta.transaction.Transactional;
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
    private final HallRepository hallRepository;
    private final CinemaShowRepository cinemaShowRepository;
    private final MovieRepository movieRepository;
    private final MapService mapService;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository,
                         MapService mapService,
                         HallRepository hallRepository,
                         CinemaShowRepository cinemaShowRepository,
                         MovieRepository movieRepository) {
        this.cinemaRepository = cinemaRepository;
        this.mapService = mapService;
        this.hallRepository = hallRepository;
        this.cinemaShowRepository = cinemaShowRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public List<Cinema> getAll(Integer movieId, Long ownerId) {
        if (ownerId != null) {
            return cinemaRepository.findAllByOwnerId(ownerId);
        }

        if (movieId != null) {
            List<Cinema> cinemas = cinemaRepository.findAll();
            return cinemas.stream()
                    .filter(cinema -> cinema.getHalls().stream()
                            .flatMap(hall -> hall.getCinemaShows().stream())
                            .anyMatch(cinemaShow -> Objects.equals(cinemaShow.getMovie().getId(), movieId)))
                    .collect(Collectors.toList());
        }
        return cinemaRepository.findAll();
    }

    public Optional<Cinema> findById(Long id) {
        return cinemaRepository.findById(id);
    }

    @Transactional
    public Cinema updateCinema(CinemaUpdateDto cinemaDTO, Cinema existingCinema) {
        LatLng coordinates = mapService.getLocationFromAddress(cinemaDTO.getAddress(), cinemaDTO.getPostalCode(), cinemaDTO.getCity(),
                cinemaDTO.getProvince(), cinemaDTO.getCountry());
        BeanUtils.copyProperties(cinemaDTO, existingCinema, "id");
        existingCinema.setLatitude(coordinates.lat);
        existingCinema.setLongitude(coordinates.lng);
        return cinemaRepository.save(existingCinema);
    }

    @Transactional
    public boolean deleteCinemaById(Long id) {
        Optional<Cinema> cinema = cinemaRepository.findById(id);
        if (cinema.isPresent()) {
            cinemaRepository.delete(cinema.get());
            return true;
        }
        return false;
    }

    @Transactional
    public Cinema createCinema(CinemaCreateDto cinemaDto) {
        LatLng coordinates = mapService.getLocationFromAddress(cinemaDto.getAddress(), cinemaDto.getPostalCode(), cinemaDto.getCity(),
                cinemaDto.getProvince(), cinemaDto.getCountry());
        Cinema newCinema = Cinema
                .builder()
                .ownerId(cinemaDto.getUserId())
                .cinemaName(cinemaDto.getCinemaName())
                .companyName(cinemaDto.getCompanyName())
                .address(cinemaDto.getAddress())
                .postalCode(cinemaDto.getPostalCode())
                .city(cinemaDto.getCity())
                .province(cinemaDto.getProvince())
                .country(cinemaDto.getCountry())
                .latitude(coordinates.lat)
                .longitude(coordinates.lng)
                .pricePerShow(cinemaDto.getPricePerShow())
                .active(cinemaDto.isActive())
                .build();
        return cinemaRepository.save(newCinema);
    }

    @Transactional
    public Hall createHall(Long cinemaId, HallCreateDto hallDto) throws Exception {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new Exception("Cinema not found."));

        Hall newHall = Hall
                .builder()
                .name(hallDto.getName())
                .width(hallDto.getWidth())
                .height(hallDto.getHeight())
                .available(hallDto.isAvailable())
                .cinema(cinema)
                .build();

        hallRepository.save(newHall);

        cinema.getHalls().add(newHall);
        cinemaRepository.save(cinema);

        return newHall;
    }

    @Transactional
    public Hall updateHall(Long cinemaId, Long hallId, HallCreateDto hallDto) throws Exception {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new Exception("Cinema not found."));

        Hall hallToUpdate = cinema.getHalls().stream()
                .filter(x -> Objects.equals(x.getId(), hallId)).findFirst()
                .orElseThrow(() -> new Exception("Hall not found."));

        BeanUtils.copyProperties(hallDto, hallToUpdate, "id");
        hallRepository.save(hallToUpdate);
        cinemaRepository.save(cinema);
        return hallToUpdate;
    }

    @Transactional
    public boolean deleteHall(Long cinemaId, Long hallId) throws Exception {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new Exception("Cinema not found."));

        boolean isDeletedFromCinema = cinema.getHalls().removeIf(hall -> Objects.equals(hall.getId(), hallId));
        Long deletedHallId = hallRepository.deleteHallById(hallId);

        // cinemaRepository.save(cinema);

        return isDeletedFromCinema && deletedHallId > 0;
    }

    @Transactional
    public CinemaShow createShow(Long cinemaId, Long hallId, ShowCreateDto showDto) throws Exception {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new Exception("Cinema not found."));

        Hall hall = cinema.getHalls().stream()
                .filter(x -> Objects.equals(x.getId(), hallId)).findFirst()
                .orElseThrow(() -> new Exception("Hall not found."));

        Optional<Movie> movie = movieRepository.findById(showDto.getMovieId().intValue());

        if (movie.isEmpty()) throw new Exception("Movie not found.");

        CinemaShow newCinemaShow = CinemaShow.builder()
                .movie(movie.get())
                .name(showDto.getName())
                .hall(hall)
                .datetime(showDto.getDatetime())
                .build();
        newCinemaShow.initSeats(hall.getHeight(),hall.getWidth());

        hall.getCinemaShows().add(newCinemaShow);
        cinemaShowRepository.save(newCinemaShow);
        cinemaRepository.save(cinema);
        return newCinemaShow;
    }

    @Transactional
    public CinemaShow updateCinemaShow(Long cinemaId, Long hallId, Long showId, ShowCreateDto showDto) throws Exception {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new Exception("Cinema not found."));

        CinemaShow showToUpdate = cinema.getHalls().stream()
                .filter(x -> Objects.equals(x.getId(), hallId)).findFirst()
                .orElseThrow(() -> new Exception("Hall not found."))
                .getCinemaShows().stream()
                .filter(x -> Objects.equals(x.getId(), showId)).findFirst()
                .orElseThrow(() -> new Exception("Show not found"));

        Movie movie = movieRepository.findById(showDto.getMovieId().intValue())
                .orElseThrow(() -> new Exception("Movie not found"));

        BeanUtils.copyProperties(showDto, showToUpdate, "id");
        showToUpdate.setMovie(movie);
        cinemaShowRepository.save(showToUpdate);
        cinemaRepository.save(cinema);
        return showToUpdate;
    }

    @Transactional
    public boolean deleteShow(Long cinemaId, Long hallId, Long showId) throws Exception {
        boolean isDeletedFromHall = false;

        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new Exception("Cinema not found."));

        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new Exception("Hall not found."));

        isDeletedFromHall = hall.getCinemaShows().removeIf(show -> Objects.equals(show.getId(), showId));
        Long deletedCinemaShowById = cinemaShowRepository.deleteCinemaShowById(showId);

        return isDeletedFromHall && deletedCinemaShowById > 0;

    }
}