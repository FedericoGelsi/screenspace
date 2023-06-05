    package com.uade.ad.model;

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
    public class Cinema {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String company;
        private String calle;
        private String numero;
        private String barrio;
        private String localidad;
        private String provincia;
        private String pais;
        private String latitude;
        private String longitude;
        private Integer seatCosts;
        private boolean available;
        private Long ownedId;
        @OneToMany(mappedBy = "cinema" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private List<Hall> halls;

        public Cinema toDto() {
            Cinema cinema = new Cinema();
            cinema.setId(this.id);
            cinema.setName(this.name);
            cinema.setCalle(this.calle);
            cinema.setNumero(this.numero);
            cinema.setLocalidad(this.localidad);
            cinema.setProvincia(this.provincia);
            cinema.setPais(this.pais);
            return cinema;
        }
    }
