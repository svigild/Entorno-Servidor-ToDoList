package com.todolist.controller;

import com.todolist.dto.TagDto;
import com.todolist.security.CustomUserDetails;
import com.todolist.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tag")
@Tag(name = "Tags", description = "CRUD de tags del usuario autenticado")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // Listar todos los tags del usuario
    @GetMapping
    @Operation(summary = "Listar tags", description = "Devuelve todos los tags creados por el usuario autenticado.")
    public ResponseEntity<List<TagDto>> getAll(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(tagService.findAllByUser(user.getId()));
    }

    // Crear un nuevo tag
    @PostMapping
    @Operation(summary = "Crear tag", description = "Crea un nuevo tag asociado al usuario autenticado.")
    public ResponseEntity<TagDto> create(
            @Valid @RequestBody TagDto dto,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(user.getId(), dto));
    }

    // Editar un tag
    @PutMapping("/{id}")
    @Operation(summary = "Editar tag", description = "Modifica el nombre de un tag del usuario.")
    public ResponseEntity<TagDto> update(
            @Parameter(description = "ID del tag") @PathVariable Long id,
            @Valid @RequestBody TagDto dto,
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(tagService.update(user.getId(), id, dto));
    }

    // Eliminar un tag
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tag", description = "Elimina un tag del usuario autenticado.")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del tag") @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user) {
        tagService.delete(user.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
