package pe.edu.university.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenValidationInterceptor implements HandlerInterceptor {

    private final TokenValidationService tokenValidationService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Verificar si el handler es un método de controlador
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            
            // Verificar si el método tiene la anotación @ValidateToken
            if (handlerMethod.getMethod().isAnnotationPresent(ValidateToken.class)) {
                log.debug("Validando token para el método: {}", handlerMethod.getMethod().getName());
                
                String token = extractTokenFromRequest(request);
                
                if (token == null || token.isEmpty()) {
                    log.warn("Token no proporcionado en la solicitud");
                    sendUnauthorizedResponse(response, "Token no proporcionado");
                    return false;
                }
                
                if (!tokenValidationService.validateToken(token)) {
                    log.warn("Token inválido o expirado");
                    sendUnauthorizedResponse(response, "Token inválido o expirado");
                    return false;
                }
                
                log.debug("Token validado correctamente");
            }
        }
        
        return true;
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader(AUTHORIZATION_HEADER);
            
            if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
                return authHeader.substring(BEARER_PREFIX.length());
            }
        } catch (Exception e) {
            log.error("Error al extraer el token de la solicitud: {}", e.getMessage());
        }
        return null;
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
