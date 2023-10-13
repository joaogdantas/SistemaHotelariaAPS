package com.projetoaps.SistemaHotelaria.domain.customer;

import com.projetoaps.SistemaHotelaria.domain.reservation.Reservation;
import com.projetoaps.SistemaHotelaria.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private String customerID;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Reservation> reservations;
}