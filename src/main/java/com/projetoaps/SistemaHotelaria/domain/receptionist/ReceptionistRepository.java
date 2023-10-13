package com.projetoaps.SistemaHotelaria.domain.receptionist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReceptionistRepository extends JpaRepository<Receptionist, UUID> {
}
