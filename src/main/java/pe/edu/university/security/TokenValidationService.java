package pe.edu.university.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenValidationService {

    private final RestTemplate restTemplate;

    // ObjectMapper para parsear respuestas JSON del servicio externo
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${token.validation.url:http://localhost:8082/users/validate-token}")
    private String tokenValidationUrl;

    /**
     * Valida el token consumiendo el servicio externo
     * 
     * @param token el token Bearer a validar
     * @return true si el token es válido, false en caso contrario
     */
    public boolean validateToken(String token) {
        try {
            // Eliminar el prefijo "Bearer " si existe
            String cleanToken = token.replace("Bearer ", "").trim();
            
            // Crear headers con Content-Type JSON
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            
            // Crear el cuerpo de la solicitud con el token en JSON
            String requestBody = "{\"token\":\"" + cleanToken + "\"}";
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            
            // Hacer la solicitud al servicio de validación (POST con body JSON)
            // Loguear token parcial para depuración (no exponer entero)
            String masked = maskToken(cleanToken);
            log.debug("Enviando token al servicio de validación (parcial): {}", masked);
            log.debug("Request body: {}", requestBody);

            ResponseEntity<String> response = restTemplate.exchange(
                    tokenValidationUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            String body = response.getBody();
            log.debug("Token validation service returned status {} and body: {}", response.getStatusCode().value(), body);

            if (response.getStatusCode().is2xxSuccessful() && body != null && !body.isBlank()) {
                try {
                    ValidateTokenResponse dto = objectMapper.readValue(body, ValidateTokenResponse.class);
                    log.debug("Parsed validate-token response: {}", dto);
                    return dto.isValid();
                } catch (JsonProcessingException jpe) {
                    log.warn("No se pudo parsear la respuesta JSON: {}. Considerando respuesta 2xx como válida.", jpe.getMessage());
                    // Fallback: si el servicio respondió 2xx pero no devolvió el JSON esperado, considerarlo válido
                    return true;
                }
            }

            // Si no fue 2xx o cuerpo vacío, no válido
            log.warn("Servicio de validación retornó código {} o cuerpo vacío", response.getStatusCode().value());
            return false;
            
        } catch (RestClientException e) {
            log.error("Error al validar el token con el servicio externo: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Error inesperado al validar el token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Devuelve una versión enmascarada del token para logs (primeros y últimos caracteres)
     */
    private String maskToken(String token) {
        if (token == null || token.isBlank()) return "<empty>";
        int len = token.length();
        if (len <= 10) return token.replaceAll(".(?=.{4})", "*");
        String start = token.substring(0, 6);
        String end = token.substring(len - 4);
        return start + "..." + end;
    }
}

