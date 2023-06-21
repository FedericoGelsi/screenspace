package com.uade.ad.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaCreateDto {
    private Long userId;
    private String cinemaName;
    private String companyName;
    private String address;
    private String postalCode;
    private String city;
    private String province;
    private String country;
    private Integer pricePerShow;
    private boolean active;
}
