# academico-backend

Proyecto backend para gestión académica (Spring Boot).

## Requisitos
- Java 11+ (recomendado 17)
- Maven 3.6+
- Base de datos configurada en `application.properties` o `application.yml`

## Compilar
mvn clean install

## Ejecutar
mvn spring-boot:run
o
java -jar target/academico-backend-<version>.jar

## OpenAPI / Swagger (Springdoc)
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml
- Swagger UI: http://localhost:8080/swagger-ui.html  (o /swagger-ui/index.html)

## Notas
- Se añadieron mappers en `pe.edu.university.mapper`: FacultadMapper, CarreraMapper, PrerrequisitoMapper.
- Si los campos identificadores no se llaman `id`, ajustar la exclusión en `updateEntity`.
- Ajusta los ServiceImpl para inyectar los mappers si no están ya referenciados.

## Estructura relevante
- src/main/java/pe/edu/university/mapper
- src/main/java/pe/edu/university/service/impl
