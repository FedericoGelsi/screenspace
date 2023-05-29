package com.uade.ad.controller;

import com.uade.ad.controller.dto.CinemaUpdateDto;
import com.uade.ad.model.Cinema;
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
    public ResponseEntity<?> getAllCinemas(@RequestParam Long movieId,@RequestParam Long ownerID){
        List<Cinema> cinemas = cinemaService.getAll(movieId,ownerID);
        if(cinemas.isEmpty()) return new ResponseEntity<>("Cinemas not found.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(cinemas,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateCinema(@RequestBody CinemaUpdateDto cinemaDTO) {

        Optional<Cinema> existingCinema = cinemaService.findById(cinemaDTO.getId());
        if (existingCinema.isEmpty()) {
            return new ResponseEntity<>("Cinema not found.", HttpStatus.NOT_FOUND);
        }

        Cinema updatedCinema = cinemaService.updateCinema(cinemaDTO,existingCinema.get());
        return new ResponseEntity<>(updatedCinema,HttpStatus.OK);
    }

}
