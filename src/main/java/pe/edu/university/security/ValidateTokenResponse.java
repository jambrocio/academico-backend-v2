package pe.edu.university.security;

import lombok.Data;
import java.util.List;

@Data
public class ValidateTokenResponse {
    private boolean valid;
    private List<String> roles;
    private String username;
}
