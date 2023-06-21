package com.uade.ad.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private double duration;
    private String imageUrl;
    @ManyToMany
    private Set<Genre> genres;
    private String synopsis;
    private double rating;
    private boolean isShowing;
    private Date releaseDate;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Review> reviews;
}
