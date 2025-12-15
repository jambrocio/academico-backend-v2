package pe.edu.university.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.MatriculaDto;
import pe.edu.university.entity.*;
import pe.edu.university.repository.*;
import pe.edu.university.security.TokenValidationService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MatriculaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private SeccionRepository seccionRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private FacultadRepository facultadRepository;
    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TokenValidationService tokenValidationService;

    private Long estudianteId;
    private Long seccionId;

    @BeforeEach
    void setUp() {
        // Mock token validation
        when(tokenValidationService.validateToken(anyString())).thenReturn(true);

        // Create full dependency chain
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Juan Pérez");
        estudiante.setDni("12345678");
        estudiante = estudianteRepository.save(estudiante);
        estudianteId = estudiante.getEstudianteId();

        Facultad facultad = new Facultad();
        facultad.setNombre("Facultad Mat");
        facultad = facultadRepository.save(facultad);

        Carrera carrera = new Carrera();
        carrera.setNombre("Carrera Mat");
        carrera.setFacultad(facultad);
        carrera = carreraRepository.save(carrera);

        Curso curso = new Curso();
        curso.setNombre("Matemáticas I");
        curso.setCarrera(carrera);
        curso = cursoRepository.save(curso);

        Profesor profesor = new Profesor();
        profesor.setNombre("Prof. Rodríguez");
        profesor = profesorRepository.save(profesor);

        Seccion seccion = new Seccion();
        seccion.setCurso(curso);
        seccion.setProfesor(profesor);
        seccion.setCodigo("MAT-A1");
        seccion = seccionRepository.save(seccion);
        seccionId = seccion.getSeccionId();
    }

    @Test
    void createMatricula_ShouldReturnCreated() throws Exception {
        MatriculaDto dto = MatriculaDto.builder()
                .estudianteId(estudianteId)
                .seccionId(seccionId)
                .fechaMatricula(LocalDate.now())
                .estado("ACTIVA")
                .costo(new BigDecimal("1500.00"))
                .build();

        mockMvc.perform(post("/api/matriculas")
                .header("Authorization", "Bearer valid_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("ACTIVA")))
                .andExpect(jsonPath("$.matriculaId", notNullValue()));
    }

    @Test
    void getAllMatriculas_ShouldReturnList() throws Exception {
        createMatricula("ACTIVA", new BigDecimal("1500.00"));
        createMatricula("PENDIENTE", new BigDecimal("1200.00"));

        mockMvc.perform(get("/api/matriculas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].estado", containsInAnyOrder("ACTIVA", "PENDIENTE")));
    }

    @Test
    void getMatriculaById_ShouldReturnMatricula() throws Exception {
        String matriculaId = createMatriculaAndGetId("ACTIVA", new BigDecimal("1500.00"));

        mockMvc.perform(get("/api/matriculas/" + matriculaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("ACTIVA")))
                .andExpect(jsonPath("$.matriculaId", is(Integer.parseInt(matriculaId))));
    }

    @Test
    void updateMatricula_ShouldReturnUpdated() throws Exception {
        String matriculaId = createMatriculaAndGetId("ACTIVA", new BigDecimal("1500.00"));

        MatriculaDto updateDto = MatriculaDto.builder()
                .estudianteId(estudianteId)
                .seccionId(seccionId)
                .estado("COMPLETADA")
                .costo(new BigDecimal("1800.00"))
                .build();

        mockMvc.perform(put("/api/matriculas/" + matriculaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("COMPLETADA")));
    }

    @Test
    void deleteMatricula_ShouldReturnNoContent() throws Exception {
        String matriculaId = createMatriculaAndGetId("ACTIVA", new BigDecimal("1500.00"));

        mockMvc.perform(delete("/api/matriculas/" + matriculaId))
                .andExpect(status().isNoContent());

        // Verify it's deleted
        mockMvc.perform(get("/api/matriculas/" + matriculaId))
                .andExpect(status().isNotFound());
    }

    private void createMatricula(String estado, BigDecimal costo) throws Exception {
        MatriculaDto dto = MatriculaDto.builder()
                .estudianteId(estudianteId)
                .seccionId(seccionId)
                .fechaMatricula(LocalDate.now())
                .estado(estado)
                .costo(costo)
                .build();

        mockMvc.perform(post("/api/matriculas")
                .header("Authorization", "Bearer valid_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    private String createMatriculaAndGetId(String estado, BigDecimal costo) throws Exception {
        MatriculaDto dto = MatriculaDto.builder()
                .estudianteId(estudianteId)
                .seccionId(seccionId)
                .fechaMatricula(LocalDate.now())
                .estado(estado)
                .costo(costo)
                .build();

        String response = mockMvc.perform(post("/api/matriculas")
                .header("Authorization", "Bearer valid_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MatriculaDto created = objectMapper.readValue(response, MatriculaDto.class);
        return created.getMatriculaId().toString();
    }
}
