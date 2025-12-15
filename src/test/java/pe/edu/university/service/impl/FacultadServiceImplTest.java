package pe.edu.university.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.university.dto.FacultadDto;
import pe.edu.university.entity.Facultad;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.FacultadMapper;
import pe.edu.university.repository.FacultadRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultadServiceImplTest {

    @Mock
    private FacultadRepository repository;

    @Mock
    private FacultadMapper mapper;

    @InjectMocks
    private FacultadServiceImpl service;

    private Facultad facultad;
    private FacultadDto facultadDto;

    @BeforeEach
    void setUp() {
        facultad = new Facultad();
        facultad.setFacultadId(1L);
        facultad.setNombre("Ingeniería");
        facultad.setDescripcion("Facultad de Ingeniería");

        facultadDto = FacultadDto.builder()
                .facultadId(1L)
                .nombre("Ingeniería")
                .descripcion("Facultad de Ingeniería")
                .build();
    }

    @Test
    void create_ShouldSaveFacultad() {
        when(mapper.toEntity(any(FacultadDto.class))).thenReturn(facultad);
        when(repository.save(any(Facultad.class))).thenReturn(facultad);
        when(mapper.toDto(any(Facultad.class))).thenReturn(facultadDto);

        FacultadDto result = service.create(facultadDto);

        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Ingeniería");
        verify(repository).save(any(Facultad.class));
    }

    @Test
    void findById_WhenExists_ShouldReturnFacultad() {
        when(repository.findById(1L)).thenReturn(Optional.of(facultad));
        when(mapper.toDto(facultad)).thenReturn(facultadDto);

        FacultadDto result = service.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getFacultadId()).isEqualTo(1L);
        verify(repository).findById(1L);
    }

    @Test
    void findById_WhenNotExists_ShouldThrowException() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findAll_ShouldReturnAllFacultades() {
        List<Facultad> facultades = Arrays.asList(facultad);
        when(repository.findAll()).thenReturn(facultades);
        when(mapper.toDto(any(Facultad.class))).thenReturn(facultadDto);

        List<FacultadDto> result = service.findAll();

        assertThat(result).hasSize(1);
        verify(repository).findAll();
    }

    @Test
    void update_ShouldUpdateFacultad() {
        FacultadDto updateDto = FacultadDto.builder()
                .nombre("Ingeniería Updated")
                .descripcion("Nueva descripción")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(facultad));
        when(repository.save(any(Facultad.class))).thenReturn(facultad);
        when(mapper.toDto(any(Facultad.class))).thenReturn(updateDto);

        FacultadDto result = service.update(1L, updateDto);

        assertThat(result).isNotNull();
        verify(repository).save(any(Facultad.class));
    }

    @Test
    void delete_WhenExists_ShouldDelete() {
        when(repository.findById(1L)).thenReturn(Optional.of(facultad));

        service.delete(1L);

        verify(repository).delete(facultad);
    }

    @Test
    void delete_WhenNotExists_ShouldThrowException() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
