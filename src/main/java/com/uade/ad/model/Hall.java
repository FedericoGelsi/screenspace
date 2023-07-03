package com.uade.ad.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int height;
    private int width;
    private boolean available;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonBackReference
    private Cinema cinema;
    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<CinemaShow> cinemaShows;

    public Hall toDto(){
        Hall hall = new Hall();
        hall.setId(this.id);
        hall.setName(this.name);
        hall.setHeight(this.height);
        hall.setWidth(this.width);
        hall.setAvailable(this.available);
        return hall;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", available=" + available +
                '}';
    }

    public List<Movie> getMoviesInTheaters() {
        List<Movie> movies = new ArrayList<>();
        for (CinemaShow cinemaShow : cinemaShows) {
            movies.add(cinemaShow.getMovie());
        }
        return movies;
    }

    public boolean containsMovieInTheather(Integer movieId) {
        boolean isMovieInTheater = false;
        for (CinemaShow cinemaShow : cinemaShows) {
            if(Objects.equals(cinemaShow.getMovie().getId(), movieId)) {
                isMovieInTheater = true;
                break;
            }
        }
        return isMovieInTheater && available;
    }
}
