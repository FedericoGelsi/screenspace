package com.uade.ad.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaCreateDto {
    private Long userId;
    private String name;
    private String company;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;
    private String pais;
    private Integer seatCosts;
    private boolean available;
}
