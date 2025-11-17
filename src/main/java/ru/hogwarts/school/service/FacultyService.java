package ru.hogwarts.school.service;

import ru.hogwarts.school.entity.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);
    Faculty findFaculty(Long id);
    Faculty updateFaculty(Long id,Faculty faculty);
    void deleteFaculty(Long id);
    Collection<Faculty> filterByColor(String color);
}
