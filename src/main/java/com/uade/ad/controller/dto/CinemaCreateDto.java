package com.uade.ad.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaCreateDto {
    private String name;
    private String company;
    private String calle;
    private String numero;
    private String barrio;
    private String localidad;
    private String provincia;
    private String pais;
    private String latitude;
    private String longitude;
    private Integer seatCosts;
    private boolean available;
}
