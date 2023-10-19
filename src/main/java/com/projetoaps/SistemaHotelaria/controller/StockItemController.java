package com.projetoaps.SistemaHotelaria.controller;

import com.projetoaps.SistemaHotelaria.domain.StockItem.*;
import com.projetoaps.SistemaHotelaria.domain.reservation.Reservation;
import com.projetoaps.SistemaHotelaria.domain.reservation.ReservationRepository;
import com.projetoaps.SistemaHotelaria.domain.user.User;
import com.projetoaps.SistemaHotelaria.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/item")
public class StockItemController {
    private StockItem item;
    @Autowired
    private StockItemRepository itemRepository;
    private Reservation reservation;
    @Autowired
    private ReservationRepository reservationRepository;

    @PostMapping("/create")
    public ResponseEntity registerItem(@RequestBody @Valid RegisterItemDTO data){
        StockItem newItem = new StockItem();

        newItem.setName(data.name());

        newItem.setQuantity(data.quantity());
        newItem.setMinStockLevel(data.minStockLevel());

        newItem.reorderItem();

        StockItem savedItem = itemRepository.save(newItem);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body("Item criado com sucesso!");
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReturnItensDTO>> findAllItens() {
        var itens = itemRepository.findAll();
        List<ReturnItensDTO> result = new ArrayList<>();
        itens.forEach(i -> result.add(new ReturnItensDTO(i.getId(), i.getName(), i.getQuantity(), i.getMinStockLevel())));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{reservationId}/all")
    public ResponseEntity<List<StockItem>> findAllReservationItens(@PathVariable UUID reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);

        if(optionalReservation.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).build();
        }

        Reservation findedReservation = optionalReservation.get();

        var reservationItens = findedReservation.getStockItems();
        return ResponseEntity.ok(reservationItens);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateItem(@RequestBody @Valid ManagerUpdateStockItemDTO data, @PathVariable UUID id){
        Optional<StockItem> existentItem = itemRepository.findById(id);

        if (existentItem.isPresent()) {
            StockItem findedItem = existentItem.get();
            findedItem.setQuantity(data.quantity());

            findedItem.reorderItem();

            itemRepository.save(findedItem);

            return ResponseEntity.status(HttpStatus.OK).body("Estoque do item atualizado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esse item não existe, insira um existente, por favor.");
    }

    @PutMapping("order/{reservationId}/{itemId}/{quantity}")
    @Transactional
    public ResponseEntity makeOrderInReservation(@PathVariable UUID reservationId, @PathVariable UUID itemId, @PathVariable int quantity) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);

        if (optionalReservation.isPresent()) {
            Optional<StockItem> existentItem = itemRepository.findById(itemId);

            Reservation findedReservation = optionalReservation.get();

            if (existentItem.isPresent()) {
                StockItem findedItem = existentItem.get();

                findedItem.setQuantity(findedItem.getQuantity() - quantity);

                itemRepository.save(findedItem);

                List<StockItem> reservationItems = findedReservation.getStockItems();

                reservationItems.add(findedItem);

                findedReservation.setStockItems(reservationItems);

                reservationRepository.save(findedReservation);

                return ResponseEntity.status(HttpStatus.OK).body("Pedido realizado com sucesso!");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esse item não existe, insira um existente, por favor.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva inválida, insira um existente, por favor.");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletePost(@PathVariable UUID id){

        Optional<StockItem> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            StockItem deletedItem = optionalItem.get();

            itemRepository.delete(deletedItem);

            return ResponseEntity.status(HttpStatus.OK).body("Item deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado, digite um existente, por favor");
        }
    }
}
