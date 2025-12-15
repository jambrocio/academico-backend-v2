package pe.edu.university.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.ProfesorDto;
import pe.edu.university.repository.ProfesorRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProfesorControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ProfesorRepository profesorRepository;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        void createProfesor_ShouldReturnCreatedProfesor() throws Exception {
                ProfesorDto dto = ProfesorDto.builder()
                                .nombre("Alan")
                                .apellido("Turing")
                                .dni("99887766")
                                .email("alan@example.com")
                                .build();

                mockMvc.perform(post("/api/profesors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nombre", is("Alan")))
                                .andExpect(jsonPath("$.profesorId", notNullValue()));
        }

        @Test
        void getAllProfesors_ShouldReturnList() throws Exception {
                createProfesor("Grace", "Hopper", "55443322", "grace@example.com");
                createProfesor("Ada", "Lovelace", "11002299", "ada@example.com");

                mockMvc.perform(get("/api/profesors"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].nombre", containsInAnyOrder("Grace", "Ada")));
        }

        private void createProfesor(String nombre, String apellido, String dni, String email) throws Exception {
                ProfesorDto dto = ProfesorDto.builder()
                                .nombre(nombre)
                                .apellido(apellido)
                                .dni(dni)
                                .email(email)
                                .build();

                mockMvc.perform(post("/api/profesors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isOk());
        }
}
