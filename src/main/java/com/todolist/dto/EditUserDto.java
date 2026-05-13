package com.todolist.dto;

import jakarta.validation.constraints.Email;

/*
 * DTO de entrada para editar un usuario (desde admin o perfil propio).
 * Permite modificar solo los campos editables sin afectar al id ni al rol,
 * que se gestionan por separado con endpoints especificos.
 */
public class EditUserDto {

    private String username;
    private String password;

    @Email(message = "El email debe tener un formato valido")
    private String email;

    private String fullname;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
