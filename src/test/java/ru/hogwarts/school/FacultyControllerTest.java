package ru.hogwarts.school;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FacultyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private FacultyService facultyService;

    @Autowired
    private FacultyRepository facultyRepository;

    private Faculty testFaculty;

    @BeforeAll
    void setup(){
        testFaculty = new Faculty();
        testFaculty.setName("Test Faculty");
        testFaculty.setColor("Red");
        testFaculty = facultyService.createFaculty(testFaculty);
    }

    @Test
    void createFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("Gryffindor");
        faculty.setColor("Red");

        ResponseEntity<Faculty> response =
                restTemplate.postForEntity("/faculties/create", faculty, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void findFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("Slytherin");
        faculty.setColor("Green");

        Faculty created =
                restTemplate.postForObject("/faculties/create", faculty, Faculty.class);

        ResponseEntity<Faculty> response =
                restTemplate.getForEntity(
                        "/faculties/find/" + created.getId(),
                        Faculty.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Slytherin");
    }

    @Test
    void updateFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("Ravenclaw");
        faculty.setColor("Blue");

        Faculty created =
                restTemplate.postForObject("/faculties/create", faculty, Faculty.class);

        Faculty updated = new Faculty();
        updated.setName("Ravenclaw Updated");
        updated.setColor("Dark Blue");

        HttpEntity<Faculty> entity = new HttpEntity<>(updated);

        ResponseEntity<Faculty> response =
                restTemplate.exchange(
                        "/faculties/update/" + created.getId(),
                        HttpMethod.PUT,
                        entity,
                        Faculty.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getColor()).isEqualTo("Dark Blue");
    }
    @Test
    void deleteFacultyTest() {
        facultyService.deleteFaculty(testFaculty.getId());

        boolean exists = facultyRepository.existsById(testFaculty.getId());
        Assertions.assertFalse(exists, "Faculty should not exist");
    }






    @Test
    void filterByColorTest() {
        ResponseEntity<Faculty[]> response =
                restTemplate.getForEntity(
                        "/faculties/filter?color=Red",
                        Faculty[].class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void searchFacultyTest() {
        ResponseEntity<Faculty[]> response =
                restTemplate.getForEntity(
                        "/faculties/search?value=Green",
                        Faculty[].class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getStudentsOfFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("TestFaculty");
        faculty.setColor("Black");

        Faculty createdFaculty =
                restTemplate.postForObject("/faculties/create", faculty, Faculty.class);

        ResponseEntity<Student[]> response =
                restTemplate.getForEntity(
                        "/faculties/" + createdFaculty.getId() + "/students",
                        Student[].class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}

