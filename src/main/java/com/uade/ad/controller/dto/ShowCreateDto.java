package com.uade.ad.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShowCreateDto {
    private Long movieId;
    private Date datetime;
    private String name;
}
