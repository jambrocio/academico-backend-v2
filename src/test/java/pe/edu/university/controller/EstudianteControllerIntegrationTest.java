package pe.edu.university.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pe.edu.university.dto.EstudianteDto;
import pe.edu.university.repository.EstudianteRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@org.springframework.transaction.annotation.Transactional
class EstudianteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Transactional will rollback changes
    }

    @Test
    void createEstudiante_ShouldReturnCreatedEstudiante() throws Exception {
        EstudianteDto dto = EstudianteDto.builder()
                .nombre("Juan")
                .apellido("Perez")
                .dni("12345678")
                .email("juan.perez@example.com")
                .build();

        mockMvc.perform(post("/api/estudiantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Juan")))
                .andExpect(jsonPath("$.apellido", is("Perez")))
                .andExpect(jsonPath("$.dni", is("12345678")))
                .andExpect(jsonPath("$.email", is("juan.perez@example.com")))
                .andExpect(jsonPath("$.estudianteId", notNullValue()));
    }

    @Test
    void getAllEstudiantes_ShouldReturnList() throws Exception {
        createEstudiante("Maria", "Lopez", "87654321", "maria@example.com");
        createEstudiante("Carlos", "Gomez", "11223344", "carlos@example.com");

        mockMvc.perform(get("/api/estudiantes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].nombre", containsInAnyOrder("Maria", "Carlos")));
    }

    @Test
    void getEstudianteById_ShouldReturnEstudiante() throws Exception {
        EstudianteDto saved = createEstudiante("Ana", "Torres", "55555555", "ana@example.com");

        mockMvc.perform(get("/api/estudiantes/{id}", saved.getEstudianteId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Ana")))
                .andExpect(jsonPath("$.dni", is("55555555")));
    }

    @Test
    void updateEstudiante_ShouldReturnUpdatedEstudiante() throws Exception {
        EstudianteDto saved = createEstudiante("Luis", "Diaz", "99999999", "luis@example.com");

        saved.setNombre("Luis Updated");
        saved.setEmail("luis.updated@example.com");

        mockMvc.perform(put("/api/estudiantes/{id}", saved.getEstudianteId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saved)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Luis Updated")))
                .andExpect(jsonPath("$.email", is("luis.updated@example.com")));
    }

    @Test
    void deleteEstudiante_ShouldReturnNoContent() throws Exception {
        EstudianteDto saved = createEstudiante("Pedro", "Ruiz", "44444444", "pedro@example.com");

        mockMvc.perform(delete("/api/estudiantes/{id}", saved.getEstudianteId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/estudiantes/{id}", saved.getEstudianteId()))
                .andExpect(status().isNotFound()); // Asuming exception handler returns 404
    }

    // Helper using the service logic effectively via controller or simpler
    // repository save if mapped properly.
    // However, since we are in integration test, lets use the repository directly
    // if we can map it back to DTO
    // OR just use the controller to create initial data to ensure full stack is
    // consistent.
    // But for "createEstudiante" helper here I will use the MockMvc call or
    // Repository save.
    // Using MockMvc is cleaner for "black box" but verbose. Using Repository
    // requires Entity access.
    // Let's use simple MockMvc perform for helper to keep it consistent or
    // Repository.
    // Actually, I can use Repository to save Entity. I need to map it manually or
    // use constructor.
    // EstudianteDto -> saved via Repository -> return Dto with ID.

    private EstudianteDto createEstudiante(String nombre, String apellido, String dni, String email) throws Exception {
        // Since I don't want to import Entity here if I can avoid it or I can just use
        // the controller endpoint.
        // Using controller endpoint to seed data is valid for integration tests.
        EstudianteDto dto = EstudianteDto.builder()
                .nombre(nombre)
                .apellido(apellido)
                .dni(dni)
                .email(email)
                .build();

        String response = mockMvc.perform(post("/api/estudiantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(response, EstudianteDto.class);
    }
}
