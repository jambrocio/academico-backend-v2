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
import pe.edu.university.dto.CarreraDto;
import pe.edu.university.entity.Facultad;
import pe.edu.university.repository.FacultadRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CarreraControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long facultadId;

    @BeforeEach
    void setUp() {
        // Create a Facultad first as prerequisite
        Facultad facultad = new Facultad();
        facultad.setNombre("Ingeniería");
        facultad = facultadRepository.save(facultad);
        facultadId = facultad.getFacultadId();
    }

    @Test
    void createCarrera_ShouldReturnCreated() throws Exception {
        CarreraDto dto = CarreraDto.builder()
                .nombre("Ingeniería de Sistemas")
                .duracionSemestres(10)
                .facultadId(facultadId)
                .build();

        mockMvc.perform(post("/api/carreras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Ingeniería de Sistemas")))
                .andExpect(jsonPath("$.carreraId", notNullValue()));
    }

    @Test
    void getAllCarreras_ShouldReturnList() throws Exception {
        createCarrera("Ing. Civil", 10);
        createCarrera("Ing. Industrial", 10);

        mockMvc.perform(get("/api/carreras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].nombre", containsInAnyOrder("Ing. Civil", "Ing. Industrial")));
    }

    private void createCarrera(String nombre, Integer duracion) throws Exception {
        CarreraDto dto = CarreraDto.builder()
                .nombre(nombre)
                .duracionSemestres(duracion)
                .facultadId(facultadId)
                .build();

        mockMvc.perform(post("/api/carreras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
