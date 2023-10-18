package com.projetoaps.SistemaHotelaria.domain.room;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private int beds;

    @ElementCollection
    @Column(nullable = false)
    private List<String> photos;
}