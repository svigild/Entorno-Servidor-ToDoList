package com.todolist.service;

import com.todolist.dto.TagDto;
import com.todolist.model.Tag;
import com.todolist.model.User;
import com.todolist.repository.TagRepository;
import com.todolist.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public TagService(TagRepository tagRepository, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    // Obtener todos los tags del usuario autenticado
    public List<TagDto> findAllByUser(Long userId) {
        return tagRepository.findByOwnerId(userId).stream()
                .map(this::toDto)
                .toList();
    }

    // Crear un nuevo tag asociado al usuario
    public TagDto create(Long userId, TagDto dto) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Tag tag = new Tag(dto.getName(), owner);
        return toDto(tagRepository.save(tag));
    }

    // Editar un tag del usuario
    public TagDto update(Long userId, Long tagId, TagDto dto) {
        Tag tag = tagRepository.findByIdAndOwnerId(tagId, userId)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado o no te pertenece"));
        tag.setName(dto.getName());
        return toDto(tagRepository.save(tag));
    }

    // Eliminar un tag del usuario
    public void delete(Long userId, Long tagId) {
        Tag tag = tagRepository.findByIdAndOwnerId(tagId, userId)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado o no te pertenece"));
        tagRepository.delete(tag);
    }

    // Convierte la entidad a DTO
    private TagDto toDto(Tag tag) {
        TagDto dto = new TagDto();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        return dto;
    }
}
