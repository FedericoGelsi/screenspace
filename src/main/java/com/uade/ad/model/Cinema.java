    package com.uade.ad.model;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
    import lombok.*;
    import org.hibernate.annotations.Fetch;
    import org.hibernate.annotations.FetchMode;

    import java.util.ArrayList;
    import java.util.List;

    @Data
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    public class Cinema {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String cinemaName;
        private String companyName;
        private String address;
        private String postalCode;
        private String city;
        private String province;
        private String country;
        private Double latitude;
        private Double longitude;
        private Integer pricePerShow;
        private boolean active;
        private Long ownerId;
        @OneToMany(mappedBy = "cinema" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @Fetch(FetchMode.SUBSELECT)
        @JsonManagedReference
        private List<Hall> halls;

        public Cinema toDto() {
            Cinema cinema = new Cinema();
            cinema.setId(this.id);
            cinema.setOwnerId(this.ownerId);
            cinema.setCinemaName(this.cinemaName);
            cinema.setAddress(this.address);
            cinema.setPostalCode(this.postalCode);
            cinema.setCity(this.city);
            cinema.setProvince(this.province);
            cinema.setCountry(this.country);
            return cinema;
        }

        @JsonIgnore
        public List<Movie> getMoviesInTheaters() {
            List<Movie> movies = new ArrayList<>();
            for(Hall hall : halls) {
                movies.addAll(hall.getMoviesInTheaters());
            }
            return movies;
        }

        public boolean containsMovieInTheather(final Integer movieId) {
            boolean isMovieInTheater = false;
            for(Hall hall : halls) {
                if(hall.containsMovieInTheather(movieId)) {
                    isMovieInTheater = true;
                    break;
                }
            }
            return isMovieInTheater && active;
        }
    }
