package com.uade.ad.controller;

import com.uade.ad.controller.dto.BookingRequestDto;
import com.uade.ad.controller.dto.BookingResponseDto;
import com.uade.ad.model.Booking;
import com.uade.ad.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer userId) {
        List<BookingResponseDto> bookings = bookingService.getBookingById(userId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        try {
            Optional<Booking> bookingOptional = bookingService.createBooking(bookingRequestDto);

            if(bookingOptional.isPresent()) {
                return new ResponseEntity<>(bookingOptional.get(), HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not reserve seats");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating booking: " + e.getMessage());
        }
    }
}

