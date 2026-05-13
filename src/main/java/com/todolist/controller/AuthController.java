package com.todolist.controller;

import com.todolist.dto.GetUserDto;
import com.todolist.dto.LoginDto;
import com.todolist.dto.RegisterDto;
import com.todolist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticacion", description = "Endpoints publicos de registro y login")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Registro de un nuevo usuario (publico, sin autenticacion)
    @PostMapping("/register")
    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Permite crear una cuenta de usuario nueva con rol USER. No requiere autenticacion."
    )
    public ResponseEntity<GetUserDto> register(@Valid @RequestBody RegisterDto dto) {
        GetUserDto created = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Login de usuario: valida credenciales y devuelve los datos del usuario
    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesion",
            description = "Valida las credenciales del usuario y devuelve sus datos si son correctas. No requiere autenticacion."
    )
    public ResponseEntity<GetUserDto> login(@Valid @RequestBody LoginDto dto) {
        GetUserDto user = userService.login(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok(user);
    }
}
