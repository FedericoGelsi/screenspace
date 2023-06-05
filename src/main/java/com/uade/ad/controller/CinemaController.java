package com.uade.ad.controller;

import com.uade.ad.controller.dto.CinemaCreateDto;
import com.uade.ad.controller.dto.CinemaUpdateDto;
import com.uade.ad.controller.dto.HallCreateDto;
import com.uade.ad.model.Cinema;
import com.uade.ad.model.Hall;
import com.uade.ad.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cinemas")
public class CinemaController {

    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCinemas(@RequestParam Long movieId, @RequestParam Long ownerID) {
        List<Cinema> cinemas = cinemaService.getAll(movieId, ownerID);
        if (cinemas.isEmpty()) return new ResponseEntity<>("Cinemas not found.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(cinemas, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateCinema(@RequestBody CinemaUpdateDto cinemaDTO) {

        Optional<Cinema> existingCinema = cinemaService.findById(cinemaDTO.getId());
        if (existingCinema.isEmpty()) {
            return new ResponseEntity<>("Cinema not found.", HttpStatus.NOT_FOUND);
        }

        Cinema updatedCinema = cinemaService.updateCinema(cinemaDTO, existingCinema.get());
        return new ResponseEntity<>(updatedCinema, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCinemaById(@PathVariable("id") Long id) {
        Optional<Cinema> cinema = cinemaService.findById(id);
        if (cinema.isEmpty()) return new ResponseEntity<>("Cinema not found.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(cinema, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCinemaById(@PathVariable("id") Long id) {
        boolean deleted = cinemaService.deleteCinemaById(id);
        if (deleted) return new ResponseEntity<>("Cinema not found.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Cinema successfully deleted!", HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> createCinema(@PathVariable("userId") Long userId, @RequestBody CinemaCreateDto cinemaDto) {
        try {
            Cinema createdCinema = cinemaService.createCinema(userId, cinemaDto);
            return new ResponseEntity<>(createdCinema.toDto(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating cinema: " + e.getMessage());
        }
    }

    @PostMapping("/{cinemaId}/halls")
    public ResponseEntity<?> createHall(@PathVariable("cinemaId") Long cinemaId, @RequestBody HallCreateDto hallDto) {
        try {
            Hall createdHall = cinemaService.createHall(cinemaId, hallDto);
            return new ResponseEntity<>(createdHall.toDto(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating hall: " + e.getMessage());
        }
    }

    @PutMapping("/{cinemaId}/{hallId}")
    public ResponseEntity<?> updateHall(@PathVariable("cinemaId") Long cinemaId, @PathVariable("hallId") Long hallId, @RequestBody HallCreateDto hallDto) {
        try {
            Hall updateHall = cinemaService.updateHall(cinemaId, hallId, hallDto);
            return new ResponseEntity<>(updateHall.toDto(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating hall: " + e.getMessage());
        }
    }

    @DeleteMapping("/{cinemaId}/{hallId}")
    public ResponseEntity<?> deleteHall(@PathVariable("cinemaId") Long cinemaId, @PathVariable("hallId") Long hallId) {
        try {
            boolean deleted = cinemaService.deleteHall(cinemaId, hallId);
            if (deleted) return new ResponseEntity<>("Hall not found.", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>("Hall successfully deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating hall: " + e.getMessage());
        }
    }
}
