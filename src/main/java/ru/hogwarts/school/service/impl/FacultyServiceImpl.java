package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long,Faculty> faculties=new HashMap<>();
    private Long id=0L;
    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++id);
        faculties.put(id,faculty);
        return faculty;
    }

    @Override
    public Faculty findFaculty(Long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        return faculties.put(faculty.getId(),faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        faculties.remove(id);
    }

    @Override
    public Collection<Faculty> filterByColor(String color) {
        Collection<Faculty> colors=faculties.values();
        List<Faculty> result=new ArrayList<>();
        for (Faculty faculty:colors){
            if(faculty.getColor().equals(color)){
                result.add(faculty);
            }
        }
        return result;
    }
}
