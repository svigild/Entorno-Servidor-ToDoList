package com.todolist.dto;

import jakarta.validation.constraints.NotBlank;

/*
 * DTO de entrada para el login de un usuario.
 * Recibe unicamente las credenciales necesarias para la autenticacion,
 * separando los datos de login del resto de la informacion del usuario.
 */
public class LoginDto {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "La contrasena es obligatoria")
    private String password;

    // Getters y setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
