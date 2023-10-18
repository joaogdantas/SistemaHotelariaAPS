package com.projetoaps.SistemaHotelaria.domain.Task;

import java.util.UUID;

public record ReturnEmployeesTaskListDTO(
        UUID id,
        String description,
        TaskType type,
        Boolean done
) {
}
