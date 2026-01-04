package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/faculties")
public class FacultyController {
    private final FacultyService facultyService;
    private  final StudentService studentService;

    public FacultyController(FacultyService facultyService, StudentService studentService) {
        this.facultyService = facultyService;
        this.studentService = studentService;
    }
    @PostMapping("/create")
    public Faculty createFaculty(@RequestBody Faculty faculty){
        return facultyService.createFaculty(faculty);

    }
    @GetMapping("/find/{id}")
    public Faculty findFaculty(@PathVariable Long id) {
        try {
            return facultyService.findFaculty(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @GetMapping("/longest-name")
    public String getLongestFacultyName() {
        return facultyService.getLongestFacultyName();
    }


    @PutMapping("/update/{id}")
    public Faculty updateFaculty(@PathVariable Long id,@RequestBody Faculty faculty){
        return facultyService.updateFaculty(id,faculty);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping("/filter")
    public Collection<Faculty> filterByColor(@RequestParam String color){
        return facultyService.filterByColor(color);
    }
    @GetMapping("/search")
    public Collection<Faculty> searchFaculty(@RequestParam String value) {
        return facultyService.findByNameOrColor(value);
    }
    @GetMapping("/{id}/students")
    public Collection<Student> getStudentsOfFaculty(@PathVariable Long id) {
        return studentService.findStudentsByFacultyId(id);
    }
}
