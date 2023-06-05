package com.uade.ad.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HallCreateDto {
    private String name;
    private Integer width;
    private Integer height;
    private boolean available;
}
