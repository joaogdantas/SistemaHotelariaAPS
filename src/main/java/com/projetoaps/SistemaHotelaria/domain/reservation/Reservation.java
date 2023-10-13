package com.projetoaps.SistemaHotelaria.domain.reservation;

import com.projetoaps.SistemaHotelaria.domain.customer.Customer;
import com.projetoaps.SistemaHotelaria.domain.receptionist.Receptionist;
import com.projetoaps.SistemaHotelaria.domain.room.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private String reservationID;

    @Column(nullable = false)
    private LocalDateTime checkInDate;

    @Column(nullable = false)
    private LocalDateTime checkOutDate;

    @ElementCollection
    @Column(nullable = false)
    private List<String> additionalServices;

    @Column(nullable = false)
    private double totalCost;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="receptionist_id", nullable=false)
    private Receptionist receptionist;
}