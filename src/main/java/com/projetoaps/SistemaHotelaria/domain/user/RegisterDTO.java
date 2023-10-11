package com.projetoaps.SistemaHotelaria.domain.user;

public record RegisterDTO(
        String login,
        String password,
        UserRole role
) {
}
