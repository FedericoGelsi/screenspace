package com.uade.ad.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaUpdateDto {
    @NotBlank
    private Long id;
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
