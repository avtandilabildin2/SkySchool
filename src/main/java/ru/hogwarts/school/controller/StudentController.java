package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
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

}
