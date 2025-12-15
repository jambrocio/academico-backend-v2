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
import pe.edu.university.dto.CursoDto;
import pe.edu.university.entity.Carrera;
import pe.edu.university.entity.Facultad;
import pe.edu.university.repository.CarreraRepository;
import pe.edu.university.repository.CursoRepository;
import pe.edu.university.repository.FacultadRepository;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CursoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long carreraId;

    @BeforeEach
    void setUp() {
        cursoRepository.deleteAll();
        carreraRepository.deleteAll();
        facultadRepository.deleteAll();

        Facultad facultad = Facultad.builder()
                .nombre("Ingenieria")
                .decano("Dr. Ingeniero")
                .activo(true)
                .build();
        facultad = facultadRepository.save(facultad);

        Carrera carrera = Carrera.builder()
                .nombre("Sistemas")
                .facultad(facultad)
                .activo(true)
                .build();
        carrera = carreraRepository.save(carrera);
        carreraId = carrera.getCarreraId();
    }

    @Test
    void createCurso_ShouldReturnCreatedCurso() throws Exception {
        CursoDto dto = CursoDto.builder()
                .nombre("Algoritmos")
                .codigo("ALG101")
                .creditos(4)
                .carreraId(carreraId)
                .build();

        mockMvc.perform(post("/api/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Algoritmos")))
                .andExpect(jsonPath("$.cursoId", notNullValue()));
    }

    @Test
    void getAllCursos_ShouldReturnList() throws Exception {
        createCurso("Fisica I", "FIS101", 3);
        createCurso("Matematica I", "MAT101", 4);

        mockMvc.perform(get("/api/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].nombre", containsInAnyOrder("Fisica I", "Matematica I")));
    }

    private void createCurso(String nombre, String codigo, Integer creditos) throws Exception {
        CursoDto dto = CursoDto.builder()
                .nombre(nombre)
                .codigo(codigo)
                .creditos(creditos)
                .carreraId(carreraId)
                .build();

        mockMvc.perform(post("/api/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
