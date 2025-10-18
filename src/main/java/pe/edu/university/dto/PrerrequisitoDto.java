package pe.edu.university.dto;

import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PrerrequisitoDto {
    private Long prerrequisitoId;
    private Long cursoId;
    private Long cursoReqId;
}
