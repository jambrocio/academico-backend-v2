package pe.edu.university.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import pe.edu.university.exception.UnauthorizedException;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TokenValidationAspect {

    private final TokenValidationService tokenValidationService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Around("@annotation(pe.edu.university.security.ValidateToken)")
    public Object validateTokenAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // Obtener el token del header Authorization
        String token = extractTokenFromRequest();

        if (token == null || token.isEmpty()) {
            log.warn("Token no proporcionado en la solicitud");
            throw new UnauthorizedException("Token no proporcionado");
        }

        // Validar el token
        if (!tokenValidationService.validateToken(token)) {
            log.warn("Token inválido o expirado: {}", token.substring(0, Math.min(10, token.length())) + "...");
            throw new UnauthorizedException("Token inválido o expirado");
        }

        log.debug("Token validado correctamente");
        
        // Continuar con la ejecución del método
        return joinPoint.proceed();
    }

    /**
     * Extrae el token Bearer del header Authorization
     * 
     * @return el token sin el prefijo "Bearer ", o null si no existe
     */
    private String extractTokenFromRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String authHeader = request.getHeader(AUTHORIZATION_HEADER);
                
                if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
                    return authHeader.substring(BEARER_PREFIX.length());
                }
            }
        } catch (Exception e) {
            log.error("Error al extraer el token de la solicitud: {}", e.getMessage());
        }
        return null;
    }
}
