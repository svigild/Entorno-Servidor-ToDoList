package com.todolist.dto;

import java.util.Map;

/*
 * DTO de salida para el dashboard del usuario.
 * Agrupa estadisticas sobre las tareas del usuario en un unico
 * objeto, evitando multiples llamadas a la API para obtener
 * la misma informacion.
 */
public class DashboardDto {

    private long totalTasks;
    private long completedTasks;
    private long pendingTasks;
    private long overdueTasks;
    private long createdToday;
    private Map<String, Long> tasksByCategory;
    private Map<String, Long> tasksByTag;
    private Map<String, Long> tasksByPriority;

    // Getters y setters

    public long getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(long totalTasks) {
        this.totalTasks = totalTasks;
    }

    public long getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(long completedTasks) {
        this.completedTasks = completedTasks;
    }

    public long getPendingTasks() {
        return pendingTasks;
    }

    public void setPendingTasks(long pendingTasks) {
        this.pendingTasks = pendingTasks;
    }

    public long getOverdueTasks() {
        return overdueTasks;
    }

    public void setOverdueTasks(long overdueTasks) {
        this.overdueTasks = overdueTasks;
    }

    public long getCreatedToday() {
        return createdToday;
    }

    public void setCreatedToday(long createdToday) {
        this.createdToday = createdToday;
    }

    public Map<String, Long> getTasksByCategory() {
        return tasksByCategory;
    }

    public void setTasksByCategory(Map<String, Long> tasksByCategory) {
        this.tasksByCategory = tasksByCategory;
    }

    public Map<String, Long> getTasksByTag() {
        return tasksByTag;
    }

    public void setTasksByTag(Map<String, Long> tasksByTag) {
        this.tasksByTag = tasksByTag;
    }

    public Map<String, Long> getTasksByPriority() {
        return tasksByPriority;
    }

    public void setTasksByPriority(Map<String, Long> tasksByPriority) {
        this.tasksByPriority = tasksByPriority;
    }
}
