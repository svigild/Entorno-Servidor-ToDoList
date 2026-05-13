package com.todolist.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/*
 * DTO de entrada para asignar tags a una tarea.
 * Recibe una lista de ids de tags, evitando enviar objetos Tag
 * completos desde el cliente y simplificando la peticion.
 */
public class AssignTagsDto {

    @NotEmpty(message = "Debe incluir al menos un tag")
    private List<Long> tagIds;

    // Getters y setters

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }
}
