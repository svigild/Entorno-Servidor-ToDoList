package com.todolist.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de la categoria (ej: "Trabajo", "Personal")
    @Column(nullable = false, unique = true)
    private String title;

    // Relacion uno a muchos: una categoria puede tener muchas tareas
    @OneToMany(mappedBy = "category")
    private List<Task> tasks = new ArrayList<>();

    public Category() {
    }

    public Category(String title) {
        this.title = title;
    }

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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
