package com.todolist.controller;

import com.todolist.dto.CategoryDto;
import com.todolist.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categorias (usuario)", description = "Consulta de categorias para usuarios autenticados")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Listar categorias disponibles (cualquier usuario autenticado)
    @GetMapping
    @Operation(
            summary = "Listar categorias disponibles",
            description = "Devuelve todas las categorias del sistema. Accesible por cualquier usuario autenticado."
    )
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }
}
