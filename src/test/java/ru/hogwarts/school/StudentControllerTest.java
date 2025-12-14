package ru.hogwarts.school;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import org.springframework.web.client.HttpClientErrorException;
import ru.hogwarts.school.entity.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    void addStudentTest() {
        Student student = new Student();
        student.setName("Harry");
        student.setAge(18);

        ResponseEntity<Student> response =
                restTemplate.postForEntity("/students/add", student, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
    }
    @Test
    void findStudentTest() {
        Student student = new Student();
        student.setName("Ron");
        student.setAge(17);

        Student created =
                restTemplate.postForObject("/students/add", student, Student.class);

        Student found =
                restTemplate.getForObject("/students/find/" + created.getId(), Student.class);

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Ron");
    }
    @Test
    void updateStudentTest() {
        Student student = new Student();
        student.setName("Hermione");
        student.setAge(18);

        Student created =
                restTemplate.postForObject("/students/add", student, Student.class);

        Student updated = new Student();
        updated.setName("Hermione Granger");
        updated.setAge(19);

        HttpEntity<Student> entity = new HttpEntity<>(updated);

        ResponseEntity<Student> response =
                restTemplate.exchange(
                        "/students/update/" + created.getId(),
                        HttpMethod.PUT,
                        entity,
                        Student.class
                );

        assertThat(response.getBody().getAge()).isEqualTo(19);
    }
    @Test
    void deleteStudentTest() {
        Student student = new Student();
        student.setName("Draco");
        student.setAge(18);

        Student created =
                restTemplate.postForObject("/students/add", student, Student.class);

        restTemplate.delete("/students/delete/" + created.getId());

        ResponseEntity<Student> response = null;

        try {
            response = restTemplate.getForEntity(
                    "/students/find/" + created.getId(),
                    Student.class
            );
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Test
    void filterByAgeTest() {
        ResponseEntity<Student[]> response =
                restTemplate.getForEntity("/students/filter?age=18", Student[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void filterByAgeBetweenTest() {
        ResponseEntity<Student[]> response =
                restTemplate.getForEntity(
                        "/students/age-between?min=16&max=20",
                        Student[].class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void getStudentFacultyTest() {
        Student student = new Student();
        student.setName("Neville");
        student.setAge(18);

        Student created =
                restTemplate.postForObject("/students/add", student, Student.class);

        ResponseEntity<Object> response =
                restTemplate.getForEntity(
                        "/students/" + created.getId() + "/faculty",
                        Object.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


}

