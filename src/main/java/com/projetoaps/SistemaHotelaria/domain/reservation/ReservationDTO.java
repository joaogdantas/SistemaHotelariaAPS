package com.projetoaps.SistemaHotelaria.domain.reservation;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationDTO(
        LocalDateTime checkInDate,
        LocalDateTime checkOutDate,
        double totalCost,
        String userId,
        String roomId,
        List<String> additionalServices
) {
}
