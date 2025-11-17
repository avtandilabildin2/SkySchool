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
    @PutMapping("/update/{id}")
    public Faculty updateFaculty(@PathVariable Long id,@RequestBody Faculty faculty){
        return facultyService.updateFaculty(id,faculty);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteFaculty(@PathVariable Long id){
        facultyService.deleteFaculty(id);
    }
    @GetMapping("/filter")
    public Collection<Faculty> filterByColor(@RequestParam String color){
        return facultyService.filterByColor(color);
    }
}
