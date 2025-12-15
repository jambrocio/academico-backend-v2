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
import pe.edu.university.dto.PrerrequisitoDto;
import pe.edu.university.entity.*;
import pe.edu.university.repository.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PrerrequisitoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private FacultadRepository facultadRepository;
    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long cursoId;
    private Long prereqCursoId;

    @BeforeEach
    void setUp() {
        Facultad facultad = new Facultad();
        facultad.setNombre("Facultad Prereq");
        facultad = facultadRepository.save(facultad);

        Carrera carrera = new Carrera();
        carrera.setNombre("Carrera Prereq");
        carrera.setFacultad(facultad);
        carrera = carreraRepository.save(carrera);

        Curso curso1 = new Curso();
        curso1.setNombre("Cálculo II");
        curso1.setCarrera(carrera);
        curso1 = cursoRepository.save(curso1);
        cursoId = curso1.getCursoId();

        Curso curso2 = new Curso();
        curso2.setNombre("Cálculo I");
        curso2.setCarrera(carrera);
        curso2 = cursoRepository.save(curso2);
        prereqCursoId = curso2.getCursoId();
    }

    @Test
    void createPrerrequisito_ShouldReturnCreated() throws Exception {
        PrerrequisitoDto dto = PrerrequisitoDto.builder()
                .cursoId(cursoId)
                .cursoReqId(prereqCursoId)
                .build();

        mockMvc.perform(post("/api/prerrequisitos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prerrequisitoId", notNullValue()));
    }

    @Test
    void getAllPrerrequisitos_ShouldReturnList() throws Exception {
        createPrerrequisito(cursoId, prereqCursoId);

        mockMvc.perform(get("/api/prerrequisitos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    private void createPrerrequisito(Long curso, Long prereq) throws Exception {
        PrerrequisitoDto dto = PrerrequisitoDto.builder()
                .cursoId(curso)
                .cursoReqId(prereq)
                .build();

        mockMvc.perform(post("/api/prerrequisitos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
