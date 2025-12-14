package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
class FacultyControllerTestMock {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FacultyService facultyService;

    @MockitoBean
    private StudentService studentService;

    @Test
    void createFacultyTest() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");

        when(facultyService.createFaculty(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculties/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }

    @Test
    void findFacultyTest() throws Exception {
        Faculty faculty = new Faculty(1L, "Slytherin", "Green");

        when(facultyService.findFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(get("/faculties/find/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("Green"));
    }

    @Test
    void findFacultyNotFoundTest() throws Exception {
        when(facultyService.findFaculty(99L))
                .thenThrow(new NoSuchElementException("Faculty not found"));

        mockMvc.perform(get("/faculties/find/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateFacultyTest() throws Exception {
        Faculty updated = new Faculty(1L, "Ravenclaw", "Blue");

        when(facultyService.updateFaculty(eq(1L), any(Faculty.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/faculties/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ravenclaw"));
    }

    @Test
    void deleteFacultyTest() throws Exception {
        doNothing().when(facultyService).deleteFaculty(1L);

        mockMvc.perform(delete("/faculties/delete/1"))
                .andExpect(status().isOk());

        verify(facultyService).deleteFaculty(1L);
    }

    @Test
    void filterByColorTest() throws Exception {
        when(facultyService.filterByColor("Red"))
                .thenReturn(List.of(new Faculty(1L, "Gryffindor", "Red")));

        mockMvc.perform(get("/faculties/filter")
                        .param("color", "Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gryffindor"));
    }

    @Test
    void searchFacultyTest() throws Exception {
        when(facultyService.findByNameOrColor("Green"))
                .thenReturn(List.of(new Faculty(1L, "Slytherin", "Green")));

        mockMvc.perform(get("/faculties/search")
                        .param("value", "Green"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value("Green"));
    }

    @Test
    void getStudentsOfFacultyTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Harry");
        student.setAge(18);

        when(studentService.findStudentsByFacultyId(1L))
                .thenReturn(List.of(student));

        mockMvc.perform(get("/faculties/1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Harry"));
    }
}

