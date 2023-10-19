package com.projetoaps.SistemaHotelaria.controller;

import com.projetoaps.SistemaHotelaria.domain.StockItem.StockItem;
import com.projetoaps.SistemaHotelaria.domain.StockItem.StockItemRepository;
import com.projetoaps.SistemaHotelaria.domain.reservation.Reservation;
import com.projetoaps.SistemaHotelaria.domain.reservation.ReservationDTO;
import com.projetoaps.SistemaHotelaria.domain.reservation.ReservationRepository;
import com.projetoaps.SistemaHotelaria.domain.reservation.ReturnReservationDTO;
import com.projetoaps.SistemaHotelaria.domain.room.Room;
import com.projetoaps.SistemaHotelaria.domain.room.RoomRepository;
import com.projetoaps.SistemaHotelaria.domain.user.User;
import com.projetoaps.SistemaHotelaria.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private StockItem item;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StockItemRepository stockItemRepository;

    @PostMapping("/create")
    public ResponseEntity save(@RequestBody @Valid ReservationDTO reservationDTO) {
        Optional<User> optionalUser = userRepository.findById(UUID.fromString(reservationDTO.userId()));

        if (optionalUser.isPresent()) {

            Optional<Room> optionalRoom = roomRepository.findById(UUID.fromString(reservationDTO.roomId()));

            if (optionalRoom.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quarto não encontrado");
            }

            Reservation reservation = new Reservation();

            reservation.setTotalCost(reservationDTO.totalCost());
            reservation.setCheckInDate(reservationDTO.checkInDate());
            reservation.setCheckOutDate(reservationDTO.checkOutDate());
            reservation.setUser(optionalUser.get());
            reservation.setRoom(optionalRoom.get());

            reservationRepository.save(reservation);

            return ResponseEntity.status(HttpStatus.CREATED).body("Reserva realizada com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReturnReservationDTO>> findAll() {
        var reservations = reservationRepository.findAll();
        List<ReturnReservationDTO> reservationDTOS = new ArrayList<>();
        reservations.forEach(r -> reservationDTOS.add(new ReturnReservationDTO(
                r.getReservationID(),
                r.getCheckInDate(),
                r.getCheckOutDate(),
                r.getTotalCost(),
                r.getUser().getId().toString(),
                r.getRoom().getId().toString())));

        return ResponseEntity.ok(reservationDTOS);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity update(@RequestBody @Valid ReservationDTO reservationDTO, @PathVariable UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {

            Optional<Room> optionalRoom = roomRepository.findById(UUID.fromString(reservationDTO.roomId()));

            if (optionalRoom.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quarto não encontrado");
            }

            Reservation reservation = new Reservation();

            reservation.setReservationID(id);
            reservation.setCheckInDate(reservationDTO.checkInDate());
            reservation.setCheckOutDate(reservationDTO.checkOutDate());
            reservation.setTotalCost(reservationDTO.totalCost());
            reservation.setRoom(optionalRoom.get());
            reservation.setUser(optionalUser.get());

            reservationRepository.save(reservation);

            return ResponseEntity.status(HttpStatus.CREATED).body("Reserva realizada com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable UUID id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();

            reservationRepository.delete(reservation);
            return ResponseEntity.status(HttpStatus.OK).body("Reserva deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva não encontrado, digite um existente, por favor");
        }
    }
}
