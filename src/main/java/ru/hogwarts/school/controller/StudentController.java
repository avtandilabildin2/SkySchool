package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student){
        return studentService.createStudent(student);
    }
    @GetMapping("/find/{id}")
    public Student findStudent(@PathVariable Long id){
        return studentService.findStudent(id);
    }
    @PutMapping("/update/{id}")
    public Student updateStudent(@PathVariable Long id,@RequestBody Student student){
        return studentService.updateStudent(id,student);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
    }
    @GetMapping("/filter")
    public Collection<Student> filterByAge(@RequestParam int age) {
        return studentService.filterByAge(age);
    }
    @GetMapping("/age-between")
    public Collection<Student> filterByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.filterByAgeBetween(min,max);
    }
    @GetMapping("/{id}/faculty")
    public Faculty getStudentFaculty(@PathVariable Long id) {
        return studentService.getStudentFaculty(id);
    }
    @GetMapping("/get-student-count")
    public Long getStudentCount() {
        return studentService.getStudentCount();
    }
    @GetMapping("/get-average-age")
    public Double getAverageAge() {
        return studentService.getAverageAge();
    }
    @GetMapping("/get-last-five-students")
    public Collection<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

}
