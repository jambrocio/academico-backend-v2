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
import pe.edu.university.dto.CalificacionDto;
import pe.edu.university.entity.Curso;
import pe.edu.university.entity.Estudiante;
import pe.edu.university.entity.Matricula;
import pe.edu.university.entity.Seccion;
import pe.edu.university.repository.CursoRepository;
import pe.edu.university.repository.EstudianteRepository;
import pe.edu.university.repository.MatriculaRepository;
import pe.edu.university.repository.SeccionRepository;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CalificacionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MatriculaRepository matriculaRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private SeccionRepository seccionRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long matriculaId;

    @Autowired
    private pe.edu.university.repository.ProfesorRepository profesorRepository;
    @Autowired
    private pe.edu.university.repository.FacultadRepository facultadRepository;
    @Autowired
    private pe.edu.university.repository.CarreraRepository carreraRepository;

    @BeforeEach
    void setUp() {
        // Create dependencies
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Estudiante Calif");
        estudiante.setDni("99988877");
        estudiante = estudianteRepository.save(estudiante);

        pe.edu.university.entity.Facultad facultad = new pe.edu.university.entity.Facultad();
        facultad.setNombre("Facultad Test");
        facultad = facultadRepository.save(facultad);

        pe.edu.university.entity.Carrera carrera = new pe.edu.university.entity.Carrera();
        carrera.setNombre("Carrera Test");
        carrera.setFacultad(facultad);
        carrera = carreraRepository.save(carrera);

        Curso curso = new Curso();
        curso.setNombre("Curso Calif");
        curso.setCarrera(carrera);
        curso = cursoRepository.save(curso);

        pe.edu.university.entity.Profesor profesor = new pe.edu.university.entity.Profesor();
        profesor.setNombre("Profe Test");
        profesor = profesorRepository.save(profesor);

        Seccion seccion = new Seccion();
        seccion.setCurso(curso);
        seccion.setProfesor(profesor);
        seccion = seccionRepository.save(seccion);

        Matricula matricula = new Matricula();
        matricula.setEstudiante(estudiante);
        matricula.setSeccion(seccion);
        matricula = matriculaRepository.save(matricula);

        matriculaId = matricula.getMatriculaId();
    }

    @Test
    void createCalificacion_ShouldReturnCreated() throws Exception {
        CalificacionDto dto = CalificacionDto.builder()
                .matriculaId(matriculaId)
                .nota(new BigDecimal("18.5"))
                .observacion("Excelente")
                .build();

        mockMvc.perform(post("/api/calificacions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.observacion", is("Excelente")))
                .andExpect(jsonPath("$.calificacionId", notNullValue()));
    }

    @Test
    void getAllCalificacions_ShouldReturnList() throws Exception {
        createCalificacion(new BigDecimal("15"), "Good");

        mockMvc.perform(get("/api/calificacions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].observacion", hasItem("Good")));
    }

    private void createCalificacion(BigDecimal nota, String obs) throws Exception {
        CalificacionDto dto = CalificacionDto.builder()
                .matriculaId(matriculaId)
                .nota(nota)
                .observacion(obs)
                .build();

        mockMvc.perform(post("/api/calificacions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
