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
    private String name;
    private int height;
    private int width;
    private boolean available;
    @ManyToOne
    private Cinema cinema;
    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Show> shows;

    public Hall toDto(){
        Hall hall = new Hall();
        hall.setId(this.id);
        hall.setName(this.name);
        hall.setHeight(this.height);
        hall.setWidth(this.width);
        hall.setAvailable(this.available);
        return hall;
    }
}
