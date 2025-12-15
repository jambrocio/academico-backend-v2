package pe.edu.university.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.FacultadDto;
import pe.edu.university.repository.FacultadRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FacultadControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private FacultadRepository facultadRepository;

        @Autowired
        private ObjectMapper objectMapper;

        @org.springframework.boot.test.mock.mockito.MockBean
        private pe.edu.university.security.TokenValidationService tokenValidationService;

        @org.junit.jupiter.api.BeforeEach
        void setUp() {
                // Mock token validation to always return true for all tests
                org.mockito.Mockito.when(tokenValidationService.validateToken(org.mockito.ArgumentMatchers.anyString()))
                                .thenReturn(true);
        }

        @Test
        void createFacultad_ShouldReturnCreatedFacultad() throws Exception {
                FacultadDto dto = FacultadDto.builder()
                                .nombre("Ciencias")
                                .descripcion("Facultad de Ciencias")
                                .build();

                mockMvc.perform(post("/api/facultads")
                                .header("Authorization", "Bearer valid_token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nombre", is("Ciencias")))
                                .andExpect(jsonPath("$.facultadId", notNullValue()));
        }

        @Test
        void getAllFacultads_ShouldReturnList() throws Exception {
                createFacultad("Medicina", "Facultad de Medicina");
                createFacultad("Derecho", "Facultad de Derecho");

                mockMvc.perform(get("/api/facultads"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[*].nombre", containsInAnyOrder("Medicina", "Derecho")));
        }

        private void createFacultad(String nombre, String descripcion) throws Exception {
                FacultadDto dto = FacultadDto.builder()
                                .nombre(nombre)
                                .descripcion(descripcion)
                                .build();

                mockMvc.perform(post("/api/facultads")
                                .header("Authorization", "Bearer valid_token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isOk());
        }
}
