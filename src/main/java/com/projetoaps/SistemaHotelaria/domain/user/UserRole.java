package com.projetoaps.SistemaHotelaria.domain.user;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    CLIENT("client"),
    MAINTENANCE("maintenance"),
    CASHIER("cashier"),
    CLEANING("cleaning"),
    MANAGER("manager");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
