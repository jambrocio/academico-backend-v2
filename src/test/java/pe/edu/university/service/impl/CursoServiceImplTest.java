package pe.edu.university.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.university.dto.CursoDto;
import pe.edu.university.entity.Carrera;
import pe.edu.university.entity.Curso;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.CursoMapper;
import pe.edu.university.repository.CarreraRepository;
import pe.edu.university.repository.CursoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CursoServiceImplTest {

    @Mock
    private CursoRepository repository;

    @Mock
    private CursoMapper mapper;

    @Mock
    private CarreraRepository carreraRepository;

    @InjectMocks
    private CursoServiceImpl service;

    private Curso curso;
    private CursoDto cursoDto;
    private Carrera carrera;

    @BeforeEach
    void setUp() {
        carrera = new Carrera();
        carrera.setCarreraId(1L);
        carrera.setNombre("Ing. Sistemas");

        curso = new Curso();
        curso.setCursoId(1L);
        curso.setNombre("Matemáticas");
        curso.setCarrera(carrera);

        cursoDto = CursoDto.builder()
                .cursoId(1L)
                .nombre("Matemáticas")
                .creditos(4)
                .carreraId(1L)
                .build();
    }

    @Test
    void create_WithCarreraId_ShouldSaveCurso() {
        when(mapper.toEntity(any(CursoDto.class))).thenReturn(curso);
        when(carreraRepository.findById(1L)).thenReturn(Optional.of(carrera));
        when(repository.save(any(Curso.class))).thenReturn(curso);
        when(mapper.toDto(any(Curso.class))).thenReturn(cursoDto);

        CursoDto result = service.create(cursoDto);

        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Matemáticas");
        verify(carreraRepository).findById(1L);
        verify(repository).save(any(Curso.class));
    }

    @Test
    void findById_WhenExists_ShouldReturnCurso() {
        when(repository.findById(1L)).thenReturn(Optional.of(curso));
        when(mapper.toDto(curso)).thenReturn(cursoDto);

        CursoDto result = service.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getCursoId()).isEqualTo(1L);
    }

    @Test
    void findById_WhenNotExists_ShouldThrowException() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findAll_ShouldReturnAllCursos() {
        when(repository.findAll()).thenReturn(Arrays.asList(curso));
        when(mapper.toDto(any(Curso.class))).thenReturn(cursoDto);

        List<CursoDto> result = service.findAll();

        assertThat(result).hasSize(1);
        verify(repository).findAll();
    }

    @Test
    void delete_WhenExists_ShouldDelete() {
        when(repository.findById(1L)).thenReturn(Optional.of(curso));

        service.delete(1L);

        verify(repository).delete(curso);
    }
}
