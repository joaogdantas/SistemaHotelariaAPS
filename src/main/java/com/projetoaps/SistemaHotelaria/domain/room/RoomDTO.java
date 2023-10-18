package com.projetoaps.SistemaHotelaria.domain.room;

import java.util.List;

public record RoomDTO(String id, String roomNumber, int beds, List<String> photos
) {

}
