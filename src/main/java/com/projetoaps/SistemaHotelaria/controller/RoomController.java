package com.projetoaps.SistemaHotelaria.controller;

import com.projetoaps.SistemaHotelaria.domain.room.CreateRoomDTO;
import com.projetoaps.SistemaHotelaria.domain.room.Room;
import com.projetoaps.SistemaHotelaria.domain.room.RoomDTO;
import com.projetoaps.SistemaHotelaria.domain.room.RoomRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @PostMapping("/create")
    public ResponseEntity save(@RequestBody @Valid CreateRoomDTO roomDTO) {

        Optional<Room> optionalRoom = roomRepository.findByRoomNumber(roomDTO.roomNumber());

        if(optionalRoom.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("Já existe um quarto com esse número, por favor escolha um diferente");
        }

        Room room = new Room();
        room.setRoomNumber(roomDTO.roomNumber());
        room.setBeds(roomDTO.beds());
        room.setPhotos(roomDTO.photos());

        roomRepository.save(room);

        return ResponseEntity.status(HttpStatus.CREATED).body("Quarto criado com sucesso");
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomDTO>> findAllItens() {
        var rooms = roomRepository.findAll();
        List<RoomDTO> roomDTOS = new ArrayList<>();
        rooms.forEach(r -> roomDTOS.add(new RoomDTO(r.getId().toString(), r.getRoomNumber(), r.getBeds(), r.getPhotos())));
        return ResponseEntity.ok(roomDTOS);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateItem(@RequestBody @Valid CreateRoomDTO data, @PathVariable UUID id) {
        Optional<Room> existentRoom = roomRepository.findById(id);

        if (existentRoom.isPresent()) {
            Room room = existentRoom.get();
            room.setRoomNumber(data.roomNumber());
            room.setBeds(data.beds());
            room.setPhotos(data.photos());

            roomRepository.save(room);

            return ResponseEntity.status(HttpStatus.OK).body("Quarto atualizado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quarto não existe, insira um existente, por favor.");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletePost(@PathVariable UUID id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if (optionalRoom.isPresent()) {
            Room deletedRoom = optionalRoom.get();

            roomRepository.delete(deletedRoom);
            return ResponseEntity.status(HttpStatus.OK).body("Quarto deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quarto não encontrado, digite um existente, por favor");
        }
    }
}
