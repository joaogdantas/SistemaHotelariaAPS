package com.projetoaps.SistemaHotelaria.domain.StockItem;

import java.util.UUID;

public record ReturnItensDTO(
        UUID id,
        String name,
        int quantity,
        int minStockLevel
) {
}
