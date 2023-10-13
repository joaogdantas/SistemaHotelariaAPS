package com.projetoaps.SistemaHotelaria.domain.room;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private boolean availability;

    @ElementCollection
    @Column(nullable = false)
    private List<String> amenities;

    @ElementCollection
    @Column(nullable = false)
    private List<String> photos;
}