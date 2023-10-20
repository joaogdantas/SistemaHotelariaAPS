package com.projetoaps.SistemaHotelaria.domain.user;

import java.util.UUID;

public record LoginResponseWithUserDTO(
    String token,
    String login,
    UUID userId
){
    public LoginResponseWithUserDTO(String token, String login, UUID userId) {
        this.token = token;
        this.login = login;
        this.userId = userId;
    }
}
