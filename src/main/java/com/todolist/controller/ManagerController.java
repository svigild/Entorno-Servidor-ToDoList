package com.todolist.controller;

import com.todolist.dto.CategoryDto;
import com.todolist.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/manager")
@Tag(name = "Gestor", description = "Endpoints para el rol GESTOR (y ADMIN)")
public class ManagerController {

    private final CategoryService categoryService;

    public ManagerController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    @Operation(
            summary = "Listar categorias (gestor)",
            description = "Devuelve todas las categorias. Accesible con rol GESTOR o ADMIN."
    )
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping("/categories")
    @Operation(summary = "Crear categoria (gestor)", description = "Crea una nueva categoria.")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(dto));
    }

    @PutMapping("/categories/{id}")
    @Operation(summary = "Editar categoria (gestor)", description = "Modifica una categoria existente.")
    public ResponseEntity<CategoryDto> updateCategory(
            @Parameter(description = "ID de la categoria") @PathVariable Long id,
            @Valid @RequestBody CategoryDto dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/categories/{id}")
    @Operation(summary = "Eliminar categoria (gestor)", description = "Elimina una categoria.")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID de la categoria") @PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
