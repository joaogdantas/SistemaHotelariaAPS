package com.projetoaps.SistemaHotelaria;

import com.projetoaps.SistemaHotelaria.domain.user.User;
import com.projetoaps.SistemaHotelaria.domain.user.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackageClasses = {User.class})
@EnableJpaRepositories(basePackageClasses = {UserRepository.class})
@SpringBootApplication
public class SistemaHotelariaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaHotelariaApplication.class, args);
    }

}
