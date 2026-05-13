package com.todolist.dto;

import com.todolist.model.Priority;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/*
 * DTO de entrada para crear una tarea.
 * Se usa para recibir solo los datos necesarios del cliente,
 * evitando que se envien campos internos como el id, la fecha de creacion
 * o el autor, que se asignan automaticamente en el servidor.
 */
public class CreateTaskDto {

    @NotBlank(message = "El titulo es obligatorio")
    private String title;

    private String description;
    private boolean completed;
    private Long categoryId;
    private LocalDateTime deadline;
    private Priority priority;

    // Getters y setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
