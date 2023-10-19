package com.projetoaps.SistemaHotelaria.domain.StockItem;

import com.projetoaps.SistemaHotelaria.domain.reservation.Reservation;
import com.projetoaps.SistemaHotelaria.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "itens")
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

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public StockItem(UUID id, String name, int quantity){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
    public void reorderItem(){
        if(quantity < minStockLevel){
            quantity += 10;
        }
    }
}
