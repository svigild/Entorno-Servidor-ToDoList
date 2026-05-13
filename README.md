# ToDo List - API REST

API REST para gestionar una lista de tareas desarrollada con Spring Boot, JPA/Hibernate y Spring Security.

## Requisitos previos

- **Java 17** o superior
- **Maven** (o usar el wrapper `mvnw` incluido)
- **MySQL 8** (se puede usar un contenedor Docker)

## Puesta en marcha

### 1. Arrancar MySQL con Docker

```bash
docker run --name mysql-todolist -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:8
```

### 2. Configurar la base de datos

El archivo `src/main/resources/application.properties` ya viene configurado para conectarse a `localhost:3306` con usuario `root` y contraseña `root`. Si tu configuracion es diferente, modifica estos valores:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todolist_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```

La base de datos `todolist_db` se crea automaticamente al arrancar.

### 3. Ejecutar la aplicacion

**Desde IntelliJ IDEA:**
1. Abre el proyecto (File > Open > selecciona la carpeta del proyecto)
2. Espera a que IntelliJ descargue las dependencias de Maven
3. Ejecuta la clase `TodolistApplication.java`

**Desde terminal:**
```bash
mvn spring-boot:run
```

### 4. Acceder a la API

- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Desde Swagger puedes probar todos los endpoints directamente

## Usuarios de prueba

La aplicacion carga datos iniciales automaticamente. Todos los usuarios tienen la contraseña `1234`:

| Usuario   | Contraseña | Rol    |
|-----------|-----------|--------|
| admin     | 1234      | ADMIN  |
| gestor    | 1234      | GESTOR |
| usuario   | 1234      | USER   |

## Autenticacion

La API usa **Basic Auth**. En Swagger, pulsa el boton "Authorize" e introduce el usuario y contraseña.
