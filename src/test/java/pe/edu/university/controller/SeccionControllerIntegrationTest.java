package pe.edu.university.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.SeccionDto;
import pe.edu.university.entity.*;
import pe.edu.university.repository.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SeccionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

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

    private Long cursoId;
    private Long profesorId;

    @BeforeEach
    void setUp() {
        Facultad facultad = new Facultad();
        facultad.setNombre("Facultad Seccion");
        facultad = facultadRepository.save(facultad);

        Carrera carrera = new Carrera();
        carrera.setNombre("Carrera Seccion");
        carrera.setFacultad(facultad);
        carrera = carreraRepository.save(carrera);

        Curso curso = new Curso();
        curso.setNombre("Matemáticas");
        curso.setCarrera(carrera);
        curso = cursoRepository.save(curso);
        cursoId = curso.getCursoId();

        Profesor profesor = new Profesor();
        profesor.setNombre("Prof. García");
        profesor = profesorRepository.save(profesor);
        profesorId = profesor.getProfesorId();
    }

    @Test
    void createSeccion_ShouldReturnCreated() throws Exception {
        SeccionDto dto = SeccionDto.builder()
                .cursoId(cursoId)
                .profesorId(profesorId)
                .codigo("SEC-001")
                .capacidadMaxima(30)
                .build();

        mockMvc.perform(post("/api/seccions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo", is("SEC-001")))
                .andExpect(jsonPath("$.seccionId", notNullValue()));
    }

    @Test
    void getAllSeccions_ShouldReturnList() throws Exception {
        createSeccion("SEC-A", 25);
        createSeccion("SEC-B", 30);

        mockMvc.perform(get("/api/seccions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].codigo", containsInAnyOrder("SEC-A", "SEC-B")));
    }

    private void createSeccion(String codigo, Integer capacidad) throws Exception {
        SeccionDto dto = SeccionDto.builder()
                .cursoId(cursoId)
                .profesorId(profesorId)
                .codigo(codigo)
                .capacidadMaxima(capacidad)
                .build();

        mockMvc.perform(post("/api/seccions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
