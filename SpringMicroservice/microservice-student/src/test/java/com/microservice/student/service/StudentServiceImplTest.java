package com.microservice.student.service;

import com.microservice.student.entities.Student;
import com.microservice.student.persistence.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {


    // Declara el objeto log para que se pueda usar en todo el código
    private static final Logger log = LoggerFactory.getLogger(StudentServiceImplTest.class);

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void testFindAll() {
        log.info("INICIO PRUEBA: testFindAll - Consultar todos con registros");

        Student student = new Student();
        student.setId(1L);
        student.setName("Jose");
        log.debug("Creado estudiante de prueba: {}", student);

        when(studentRepository.findAll())
                .thenReturn(List.of(student));

        log.debug("Ejecutando servicio: findAll()");
        List<Student> result = studentService.findAll();

        log.debug("Verificando que el tamaño de la lista sea 1");
        assertEquals(1, result.size());

        log.debug("Verificando que se llamó al método findAll del repositorio");
        verify(studentRepository).findAll();

        log.info("FIN PRUEBA: testFindAll - ✅ PASÓ\n");
    }

    @Test
    void testFindById() {
        log.info("INICIO PRUEBA: testFindById - Buscar estudiante existente por ID");

        Student student = new Student();
        student.setId(1L);
        student.setName("Jose");
        log.debug("Creado estudiante de prueba: ID={}, Nombre={}", student.getId(), student.getName());

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        log.debug("Ejecutando servicio: findById(1L)");
        Student result = studentService.findById(1L);

        log.debug("Verificando que el nombre obtenido sea 'Jose'");
        assertEquals("Jose", result.getName());

        log.debug("Verificando llamada al repositorio");
        verify(studentRepository).findById(1L);

        log.info("FIN PRUEBA: testFindById - ✅ PASÓ\n");
    }

    @Test
    void testSave() {
        log.info("INICIO PRUEBA: testSave - Guardar nuevo estudiante");

        Student student = new Student();
        student.setName("Jose");
        log.debug("Datos del estudiante a guardar: {}", student);

        log.debug("Ejecutando servicio: save()");
        studentService.save(student);

        log.debug("Verificando que se llamó al método save del repositorio con el objeto correcto");
        verify(studentRepository).save(student);

        log.info("FIN PRUEBA: testSave - ✅ PASÓ\n");
    }

    @Test
    void testFindByCourseId() {
        log.info("INICIO PRUEBA: testFindByCourseId - Buscar estudiantes por Curso (con resultados)");

        Student student = new Student();
        student.setId(1L);
        student.setCourseId(100L);
        log.debug("Estudiante de prueba asignado al curso: {}", student.getCourseId());

        when(studentRepository.findAllByCourseId(100L))
                .thenReturn(List.of(student));

        log.debug("Ejecutando servicio: findByCourseId(100L)");
        List<Student> result =
                studentService.findByCourseId(100L);

        log.debug("Verificando cantidad de resultados = 1");
        assertEquals(1, result.size());

        log.debug("Verificando llamada al repositorio con ID de curso 100");
        verify(studentRepository)
                .findAllByCourseId(100L);

        log.info("FIN PRUEBA: testFindByCourseId - ✅ PASÓ\n");
    }

    @Test
    void testFindByIdNotFound() {
        log.info("INICIO PRUEBA: testFindByIdNotFound - Buscar ID que NO existe (esperando error)");

        log.debug("Simulando repositorio: findById(99L) -> Optional vacío (no existe)");
        when(studentRepository.findById(99L))
                .thenReturn(Optional.empty());

        log.debug("Ejecutando servicio y esperando que lance RuntimeException...");
        assertThrows(RuntimeException.class,
                () -> studentService.findById(99L));

        log.debug("Excepción lanzada correctamente. Verificando llamada al repositorio");
        verify(studentRepository).findById(99L);

        log.info("FIN PRUEBA: testFindByIdNotFound - ✅ PASÓ\n");
    }

    @Test
    void testFindAllEmpty() {
        log.info("INICIO PRUEBA: testFindAllEmpty - Consultar todos cuando NO hay registros");

        log.debug("Simulando repositorio: findAll() -> Lista vacía");
        when(studentRepository.findAll())
                .thenReturn(List.of());

        log.debug("Ejecutando servicio: findAll()");
        List<Student> result = studentService.findAll();

        log.debug("Verificando que la lista devuelta esté vacía");
        assertTrue(result.isEmpty());

        log.debug("Verificando llamada al repositorio");
        verify(studentRepository).findAll();

        log.info("FIN PRUEBA: testFindAllEmpty - ✅ PASÓ\n");
    }

    @Test
    void testFindByCourseIdEmpty() {
        log.info("INICIO PRUEBA: testFindByCourseIdEmpty - Buscar por Curso cuando NO hay alumnos");

        log.debug("Simulando repositorio: findAllByCourseId(500L) -> Lista vacía");
        when(studentRepository.findAllByCourseId(500L))
                .thenReturn(List.of());

        log.debug("Ejecutando servicio: findByCourseId(500L)");
        List<Student> result =
                studentService.findByCourseId(500L);

        log.debug("Verificando que el tamaño sea 0");
        assertEquals(0, result.size());

        log.debug("Verificando llamada al repositorio");
        verify(studentRepository)
                .findAllByCourseId(500L);

        log.info("FIN PRUEBA: testFindByCourseIdEmpty - ✅ PASÓ\n");
    }

    @Test
    void testStudentData() {
        log.info("INICIO PRUEBA: testStudentData - Verificar que TODOS los datos se devuelvan correctamente");

        Student student = new Student();
        student.setId(1L);
        student.setName("Jose");
        student.setLastName("Vazquez");
        student.setEmail("jose@gmail.com");
        student.setCourseId(100L);
        log.debug("Objeto de prueba creado con datos completos: {}", student);

        log.debug("Simulando repositorio: findById(1L) -> Retorna el objeto completo");
        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        log.debug("Ejecutando servicio: findById(1L)");
        Student result = studentService.findById(1L);

        log.debug("Verificando campo: Nombre");
        assertEquals("Jose", result.getName());
        log.debug("Verificando campo: Apellido");
        assertEquals("Vazquez", result.getLastName());
        log.debug("Verificando campo: Email");
        assertEquals("jose@gmail.com", result.getEmail());
        log.debug("Verificando campo: ID Curso");
        assertEquals(100L, result.getCourseId());

        log.info("FIN PRUEBA: testStudentData - ✅ PASÓ\n");
    }
    /// //
    @Test
    void testFindByIdExistente() {
        log.info("INICIO: Buscar ID=5");

        Student s = new Student();
        s.setId(5L);
        s.setName("Ana");
        s.setLastName("Gómez");
        // puedes agregar email y courseId si los necesitas
        log.debug("Estudiante: {}", s);

        when(studentRepository.findById(5L)).thenReturn(Optional.of(s));
        log.debug("Simulación lista");

        Student res = studentService.findById(5L);
        log.debug("Respuesta: {}", res.getName());

        assertEquals("Ana", res.getName());
        verify(studentRepository).findById(5L);

        log.info("FIN: Correcto ✅");
    }

    @Test
    void testSaveStudent() {
        log.info("INICIO: Guardar estudiante");

        // ✅ CORREGIDO
        Student nuevo = new Student();
        nuevo.setName("Luis");
        nuevo.setLastName("Martínez");
        // si necesitas: nuevo.setEmail("..."); nuevo.setCourseId(...);

        log.debug("Datos a guardar: {}", nuevo);

        studentService.save(nuevo);
        log.debug("Ejecutado");

        verify(studentRepository).save(nuevo);
        log.debug("Llamada al repo verificada");

        log.info("FIN: Correcto ✅");
    }

    @Test
    void testFindByIdNoExiste() {
        log.info("INICIO: Buscar ID=999 (NO existe)");

        Long id = 999L;
        when(studentRepository.findById(id)).thenReturn(Optional.empty());
        log.debug("Repo vacío");

        RuntimeException err = assertThrows(RuntimeException.class, () -> {
            studentService.findById(id);
        });
        log.debug("Error capturado: {}", err.getMessage());

        verify(studentRepository).findById(id);
        log.info("FIN: Error esperado ✅");
    }

    @Test
    void testBuscarPorCurso() {
        log.info("INICIO: Buscar curso ID=50");

        Long curso = 50L;

        // ✅ CORREGIDO: Crear objetos con constructor vacío + setters
        Student s1 = new Student();
        s1.setId(10L);
        s1.setName("Pedro");
        s1.setCourseId(curso);

        Student s2 = new Student();
        s2.setId(20L);
        s2.setName("María");
        s2.setCourseId(curso);

        List<Student> lista = List.of(s1, s2);
        log.debug("Datos creados: {}", lista.size());

        when(studentRepository.findAllByCourseId(curso)).thenReturn(lista);
        log.debug("Simulación lista");

        // ⚠️ IMPORTANTE: Asegúrate de tener creado este método en tu servicio
        List<Student> res = studentService.findAllByCourseId(curso);
        log.debug("Resultado: {}", res.size());

        assertEquals(2, res.size());
        verify(studentRepository).findAllByCourseId(curso);

        log.info("FIN: Correcto ✅");
    }

    }
