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
import pe.edu.university.dto.PagoDto;
import pe.edu.university.entity.*;
import pe.edu.university.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PagoControllerIntegrationTest {

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
    private FacultadRepository facultadRepository;
    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long matriculaId;

    @BeforeEach
    void setUp() {
        // Create full dependency chain
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Estudiante Pago");
        estudiante.setDni("77665544");
        estudiante = estudianteRepository.save(estudiante);

        Facultad facultad = new Facultad();
        facultad.setNombre("Facultad Pago");
        facultad = facultadRepository.save(facultad);

        Carrera carrera = new Carrera();
        carrera.setNombre("Carrera Pago");
        carrera.setFacultad(facultad);
        carrera = carreraRepository.save(carrera);

        Curso curso = new Curso();
        curso.setNombre("Curso Pago");
        curso.setCarrera(carrera);
        curso = cursoRepository.save(curso);

        Profesor profesor = new Profesor();
        profesor.setNombre("Profesor Pago");
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
    void createPago_ShouldReturnCreated() throws Exception {
        PagoDto dto = PagoDto.builder()
                .matriculaId(matriculaId)
                .monto(new BigDecimal("500.00"))
                .metodoPago("TARJETA")
                .fechaPago(LocalDate.now())
                .build();

        mockMvc.perform(post("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metodoPago", is("TARJETA")))
                .andExpect(jsonPath("$.pagoId", notNullValue()));
    }

    @Test
    void getAllPagos_ShouldReturnList() throws Exception {
        createPago(new BigDecimal("300.00"), "EFECTIVO");
        createPago(new BigDecimal("700.00"), "TRANSFERENCIA");

        mockMvc.perform(get("/api/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].metodoPago", hasItem("EFECTIVO")));
    }

    private void createPago(BigDecimal monto, String metodo) throws Exception {
        PagoDto dto = PagoDto.builder()
                .matriculaId(matriculaId)
                .monto(monto)
                .metodoPago(metodo)
                .fechaPago(LocalDate.now())
                .build();

        mockMvc.perform(post("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
