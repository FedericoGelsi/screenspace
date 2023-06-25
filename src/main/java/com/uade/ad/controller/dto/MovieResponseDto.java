package com.uade.ad.controller.dto;

import com.uade.ad.model.Review;
import java.util.Date;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MovieResponseDto {
    private Integer id;
    private String title;
    private double duration;
    private String imageUrl;
    private Set<GenreResponseDto> genres;
    private String synopsis;
    private double rating;
    private boolean isShowing;
    private Date releaseDate;
    private Set<Review> reviews;
}
