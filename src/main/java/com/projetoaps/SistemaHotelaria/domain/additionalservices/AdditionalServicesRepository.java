package com.projetoaps.SistemaHotelaria.domain.additionalservices;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdditionalServicesRepository extends JpaRepository<AdditionalServices, UUID> {
}
