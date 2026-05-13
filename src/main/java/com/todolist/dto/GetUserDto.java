package com.todolist.dto;

import com.todolist.model.UserRole;

/*
 * DTO de salida para devolver datos de un usuario.
 * No incluye la contrasena para evitar exponer informacion
 * sensible en las respuestas de la API.
 */
public class GetUserDto {

    private Long id;
    private String username;
    private String email;
    private String fullname;
    private UserRole role;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
