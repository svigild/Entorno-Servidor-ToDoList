package com.todolist.dto;

import jakarta.validation.constraints.NotBlank;

/*
 * DTO compartido para crear, editar y devolver tags.
 * Similar a CategoryDto, al tener un solo campo editable (name)
 * se reutiliza el mismo DTO para entrada y salida, incluyendo
 * el id en las respuestas.
 */
public class TagDto {

    private Long id;

    @NotBlank(message = "El nombre del tag es obligatorio")
    private String name;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
