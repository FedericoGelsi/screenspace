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
    private String calle;
    private String numero;
    private String Barrio;
    private String localidad;
    private String provincia;
    private String pais;
    private Long ownedId;
    @OneToMany(mappedBy = "cinema" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Hall> halls;
}
