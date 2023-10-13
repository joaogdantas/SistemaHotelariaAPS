package com.projetoaps.SistemaHotelaria.domain.receptionist;

import com.projetoaps.SistemaHotelaria.domain.reservation.Reservation;
import com.projetoaps.SistemaHotelaria.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "receptionists")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Receptionist extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private String receptionistId;

    @OneToMany(mappedBy = "receptionist", cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Reservation> reservations;
}