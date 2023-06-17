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
    private String name;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;
    private String pais;

}
