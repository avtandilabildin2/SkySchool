package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Faculty with id " + id + " not found")
        );
    }

    @Override
    public Faculty updateFaculty(Long id,Faculty faculty) {
        Faculty existing = facultyRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Faculty with id " + id + " not found")
        );



        existing.setName(faculty.getName());
        existing.setColor(faculty.getColor());

        return facultyRepository.save(existing);

    }

    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> filterByColor(String color) {
        Collection<Faculty> colors=facultyRepository.findAll();
        List<Faculty> result=new ArrayList<>();
        for (Faculty faculty:colors){
            if(faculty.getColor().equals(color)){
                result.add(faculty);
            }
        }
        return result;
    }

    @Override
    public Collection<Faculty> findByNameOrColor(String value) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(value, value);
    }

    @Override
    public List<Student> getFacultyStudents(Long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Faculty not found"));
        return (List<Student>) faculty.getStudents();
    }
}
