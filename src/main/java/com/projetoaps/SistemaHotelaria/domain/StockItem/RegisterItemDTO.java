package com.projetoaps.SistemaHotelaria.domain.StockItem;

public record RegisterItemDTO(
        String name,
        int quantity,
        int minStockLevel
) {
}
