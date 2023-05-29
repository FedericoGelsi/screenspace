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
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int height;
    private int lenght;
    private boolean available;
    @ManyToOne
    private Cinema cinema;
    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Show> shows;
}
