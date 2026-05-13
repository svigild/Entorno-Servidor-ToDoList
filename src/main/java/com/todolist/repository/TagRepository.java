package com.todolist.repository;

import com.todolist.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    // Obtener todos los tags de un usuario
    List<Tag> findByOwnerId(Long ownerId);

    // Obtener un tag concreto de un usuario
    Optional<Tag> findByIdAndOwnerId(Long id, Long ownerId);
}
