package com.todolist.dto;

import jakarta.validation.constraints.NotBlank;

/*
 * DTO compartido para crear y editar categorias.
 * Al ser una entidad sencilla con un solo campo editable (title),
 * no es necesario separar en DTOs de entrada y salida.
 * Para la respuesta se devuelve junto con el id.
 */
public class CategoryDto {

    private Long id;

    @NotBlank(message = "El titulo de la categoria es obligatorio")
    private String title;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
