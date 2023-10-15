package com.projetoaps.SistemaHotelaria.domain.StockItem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "item")
@Table(name = "itens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    private String name;

    private int quantity;

    private int minStockLevel;

    public void reorderItem(){
        if(quantity < minStockLevel){
            quantity += 10;
        }
    }
}
