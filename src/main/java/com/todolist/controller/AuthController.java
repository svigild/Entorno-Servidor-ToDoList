package com.todolist.controller;

import com.todolist.dto.JwtResponseDto;
import com.todolist.dto.LoginDto;
import com.todolist.dto.RegisterDto;
import com.todolist.security.CustomUserDetails;
import com.todolist.security.JwtService;
import com.todolist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticacion", description = "Endpoints publicos de registro y login. Devuelven un token JWT.")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtService jwtService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // Registro de un nuevo usuario: crea la cuenta y devuelve un token JWT
    @PostMapping("/register")
    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Crea una cuenta de usuario con rol USER y devuelve un token JWT para autenticarse."
    )
    public ResponseEntity<JwtResponseDto> register(@Valid @RequestBody RegisterDto dto) {
        // Registrar al usuario en la base de datos
        userService.register(dto);

        // Autenticar con las credenciales recien creadas para obtener el UserDetails
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        // Generar el token JWT
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(buildResponse(token, userDetails));
    }

    // Login: valida las credenciales y devuelve un token JWT
    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesion",
            description = "Valida las credenciales del usuario y devuelve un token JWT para autenticarse en los demas endpoints."
    )
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody LoginDto dto) {
        // Autenticar credenciales con Spring Security
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        // Generar el token JWT
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(buildResponse(token, userDetails));
    }

    // Construye el DTO de respuesta con el token y los datos del usuario
    private JwtResponseDto buildResponse(String token, CustomUserDetails userDetails) {
        JwtResponseDto response = new JwtResponseDto();
        response.setToken(token);
        response.setId(userDetails.getId());
        response.setUsername(userDetails.getUsername());

        // Obtener email y fullname del servicio
        var userData = userService.findById(userDetails.getId());
        response.setEmail(userData.getEmail());
        response.setFullname(userData.getFullname());
        response.setRole(userData.getRole());

        return response;
    }
}
