package com.uade.ad.service;

import com.uade.ad.controller.dto.BookingRequestDto;
import com.uade.ad.controller.dto.BookingResponseDto;
import com.uade.ad.model.Booking;
import com.uade.ad.model.Cinema;
import com.uade.ad.model.CinemaShow;
import com.uade.ad.model.Hall;
import com.uade.ad.repository.BookingRepository;
import com.uade.ad.repository.CinemaShowRepository;
import com.uade.ad.repository.HallRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final HallRepository hallRepository;

    public BookingService(CinemaShowRepository cinemaShowRepository,
                          BookingRepository bookingRepository, HallRepository hallRepository) {
        this.cinemaShowRepository = cinemaShowRepository;
        this.bookingRepository = bookingRepository;
        this.hallRepository = hallRepository;
    }

    public List<BookingResponseDto> getBookingById(Integer userId) {
        List<BookingResponseDto> result = new ArrayList<>();
       List<Booking> bookings = bookingRepository.findAllByUserId(userId);
        for (Booking book: bookings) {
            Optional<Hall> hall = hallRepository.findById(book.getHallId());
            Cinema cinema = hall.get().getCinema();
            Integer price =  cinema.getPricePerShow();
            String name = cinema.getCinemaName();
            BookingResponseDto dto = BookingResponseDto.builder()
                    .id(book.getId())
                    .bookingCode(book.getBookingCode())
                    .userId(book.getUserId())
                    .movie(book.getMovie())
                    .hallId(book.getHallId())
                    .timetable(book.getTimetable())
                    .seats(book.getSeats())
                    .cinemaName(name)
                    .pricePerSeat(price)
                    .build();
            result.add(dto);
        }
        return result;
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
