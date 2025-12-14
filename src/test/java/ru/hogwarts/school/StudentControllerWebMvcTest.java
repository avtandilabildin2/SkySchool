package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@Import(StudentServiceImpl.class)
class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addStudentTest() throws Exception {
        Student studentToSave = new Student();
        studentToSave.setName("Harry");
        studentToSave.setAge(18);

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setName("Harry");
        savedStudent.setAge(18);

        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        mockMvc.perform(post("/students/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentToSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Harry"));
    }

    @Test
    void findStudentTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Ron");
        student.setAge(17);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/find/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ron"));
    }

    @Test
    void updateStudentTest() throws Exception {
        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        updatedStudent.setName("Hermione");
        updatedStudent.setAge(19);
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student()));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/students/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hermione"))
                .andExpect(jsonPath("$.age").value(19));
    }

    @Test
    void deleteStudentTest() throws Exception {
        // Для этого теста не нужно настраивать `when`, так как метод deleteById
        // ничего не возвращает. Мы просто проверяем, что он был вызван.
        mockMvc.perform(delete("/students/delete/1"))
                .andExpect(status().isOk());

        // Проверяем, что сервис вызвал у репозитория метод deleteById ровно 1 раз с аргументом 1L
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void filterByAgeTest() throws Exception {
        Student student = new Student();
        student.setAge(18);
        student.setName("Harry");

        // ВТОРАЯ ПРОВЕРКА И ИСПРАВЛЕНИЕ:
        // Теперь, когда я видел ваш StudentRepository, я использую правильное имя метода: `findAllByAge`.
        when(studentRepository.findAllByAge(18)).thenReturn(List.of(student));

        mockMvc.perform(get("/students/filter")
                        .param("age", "18"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Harry"));
    }

    @Test
    void filterByAgeBetweenTest() throws Exception {
        // --- Подготовка (Arrange) ---
        Student student17 = new Student();
        student17.setName("Ron");
        student17.setAge(17);

        // Настраиваем мок-репозиторий, используя метод `findByAgeBetween` из вашего репозитория.
        when(studentRepository.findByAgeBetween(16, 20)).thenReturn(List.of(student17));

        // --- Действие (Act) & Проверка (Assert) ---
        mockMvc.perform(get("/students/age-between")
                        .param("min", "16")
                        .param("max", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Ron"));
    }

    @Test
    void getStudentFacultyTest() throws Exception {
        // --- Подготовка (Arrange) ---
        Faculty faculty = new Faculty();
        faculty.setId(2L);
        faculty.setName("Gryffindor");

        Student student = new Student();
        student.setId(1L);
        student.setName("Harry");
        student.setFaculty(faculty);

        // Логика получения факультета студента:
        // 1. Сервис находит студента по ID.
        // 2. Сервис берет у студента объект факультета.
        // Поэтому нам нужно настроить мок `findById`
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // --- Действие (Act) & Проверка (Assert) ---
        mockMvc.perform(get("/students/1/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }
}
