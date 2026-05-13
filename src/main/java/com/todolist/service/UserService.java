package com.todolist.service;

import com.todolist.dto.EditUserDto;
import com.todolist.dto.GetUserDto;
import com.todolist.dto.RegisterDto;
import com.todolist.model.User;
import com.todolist.model.UserRole;
import com.todolist.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Validar credenciales y devolver datos del usuario
    public GetUserDto login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        return toDto(user);
    }

    // Registrar un nuevo usuario con rol USER por defecto
    public GetUserDto register(RegisterDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya esta registrado");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setFullname(dto.getFullname());
        user.setRole(UserRole.USER);

        return toDto(userRepository.save(user));
    }

    // Obtener todos los usuarios (solo admin)
    public List<GetUserDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    // Obtener un usuario por su id
    public GetUserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return toDto(user);
    }

    // Editar un usuario (admin)
    public GetUserDto update(Long id, EditUserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        applyChanges(user, dto);
        return toDto(userRepository.save(user));
    }

    // Eliminar un usuario
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    // Promocionar un usuario a gestor
    public GetUserDto promote(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (user.getRole() != UserRole.USER) {
            throw new RuntimeException("Solo se puede promocionar a usuarios con rol USER");
        }
        user.setRole(UserRole.GESTOR);
        return toDto(userRepository.save(user));
    }

    // Degradar un gestor a usuario
    public GetUserDto demote(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (user.getRole() != UserRole.GESTOR) {
            throw new RuntimeException("Solo se puede degradar a usuarios con rol GESTOR");
        }
        user.setRole(UserRole.USER);
        return toDto(userRepository.save(user));
    }

    // Modificar perfil del propio usuario
    public GetUserDto updateProfile(Long userId, EditUserDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        applyChanges(user, dto);
        return toDto(userRepository.save(user));
    }

    // Aplica los cambios del DTO a la entidad
    private void applyChanges(User user, EditUserDto dto) {
        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getFullname() != null && !dto.getFullname().isBlank()) {
            user.setFullname(dto.getFullname());
        }
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
    }

    // Convierte la entidad User a su DTO de salida
    private GetUserDto toDto(User user) {
        GetUserDto dto = new GetUserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullname(user.getFullname());
        dto.setRole(user.getRole());
        return dto;
    }
}
