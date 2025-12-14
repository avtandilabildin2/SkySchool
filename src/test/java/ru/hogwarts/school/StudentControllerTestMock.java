package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTestMock {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addStudentTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Harry");
        student.setAge(18);

        when(studentService.createStudent(any(Student.class)))
                .thenReturn(student);

        mockMvc.perform(post("/students/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
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

        when(studentService.findStudent(1L)).thenReturn(student);

        mockMvc.perform(get("/students/find/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ron"));
    }

    @Test
    void updateStudentTest() throws Exception {
        Student updated = new Student();
        updated.setId(1L);
        updated.setName("Hermione");
        updated.setAge(19);

        when(studentService.updateStudent(eq(1L), any(Student.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/students/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(19));
    }

    @Test
    void deleteStudentTest() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/students/delete/1"))
                .andExpect(status().isOk());

        verify(studentService, times(1)).deleteStudent(1L);
    }

    @Test
    void filterByAgeTest() throws Exception {
        when(studentService.filterByAge(18))
                .thenReturn(List.of(new Student()));

        mockMvc.perform(get("/students/filter")
                        .param("age", "18"))
                .andExpect(status().isOk());
    }

    @Test
    void filterByAgeBetweenTest() throws Exception {
        when(studentService.filterByAgeBetween(16, 20))
                .thenReturn(List.of(new Student()));

        mockMvc.perform(get("/students/age-between")
                        .param("min", "16")
                        .param("max", "20"))
                .andExpect(status().isOk());
    }

    @Test
    void getStudentFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Gryffindor");

        when(studentService.getStudentFaculty(1L))
                .thenReturn(faculty);

        mockMvc.perform(get("/students/1/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }
}

