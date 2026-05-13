package com.todolist.controller;

import com.todolist.dto.DashboardDto;
import com.todolist.security.CustomUserDetails;
import com.todolist.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "Estadisticas de tareas del usuario")
public class DashboardController {

    private final TaskService taskService;

    public DashboardController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Obtener estadisticas de las tareas del usuario
    @GetMapping
    @Operation(
            summary = "Dashboard de tareas",
            description = "Devuelve estadisticas sobre las tareas del usuario: total, completadas, pendientes, "
                    + "vencidas, creadas hoy, agrupadas por categoria, tag y prioridad."
    )
    public ResponseEntity<DashboardDto> getDashboard(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(taskService.getDashboard(user.getId()));
    }
}
