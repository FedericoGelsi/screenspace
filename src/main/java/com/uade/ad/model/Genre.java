package com.uade.ad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String genre;
    private String genreSpanish;
    @ManyToMany
    @JsonIgnore
    private List<Movie> movies;

    public String getGenre(final String language) {
        return "en".equals(language) ? genre : genreSpanish;
    }
}
