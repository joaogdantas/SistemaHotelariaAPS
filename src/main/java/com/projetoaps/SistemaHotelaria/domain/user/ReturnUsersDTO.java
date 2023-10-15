package com.projetoaps.SistemaHotelaria.domain.user;

import java.util.UUID;

public record ReturnUsersDTO(
        UUID id,
        String login,
        UserRole role
) {
}
