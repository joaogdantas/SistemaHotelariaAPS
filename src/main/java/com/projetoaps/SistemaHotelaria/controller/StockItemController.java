package com.projetoaps.SistemaHotelaria.controller;

import com.projetoaps.SistemaHotelaria.domain.StockItem.*;
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

    @PutMapping("order/{id}/{quantity}")
    @Transactional
    public ResponseEntity makeOrder(@PathVariable UUID id, @PathVariable int quantity){
        Optional<StockItem> existentItem = itemRepository.findById(id);

        if (existentItem.isPresent()) {
            StockItem findedItem = existentItem.get();

            findedItem.setQuantity(findedItem.getQuantity() - quantity);

            findedItem.reorderItem();

            itemRepository.save(findedItem);

            return ResponseEntity.status(HttpStatus.OK).body("Estoque do item atualizado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esse item não existe, insira um existente, por favor.");
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
