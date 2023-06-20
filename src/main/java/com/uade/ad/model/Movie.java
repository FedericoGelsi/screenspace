package com.uade.ad.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie {
    @Id
    private Long id;
    private String title;
    private Long duration;
    private String imageUrl;
    @OneToMany
    private List<Genre> genres;
    private String synopsis;
    private Long rating;
    private boolean isShowing;
    private Date releaseDate;
}
