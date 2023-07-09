package com.uade.ad.service;

import com.uade.ad.controller.dto.BookingRequestDto;
import com.uade.ad.model.Booking;
import com.uade.ad.model.CinemaShow;
import com.uade.ad.repository.BookingRepository;
import com.uade.ad.repository.CinemaShowRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookingService {

    private final int leftLimit = 65; // letter 'a'
    private final int rightLimit = 90; // letter 'z'
    private final int targetStringLength = 6;
    Random random = new Random();

    private final CinemaShowRepository cinemaShowRepository;
    private final BookingRepository bookingRepository;

    public BookingService(CinemaShowRepository cinemaShowRepository,
                          BookingRepository bookingRepository) {
        this.cinemaShowRepository = cinemaShowRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getBookingById(Integer userId) {
        return bookingRepository.findAllByUserId(userId);
    }

    @Transactional
    public Optional<Booking> createBooking(BookingRequestDto bookingRequestDto) throws Exception {
        CinemaShow cinemaShow = cinemaShowRepository.findCinemaShowById(bookingRequestDto.getShowId());

        boolean isErrorReserving = false;

        for(String seat : bookingRequestDto.getSeats()) {
            boolean removed = cinemaShow.takeSeat(seat);
            if(!removed) {
                isErrorReserving = true;
            }
        }

        if(!isErrorReserving) {
            cinemaShowRepository.save(cinemaShow);

            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            Booking booking = Booking.builder()
                    .bookingCode(generatedString)
                    .userId(bookingRequestDto.getUserId())
                    .movie(cinemaShow.getMovie())
                    .hallId(cinemaShow.getHall().getId())
                    .timetable(cinemaShow.getDatetime())
                    .seats(bookingRequestDto.getSeats())
                    .build();

            Booking savedBooking = bookingRepository.save(booking);

            return Optional.of(savedBooking);
        } else {
            return Optional.empty();
        }
    }
}
