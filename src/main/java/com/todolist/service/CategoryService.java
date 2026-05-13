package com.todolist.service;

import com.todolist.dto.CategoryDto;
import com.todolist.model.Category;
import com.todolist.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Obtener todas las categorias
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    // Obtener una categoria por su id
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        return toDto(category);
    }

    // Crear una nueva categoria
    public CategoryDto create(CategoryDto dto) {
        if (categoryRepository.existsByTitle(dto.getTitle())) {
            throw new RuntimeException("Ya existe una categoria con ese titulo");
        }
        Category category = new Category(dto.getTitle());
        return toDto(categoryRepository.save(category));
    }

    // Editar una categoria existente
    public CategoryDto update(Long id, CategoryDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        category.setTitle(dto.getTitle());
        return toDto(categoryRepository.save(category));
    }

    // Eliminar una categoria
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada");
        }
        categoryRepository.deleteById(id);
    }

    // Convierte la entidad a DTO
    private CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setTitle(category.getTitle());
        return dto;
    }
}
