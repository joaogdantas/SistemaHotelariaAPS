package com.projetoaps.SistemaHotelaria.domain.Task;

import java.util.UUID;

public record CreateTaskDTO(
        String description,
        TaskType type,
        UUID userId
) {
}
