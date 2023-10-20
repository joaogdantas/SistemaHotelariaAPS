package com.projetoaps.SistemaHotelaria.domain.user;

import java.util.Optional;
import java.util.UUID;

public record ReturnUsersDTO(
        UUID id,
        String login,
        UserRole role
) {
    public ReturnUsersDTO(User user){
        this(
                user.getId(),
                user.getLogin(),
                user.getRole()
        );
    }
}
