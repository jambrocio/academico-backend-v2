package pe.edu.university.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.university.dto.CarreraDto;
import pe.edu.university.entity.Carrera;
import pe.edu.university.entity.Facultad;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.CarreraMapper;
import pe.edu.university.repository.CarreraRepository;
import pe.edu.university.repository.FacultadRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarreraServiceImplTest {

    @Mock
    private CarreraRepository repository;

    @Mock
    private CarreraMapper mapper;

    @Mock
    private FacultadRepository facultadRepository;

    @InjectMocks
    private CarreraServiceImpl service;

    private Carrera carrera;
    private CarreraDto carreraDto;
    private Facultad facultad;

    @BeforeEach
    void setUp() {
        facultad = new Facultad();
        facultad.setFacultadId(1L);
        facultad.setNombre("Ingeniería");

        carrera = new Carrera();
        carrera.setCarreraId(1L);
        carrera.setNombre("Ing. Sistemas");
        carrera.setFacultad(facultad);

        carreraDto = CarreraDto.builder()
                .carreraId(1L)
                .nombre("Ing. Sistemas")
                .duracionSemestres(10)
                .facultadId(1L)
                .build();
    }

    @Test
    void create_WithFacultadId_ShouldSaveCarrera() {
        when(mapper.toEntity(any(CarreraDto.class))).thenReturn(carrera);
        when(facultadRepository.findById(1L)).thenReturn(Optional.of(facultad));
        when(repository.save(any(Carrera.class))).thenReturn(carrera);
        when(mapper.toDto(any(Carrera.class))).thenReturn(carreraDto);

        CarreraDto result = service.create(carreraDto);

        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Ing. Sistemas");
        verify(facultadRepository).findById(1L);
        verify(repository).save(any(Carrera.class));
    }

    @Test
    void create_WithInvalidFacultadId_ShouldThrowException() {
        when(mapper.toEntity(any(CarreraDto.class))).thenReturn(carrera);
        when(facultadRepository.findById(999L)).thenReturn(Optional.empty());

        CarreraDto dtoWithInvalidFacultad = CarreraDto.builder()
                .nombre("Test")
                .facultadId(999L)
                .build();

        assertThatThrownBy(() -> service.create(dtoWithInvalidFacultad))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findById_WhenExists_ShouldReturnCarrera() {
        when(repository.findById(1L)).thenReturn(Optional.of(carrera));
        when(mapper.toDto(carrera)).thenReturn(carreraDto);

        CarreraDto result = service.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getCarreraId()).isEqualTo(1L);
    }

    @Test
    void findById_WhenNotExists_ShouldThrowException() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findAll_ShouldReturnAllCarreras() {
        when(repository.findAll()).thenReturn(Arrays.asList(carrera));
        when(mapper.toDto(any(Carrera.class))).thenReturn(carreraDto);

        List<CarreraDto> result = service.findAll();

        assertThat(result).hasSize(1);
        verify(repository).findAll();
    }

    @Test
    void update_ShouldUpdateCarrera() {
        CarreraDto updateDto = CarreraDto.builder()
                .nombre("Ing. Sistemas Updated")
                .duracionSemestres(12)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(carrera));
        when(repository.save(any(Carrera.class))).thenReturn(carrera);
        when(mapper.toDto(any(Carrera.class))).thenReturn(updateDto);

        CarreraDto result = service.update(1L, updateDto);

        assertThat(result).isNotNull();
        verify(repository).save(any(Carrera.class));
    }

    @Test
    void delete_WhenExists_ShouldDelete() {
        when(repository.findById(1L)).thenReturn(Optional.of(carrera));

        service.delete(1L);

        verify(repository).delete(carrera);
    }
}
