package com.uade.ad.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String bookingCode;

    private Long userId;

    @ManyToOne
    private Movie movie;

    private Long hallId;

    private Date timetable;

    @ElementCollection
    private List<String> seats;
}
