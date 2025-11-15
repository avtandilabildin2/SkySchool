package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculties")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }
    @PostMapping("/create")
    public Faculty createFaculty(@RequestBody Faculty faculty){
        return facultyService.createFaculty(faculty);

    }
    @GetMapping("/find/{id}")
    public Faculty findFaculty(@PathVariable Long id){
        return facultyService.findFaculty(id);
    }
    @PutMapping("/update")
    public Faculty updateFaculty(@RequestBody Faculty faculty){
        return facultyService.updateFaculty(faculty);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteFaculty(@PathVariable Long id){
        facultyService.deleteFaculty(id);
    }
    @GetMapping("/filter/{color}")
    public Collection<Faculty> filterByColor(@PathVariable String color){
        return facultyService.filterByColor(color);
    }
}
