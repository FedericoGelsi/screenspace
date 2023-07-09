package com.uade.ad.controller.dto;

import com.uade.ad.model.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class BookingResponseDto {
    private Integer id;
    private String bookingCode;
    private Long userId;
    private Movie movie;
    private Long hallId;
    private Date timetable;
    private List<String> seats;
    private String cinemaName;
    private Integer pricePerSeat;
}
