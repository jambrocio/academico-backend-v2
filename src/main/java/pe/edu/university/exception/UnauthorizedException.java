package pe.edu.university.exception;

/**
 * Excepción lanzada cuando hay un error de autorización
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
