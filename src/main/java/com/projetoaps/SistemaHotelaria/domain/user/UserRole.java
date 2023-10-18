package com.projetoaps.SistemaHotelaria.domain.user;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    CLIENT("client"),
    MAINTENANCE("maintenance"),
    CASHIER("cashier"),
    CLEANER("cleaner"),
    MANAGER("manager"),
    CUSTOMER("customer");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
