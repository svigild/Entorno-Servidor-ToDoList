-- Datos iniciales para probar la aplicacion
-- Las contrasenas estan hasheadas con BCrypt (todas son "1234")

-- Usuarios: uno de cada rol para poder probar todos los endpoints
INSERT INTO users (username, password, email, fullname, role) VALUES ('admin', '$2a$10$RR2B6L4KQndoNUARcIMtkuytjfIsOdHKArI4lZN1J5dJeSXACR.Ya', 'admin@todolist.com', 'Administrador', 'ADMIN');
INSERT INTO users (username, password, email, fullname, role) VALUES ('gestor', '$2a$10$RR2B6L4KQndoNUARcIMtkuytjfIsOdHKArI4lZN1J5dJeSXACR.Ya', 'gestor@todolist.com', 'Gestor Principal', 'GESTOR');
INSERT INTO users (username, password, email, fullname, role) VALUES ('usuario', '$2a$10$RR2B6L4KQndoNUARcIMtkuytjfIsOdHKArI4lZN1J5dJeSXACR.Ya', 'usuario@todolist.com', 'Usuario Normal', 'USER');

-- Categorias de ejemplo
INSERT INTO categories (title) VALUES ('Trabajo');
INSERT INTO categories (title) VALUES ('Personal');
INSERT INTO categories (title) VALUES ('Estudios');

-- Tags de ejemplo para el usuario (id=3)
INSERT INTO tags (name, owner_id) VALUES ('urgente', 3);
INSERT INTO tags (name, owner_id) VALUES ('revision', 3);

-- Tareas de ejemplo para el usuario (id=3)
INSERT INTO tasks (title, description, completed, created_at, updated_at, deadline, priority, author_id, category_id) VALUES ('Terminar proyecto DWES', 'Completar la API REST del ToDo List', 0, NOW(), NOW(), '2025-06-30 23:59:59', 'HIGH', 3, 3);
INSERT INTO tasks (title, description, completed, created_at, updated_at, deadline, priority, author_id, category_id) VALUES ('Comprar material oficina', 'Comprar boligrafos y libretas', 0, NOW(), NOW(), '2025-07-15 12:00:00', 'LOW', 3, 2);
INSERT INTO tasks (title, description, completed, created_at, updated_at, deadline, priority, author_id, category_id) VALUES ('Reunion semanal', 'Preparar la presentacion para la reunion del lunes', 1, NOW(), NOW(), NULL, 'MEDIUM', 3, 1);

-- Asignar tags a tareas
INSERT INTO task_tags (task_id, tag_id) VALUES (1, 1);
INSERT INTO task_tags (task_id, tag_id) VALUES (1, 2);
INSERT INTO task_tags (task_id, tag_id) VALUES (2, 1);
