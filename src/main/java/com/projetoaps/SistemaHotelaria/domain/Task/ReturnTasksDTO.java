package com.projetoaps.SistemaHotelaria.domain.Task;

import java.util.UUID;

public record ReturnTasksDTO(
        UUID id,
        String description,
        TaskType type,
        Boolean done,
        String username
) {
}
