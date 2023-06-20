package com.uade.ad.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Movie movie;
    private Date datetime;
    private String name;
    @ManyToOne
    private Hall hall;
    @ElementCollection
    private ArrayList<String> availableSeats;

    /*
    * Por lo que vi podemos guardar todo como una cadena de texto
    * cuando se crea un show el hall le pasa sus dimensiones para crear los asientos
    * entonces de entrada estan todos disponibles, despues takeSeat lo eliminamos cuando
    * se hace una reserva
    * */
    public void initSeats(int height, int width) {
        this.availableSeats = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char fila = (char) ('A' + i);
                int numero = j + 1;
                String seat = fila + String.valueOf(numero);
                this.availableSeats.add(seat);
            }
        }
    }
    public void takeSeat(String seat) {
        this.availableSeats.remove(seat);
    }

    public Show toDto(){
        return Show.builder()
                .id(this.id)
                .movie(this.movie)
                .name(this.name)
                .datetime(this.datetime)
                .build();
    }
}

