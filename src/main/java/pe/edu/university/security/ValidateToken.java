package pe.edu.university.security;

import java.lang.annotation.*;

/**
 * Anotación para validar el token Bearer en los endpoints
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateToken {
}
