# Pruebas de Validación de Token

## Endpoint Protegido

- **GET** `/api/facultads` - Requiere token Bearer válido

## Ejemplos de Uso

### 1. Sin Token (Error 401)
```bash
curl -X GET http://localhost:8080/api/facultads
```

Respuesta esperada:
```json
{"error": "Token no proporcionado"}
```

### 2. Con Token Válido (Suponiendo que el servicio retorna 200)
```bash
curl -X GET http://localhost:8080/api/facultads \
  -H "Authorization: Bearer tu_token_válido_aqui"
```

Respuesta esperada: 200 OK con lista de facultades

### 3. Con Token Inválido (Error 401)
```bash
curl -X GET http://localhost:8080/api/facultads \
  -H "Authorization: Bearer token_inválido"
```

Respuesta esperada:
```json
{"error": "Token inválido o expirado"}
```

## Endpoints Sin Validación

- **POST** `/api/facultads` - Crear facultad (sin validación)
- **PUT** `/api/facultads/{id}` - Actualizar facultad (sin validación)
- **GET** `/api/facultads/{id}` - Obtener facultad por ID (sin validación)
- **DELETE** `/api/facultads/{id}` - Eliminar facultad (sin validación)

## Servicio de Validación

El servicio consulta: `http://localhost:8082/users/validate-token`
- Método: GET
- Header: `Authorization: Bearer <token>`
- Respuesta: 2xx = válido, otros códigos = inválido

## Configuración

Ubicación: `src/main/resources/application.yml`

```yaml
token:
  validation:
    url: http://localhost:8082/users/validate-token
```
