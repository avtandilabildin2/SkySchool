package ru.hogwarts.school.service;

import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);
    Faculty findFaculty(Long id);
    Faculty updateFaculty(Long id,Faculty faculty);
    void deleteFaculty(Long id);
    Collection<Faculty> filterByColor(String color);
    Collection<Faculty> findByNameOrColor(String value);
    public List<Student> getFacultyStudents(Long id);
}
