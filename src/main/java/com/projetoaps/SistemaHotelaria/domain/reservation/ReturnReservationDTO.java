package com.projetoaps.SistemaHotelaria.domain.reservation;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReturnReservationDTO(
        UUID id,
        LocalDateTime checkInDate,
        LocalDateTime checkOutDate,
        double totalCost,
        String userId,
        String roomId
) {
}
