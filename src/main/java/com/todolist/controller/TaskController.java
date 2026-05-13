package com.todolist.controller;

import com.todolist.dto.AssignTagsDto;
import com.todolist.dto.CreateTaskDto;
import com.todolist.dto.GetTaskDto;
import com.todolist.model.Priority;
import com.todolist.security.CustomUserDetails;
import com.todolist.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/task")
@Tag(name = "Tareas", description = "CRUD y busquedas de tareas del usuario autenticado")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Listar todas las tareas del usuario autenticado
    @GetMapping
    @Operation(summary = "Listar tareas", description = "Devuelve todas las tareas del usuario autenticado.")
    public ResponseEntity<List<GetTaskDto>> getAll(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(taskService.findAllByUser(user.getId()));
    }

    // Ver una tarea concreta del usuario
    @GetMapping("/{id}")
    @Operation(summary = "Ver tarea por ID", description = "Devuelve una tarea concreta si pertenece al usuario autenticado.")
    public ResponseEntity<GetTaskDto> getById(
            @Parameter(description = "ID de la tarea") @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(taskService.findByIdAndUser(id, user.getId()));
    }

    // Crear una nueva tarea asociada al usuario autenticado
    @PostMapping
    @Operation(
            summary = "Crear tarea",
            description = "Crea una nueva tarea asociada automaticamente al usuario autenticado."
    )
    public ResponseEntity<GetTaskDto> create(
            @Valid @RequestBody CreateTaskDto dto,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(user.getId(), dto));
    }

    // Editar una tarea existente (solo si pertenece al usuario)
    @PutMapping("/{id}")
    @Operation(summary = "Editar tarea", description = "Modifica una tarea existente del usuario autenticado.")
    public ResponseEntity<GetTaskDto> update(
            @Parameter(description = "ID de la tarea") @PathVariable Long id,
            @Valid @RequestBody CreateTaskDto dto,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(taskService.update(user.getId(), id, dto));
    }

    // Eliminar una tarea (solo si pertenece al usuario)
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tarea", description = "Elimina una tarea del usuario autenticado.")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la tarea") @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user) {
        taskService.delete(user.getId(), id);
        return ResponseEntity.noContent().build();
    }

    // --- Busquedas por campos base de la tarea ---
    // Cada busqueda usa /task/search con un parametro query distinto,
    // tal como indica el enunciado: "consultas por cada campo propio de la tarea"

    // Buscar tareas por titulo (contiene texto)
    @GetMapping(value = "/search", params = "title")
    @Operation(
            summary = "Buscar tareas por titulo",
            description = "Busca tareas del usuario cuyo titulo contenga el texto indicado."
    )
    public ResponseEntity<List<GetTaskDto>> searchByTitle(
            @Parameter(description = "Texto a buscar en el titulo") @RequestParam String title,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(taskService.searchByTitle(user.getId(), title));
    }

    // Buscar tareas por estado completado/pendiente
    @GetMapping(value = "/search", params = "completed")
    @Operation(summary = "Buscar por estado", description = "Filtra tareas completadas (true) o pendientes (false).")
    public ResponseEntity<List<GetTaskDto>> searchByCompleted(
            @Parameter(description = "true para completadas, false para pendientes") @RequestParam boolean completed,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(taskService.searchByCompleted(user.getId(), completed));
    }

    // Buscar tareas por categoria
    @GetMapping(value = "/search", params = "category")
    @Operation(summary = "Buscar por categoria", description = "Filtra tareas por ID de categoria.")
    public ResponseEntity<List<GetTaskDto>> searchByCategory(
            @Parameter(description = "ID de la categoria") @RequestParam Long category,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(taskService.searchByCategory(user.getId(), category));
    }

    // --- Busquedas por campos adicionales (ampliacion) ---

    // Buscar tareas por nivel de prioridad
    @GetMapping(value = "/search", params = "priority")
    @Operation(
            summary = "Buscar por prioridad",
            description = "Filtra tareas por nivel de prioridad (LOW, MEDIUM, HIGH)."
    )
    public ResponseEntity<List<GetTaskDto>> searchByPriority(
            @Parameter(description = "Nivel de prioridad: LOW, MEDIUM o HIGH") @RequestParam Priority priority,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(taskService.searchByPriority(user.getId(), priority));
    }

    // Buscar tareas con fecha limite anterior a la indicada
    @GetMapping(value = "/search", params = "deadlineBefore")
    @Operation(
            summary = "Buscar por fecha limite",
            description = "Devuelve tareas cuya fecha limite sea anterior a la fecha indicada."
    )
    public ResponseEntity<List<GetTaskDto>> searchByDeadlineBefore(
            @Parameter(description = "Fecha en formato ISO (ej: 2025-12-31T23:59:59)") @RequestParam LocalDateTime deadlineBefore,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(taskService.searchByDeadlineBefore(user.getId(), deadlineBefore));
    }

    // Buscar tareas modificadas despues de una fecha
    @GetMapping(value = "/search", params = "updatedAfter")
    @Operation(
            summary = "Buscar por fecha de actualizacion",
            description = "Devuelve tareas que se hayan modificado despues de la fecha indicada."
    )
    public ResponseEntity<List<GetTaskDto>> searchByUpdatedAfter(
            @Parameter(description = "Fecha desde en formato ISO") @RequestParam LocalDateTime updatedAfter,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(taskService.searchByUpdatedAfter(user.getId(), updatedAfter));
    }

    // --- Busqueda por tag ---

    // Buscar tareas que tengan un tag concreto asignado
    @GetMapping("/by-tag")
    @Operation(summary = "Buscar por tag", description = "Devuelve las tareas que tengan asignado un tag concreto.")
    public ResponseEntity<List<GetTaskDto>> searchByTag(
            @Parameter(description = "ID del tag") @RequestParam Long tag,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(taskService.searchByTag(user.getId(), tag));
    }

    // --- Gestion de tags en tareas ---

    // Asignar uno o varios tags a una tarea existente
    @PostMapping("/{id}/tags")
    @Operation(summary = "Asignar tags a tarea", description = "Anade uno o varios tags a una tarea existente.")
    public ResponseEntity<GetTaskDto> assignTags(
            @Parameter(description = "ID de la tarea") @PathVariable Long id,
            @Valid @RequestBody AssignTagsDto dto,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(taskService.assignTags(user.getId(), id, dto.getTagIds()));
    }

    // Eliminar un tag de una tarea
    @DeleteMapping("/{id}/tags/{tagId}")
    @Operation(summary = "Eliminar tag de tarea", description = "Elimina la asociacion de un tag con una tarea.")
    public ResponseEntity<GetTaskDto> removeTag(
            @Parameter(description = "ID de la tarea") @PathVariable Long id,
            @Parameter(description = "ID del tag a eliminar") @PathVariable Long tagId,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(taskService.removeTag(user.getId(), id, tagId));
    }
}
