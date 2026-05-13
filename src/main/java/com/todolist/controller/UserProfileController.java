package com.todolist.controller;

import com.todolist.dto.EditUserDto;
import com.todolist.dto.GetUserDto;
import com.todolist.security.CustomUserDetails;
import com.todolist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "Perfil de usuario", description = "Gestion del perfil del usuario autenticado")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    // Modificar perfil del usuario autenticado
    @PutMapping("/profile")
    @Operation(
            summary = "Modificar perfil",
            description = "Permite al usuario autenticado modificar sus datos: username, email, contrasena y nombre completo."
    )
    public ResponseEntity<GetUserDto> updateProfile(
            @Valid @RequestBody EditUserDto dto,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(userService.updateProfile(user.getId(), dto));
    }
}
