package com.projetoaps.SistemaHotelaria.controller;

import com.projetoaps.SistemaHotelaria.domain.user.*;
import com.projetoaps.SistemaHotelaria.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        User authenticatedUser = (User) auth.getPrincipal();

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseWithUserDTO(token, authenticatedUser.getLogin(), authenticatedUser.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().body("Usuário registrado com sucesso!");
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable UUID id){

        Optional<User> optionalUser = repository.findById(id);

        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("Não existe um usuário com esse id");
        }

        User findedUser = optionalUser.get();
        return ResponseEntity.ok(new ReturnUsersDTO(findedUser));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReturnUsersDTO>> findAllUsers() {
        var users = repository.findAll();
        List<ReturnUsersDTO> result = new ArrayList<>();
        users.forEach(u -> result.add(new ReturnUsersDTO(u.getId(), u.getLogin(), u.getRole())));
        return ResponseEntity.ok(result);
    }
}
