package com.uade.ad.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GenreResponseDto {
    private Integer id;
    private String genre;
}
