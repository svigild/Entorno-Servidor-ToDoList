package com.todolist.controller;

import com.todolist.dto.CategoryDto;
import com.todolist.dto.EditUserDto;
import com.todolist.dto.GetUserDto;
import com.todolist.service.CategoryService;
import com.todolist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Administrador", description = "Endpoints exclusivos para el rol ADMIN")
public class AdminController {

    private final UserService userService;
    private final CategoryService categoryService;

    public AdminController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    // --- Gestion de usuarios ---

    @PostMapping("/users/{id}/promote")
    @Operation(
            summary = "Promocionar usuario a GESTOR",
            description = "Cambia el rol de un usuario de USER a GESTOR. Solo accesible por ADMIN."
    )
    public ResponseEntity<GetUserDto> promote(
            @Parameter(description = "ID del usuario a promocionar") @PathVariable Long id) {
        return ResponseEntity.ok(userService.promote(id));
    }

    @PostMapping("/users/{id}/demote")
    @Operation(
            summary = "Degradar gestor a USER",
            description = "Cambia el rol de un usuario de GESTOR a USER. Solo accesible por ADMIN."
    )
    public ResponseEntity<GetUserDto> demote(
            @Parameter(description = "ID del gestor a degradar") @PathVariable Long id) {
        return ResponseEntity.ok(userService.demote(id));
    }

    @GetMapping("/users")
    @Operation(summary = "Listar todos los usuarios", description = "Devuelve la lista completa de usuarios del sistema.")
    public ResponseEntity<List<GetUserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Ver un usuario concreto", description = "Devuelve los datos de un usuario por su ID.")
    public ResponseEntity<GetUserDto> getUser(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Editar un usuario", description = "Permite modificar los datos de un usuario existente.")
    public ResponseEntity<GetUserDto> updateUser(
            @Parameter(description = "ID del usuario a editar") @PathVariable Long id,
            @Valid @RequestBody EditUserDto dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario del sistema junto con sus tareas y tags.")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID del usuario a eliminar") @PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Gestion de categorias (admin) ---

    @GetMapping("/categories")
    @Operation(summary = "Listar categorias (admin)", description = "Devuelve todas las categorias disponibles.")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping("/categories")
    @Operation(summary = "Crear categoria", description = "Crea una nueva categoria en el sistema.")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(dto));
    }

    @PutMapping("/categories/{id}")
    @Operation(summary = "Editar categoria", description = "Modifica el titulo de una categoria existente.")
    public ResponseEntity<CategoryDto> updateCategory(
            @Parameter(description = "ID de la categoria") @PathVariable Long id,
            @Valid @RequestBody CategoryDto dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/categories/{id}")
    @Operation(summary = "Eliminar categoria", description = "Elimina una categoria del sistema.")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID de la categoria a eliminar") @PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
