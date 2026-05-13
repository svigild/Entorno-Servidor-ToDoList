package com.todolist.service;

import com.todolist.dto.CreateTaskDto;
import com.todolist.dto.DashboardDto;
import com.todolist.dto.GetTaskDto;
import com.todolist.model.*;
import com.todolist.repository.CategoryRepository;
import com.todolist.repository.TagRepository;
import com.todolist.repository.TaskRepository;
import com.todolist.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository,
                       CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    // Obtener todas las tareas del usuario autenticado
    public List<GetTaskDto> findAllByUser(Long userId) {
        return taskRepository.findByAuthorId(userId).stream()
                .map(this::toDto)
                .toList();
    }

    // Obtener una tarea concreta del usuario
    public GetTaskDto findByIdAndUser(Long taskId, Long userId) {
        Task task = taskRepository.findByIdAndAuthorId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada o no te pertenece"));
        return toDto(task);
    }

    // Crear una nueva tarea asociada al usuario autenticado
    public GetTaskDto create(Long userId, CreateTaskDto dto) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());
        task.setAuthor(author);
        task.setDeadline(dto.getDeadline());
        task.setPriority(dto.getPriority());

        // Asignar categoria si se proporciona
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
            task.setCategory(category);
        }

        return toDto(taskRepository.save(task));
    }

    // Editar una tarea del usuario
    public GetTaskDto update(Long userId, Long taskId, CreateTaskDto dto) {
        Task task = taskRepository.findByIdAndAuthorId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada o no te pertenece"));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());
        task.setDeadline(dto.getDeadline());
        task.setPriority(dto.getPriority());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
            task.setCategory(category);
        } else {
            task.setCategory(null);
        }

        return toDto(taskRepository.save(task));
    }

    // Eliminar una tarea del usuario
    public void delete(Long userId, Long taskId) {
        Task task = taskRepository.findByIdAndAuthorId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada o no te pertenece"));
        taskRepository.delete(task);
    }

    // Buscar tareas por titulo
    public List<GetTaskDto> searchByTitle(Long userId, String title) {
        return taskRepository.findByAuthorIdAndTitleContainingIgnoreCase(userId, title).stream()
                .map(this::toDto)
                .toList();
    }

    // Buscar tareas por estado completado
    public List<GetTaskDto> searchByCompleted(Long userId, boolean completed) {
        return taskRepository.findByAuthorIdAndCompleted(userId, completed).stream()
                .map(this::toDto)
                .toList();
    }

    // Buscar tareas por categoria
    public List<GetTaskDto> searchByCategory(Long userId, Long categoryId) {
        return taskRepository.findByAuthorIdAndCategoryId(userId, categoryId).stream()
                .map(this::toDto)
                .toList();
    }

    // Buscar tareas por tag
    public List<GetTaskDto> searchByTag(Long userId, Long tagId) {
        return taskRepository.findByAuthorIdAndTagsId(userId, tagId).stream()
                .map(this::toDto)
                .toList();
    }

    // Buscar tareas por prioridad
    public List<GetTaskDto> searchByPriority(Long userId, Priority priority) {
        return taskRepository.findByAuthorIdAndPriority(userId, priority).stream()
                .map(this::toDto)
                .toList();
    }

    // Buscar tareas con deadline antes de una fecha
    public List<GetTaskDto> searchByDeadlineBefore(Long userId, LocalDateTime deadline) {
        return taskRepository.findByAuthorIdAndDeadlineBefore(userId, deadline).stream()
                .map(this::toDto)
                .toList();
    }

    // Buscar tareas actualizadas despues de una fecha
    public List<GetTaskDto> searchByUpdatedAfter(Long userId, LocalDateTime date) {
        return taskRepository.findByAuthorIdAndUpdatedAtAfter(userId, date).stream()
                .map(this::toDto)
                .toList();
    }

    // Asignar tags a una tarea
    public GetTaskDto assignTags(Long userId, Long taskId, List<Long> tagIds) {
        Task task = taskRepository.findByIdAndAuthorId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada o no te pertenece"));

        List<Tag> tags = tagRepository.findAllById(tagIds);
        // Solo permite asignar tags que pertenezcan al usuario
        for (Tag tag : tags) {
            if (!tag.getOwner().getId().equals(userId)) {
                throw new RuntimeException("El tag con id " + tag.getId() + " no te pertenece");
            }
            if (!task.getTags().contains(tag)) {
                task.getTags().add(tag);
            }
        }

        return toDto(taskRepository.save(task));
    }

    // Eliminar un tag de una tarea
    public GetTaskDto removeTag(Long userId, Long taskId, Long tagId) {
        Task task = taskRepository.findByIdAndAuthorId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada o no te pertenece"));

        task.getTags().removeIf(tag -> tag.getId().equals(tagId));
        return toDto(taskRepository.save(task));
    }

    // Generar el dashboard con estadisticas del usuario
    public DashboardDto getDashboard(Long userId) {
        List<Task> tasks = taskRepository.findByAuthorId(userId);
        DashboardDto dashboard = new DashboardDto();

        dashboard.setTotalTasks(tasks.size());
        dashboard.setCompletedTasks(taskRepository.countByAuthorIdAndCompleted(userId, true));
        dashboard.setPendingTasks(taskRepository.countByAuthorIdAndCompleted(userId, false));

        LocalDateTime now = LocalDateTime.now();
        dashboard.setOverdueTasks(taskRepository.countByAuthorIdAndCompletedFalseAndDeadlineBefore(userId, now));

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        dashboard.setCreatedToday(taskRepository.countByAuthorIdAndCreatedAtAfter(userId, startOfDay));

        // Agrupar tareas por categoria
        Map<String, Long> byCategory = tasks.stream()
                .filter(t -> t.getCategory() != null)
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getTitle(),
                        Collectors.counting()
                ));
        dashboard.setTasksByCategory(byCategory);

        // Agrupar tareas por tag
        Map<String, Long> byTag = new HashMap<>();
        for (Task task : tasks) {
            for (Tag tag : task.getTags()) {
                byTag.merge(tag.getName(), 1L, Long::sum);
            }
        }
        dashboard.setTasksByTag(byTag);

        // Agrupar tareas por prioridad
        Map<String, Long> byPriority = tasks.stream()
                .filter(t -> t.getPriority() != null)
                .collect(Collectors.groupingBy(
                        t -> t.getPriority().name(),
                        Collectors.counting()
                ));
        dashboard.setTasksByPriority(byPriority);

        return dashboard;
    }

    // Convierte la entidad Task a su DTO de salida
    private GetTaskDto toDto(Task task) {
        GetTaskDto dto = new GetTaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setDeadline(task.getDeadline());
        dto.setPriority(task.getPriority());
        dto.setAuthorUsername(task.getAuthor().getUsername());

        if (task.getCategory() != null) {
            dto.setCategoryTitle(task.getCategory().getTitle());
        }

        List<String> tagNames = task.getTags().stream()
                .map(Tag::getName)
                .toList();
        dto.setTags(tagNames);

        return dto;
    }
}
