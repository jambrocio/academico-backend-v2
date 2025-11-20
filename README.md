# academico-backend

Proyecto backend para gestión académica (Spring Boot).

## Requisitos
- Java 11+ (recomendado 17)
- Maven 3.6+
- Base de datos configurada en `application.properties` o `application.yml`
- **Dependencia externa**: Proyecto [backend-userapp](https://github.com/jambrocio/backend-userapp) debe estar ejecutándose en `http://localhost:8082` para la validación de tokens en el endpoint `/users/validate-token`

## Compilar
mvn clean install

## Ejecutar
mvn spring-boot:run
o
java -jar target/academico-backend-<version>.jar

## Ejecutar con docker
docker-compose up -d

## OpenAPI / Swagger (Springdoc)
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml
- Swagger UI: http://localhost:8080/swagger-ui.html  (o /swagger-ui/index.html)

## Validación de Tokens

El endpoint `GET /api/facultads` (listado de facultades) requiere validación de token Bearer.

### ⚠️ Dependencia Externa - IMPORTANTE PARA PRUEBAS

Para realizar pruebas del endpoint protegido, **DEBE estar ejecutándose el proyecto [backend-userapp](https://github.com/jambrocio/backend-userapp)** en el puerto **8082**.

**Pasos para configurar:**

1. Clonar el repositorio `backend-userapp`:
```bash
git clone https://github.com/jambrocio/backend-userapp.git
cd backend-userapp
```

2. Ejecutar el servicio en puerto 8082:
```bash
mvn spring-boot:run
# o configurar en application.yml: server.port: 8082
```

3. Verificar que está corriendo:
```bash
curl http://localhost:8082/users/validate-token -X POST -H "Content-Type: application/json" -d '{"token":"test"}'
# Deberá responder con un JSON (200, 401, etc.)
```

**Sin este servicio ejecutándose, las pruebas de `GET /api/facultads` fallarán.**

### Configuración
La URL del servicio de validación está configurada en `application.yml`:
```yaml
token:
  validation:
    url: http://localhost:8082/users/validate-token
```

Para cambiar el puerto o URL, edita la propiedad anterior en `src/main/resources/application.yml`.

### Flujo de Validación
1. Cliente envía: `GET /api/facultads` con header `Authorization: Bearer <token>`
2. El servicio intercepta la solicitud y valida el token consultando `http://localhost:8082/users/validate-token`
3. Si el servicio responde con `{"valid": true, ...}`, la solicitud es autorizada (200 OK)
4. Si `valid` es false o el servicio retorna error, se rechaza con 401 Unauthorized

### Ejemplo de Uso
```bash
# Con token válido
curl -H "Authorization: Bearer <TOKEN_VALIDO>" http://localhost:8080/api/facultads

# Sin token (Error 401)
curl http://localhost:8080/api/facultads
# Respuesta: {"error": "Token no proporcionado"}

# Con token inválido (Error 401)
curl -H "Authorization: Bearer <TOKEN_INVALIDO>" http://localhost:8080/api/facultads
# Respuesta: {"error": "Token inválido o expirado"}
```

## Notas
- Se añadieron mappers en `pe.edu.university.mapper`: FacultadMapper, CarreraMapper, PrerrequisitoMapper.
- Si los campos identificadores no se llaman `id`, ajustar la exclusión en `updateEntity`.
- Ajusta los ServiceImpl para inyectar los mappers si no están ya referenciados.

## Estructura relevante
- src/main/java/pe/edu/university/mapper
- src/main/java/pe/edu/university/service/impl
- src/main/java/pe/edu/university/security (Token validation)
- src/main/java/pe/edu/university/config (WebMvcConfig para interceptores)
