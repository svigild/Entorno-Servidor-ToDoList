package com.todolist.dto;

import com.todolist.model.UserRole;

/*
 * DTO de respuesta para el login y registro.
 * Devuelve el token JWT junto con los datos basicos del usuario,
 * para que el cliente pueda almacenar el token y conocer el rol del usuario.
 */
public class JwtResponseDto {

    private String token;
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private UserRole role;

    public JwtResponseDto() {
    }

    public JwtResponseDto(String token, Long id, String username, String email, String fullname, UserRole role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.role = role;
    }

    // Getters y setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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
