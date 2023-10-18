package com.projetoaps.SistemaHotelaria.domain.reservation;

import com.projetoaps.SistemaHotelaria.domain.additionalservices.AdditionalServices;
import com.projetoaps.SistemaHotelaria.domain.room.Room;
import com.projetoaps.SistemaHotelaria.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID reservationID;

    @Column(nullable = false)
    private LocalDateTime checkInDate;

    @Column(nullable = false)
    private LocalDateTime checkOutDate;

    @Column(nullable = false)
    private double totalCost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<AdditionalServices> additionalServices;
}