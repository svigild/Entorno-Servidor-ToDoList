package com.todolist.repository;

import com.todolist.model.Priority;
import com.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Obtener todas las tareas de un usuario
    List<Task> findByAuthorId(Long authorId);

    // Obtener una tarea concreta de un usuario
    Optional<Task> findByIdAndAuthorId(Long id, Long authorId);

    // Buscar por titulo (contiene, sin importar mayusculas)
    List<Task> findByAuthorIdAndTitleContainingIgnoreCase(Long authorId, String title);

    // Buscar por estado de completado
    List<Task> findByAuthorIdAndCompleted(Long authorId, boolean completed);

    // Buscar por categoria
    List<Task> findByAuthorIdAndCategoryId(Long authorId, Long categoryId);

    // Buscar por tag
    List<Task> findByAuthorIdAndTagsId(Long authorId, Long tagId);

    // Buscar por prioridad
    List<Task> findByAuthorIdAndPriority(Long authorId, Priority priority);

    // Buscar tareas con deadline antes de una fecha
    List<Task> findByAuthorIdAndDeadlineBefore(Long authorId, LocalDateTime deadline);

    // Buscar tareas actualizadas despues de una fecha
    List<Task> findByAuthorIdAndUpdatedAtAfter(Long authorId, LocalDateTime updatedAt);

    // Contar tareas completadas de un usuario
    long countByAuthorIdAndCompleted(Long authorId, boolean completed);

    // Contar tareas con deadline vencido y no completadas
    long countByAuthorIdAndCompletedFalseAndDeadlineBefore(Long authorId, LocalDateTime now);

    // Contar tareas creadas hoy
    long countByAuthorIdAndCreatedAtAfter(Long authorId, LocalDateTime startOfDay);
}
