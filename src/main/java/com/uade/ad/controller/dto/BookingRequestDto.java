package com.uade.ad.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {
    private Long userId;
    private Long showId;
    private Date timetable;
    private List<String> seats;
}
