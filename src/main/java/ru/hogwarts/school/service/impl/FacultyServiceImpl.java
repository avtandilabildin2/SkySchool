package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private static final Logger logger=LoggerFactory.getLogger(FacultyServiceImpl.class);
    public FacultyServiceImpl(FacultyRepository facultyRepository) {

        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoced method for create faculty!");
        logger.debug("Faculty data: {}",faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        logger.info("Was invoced method for find faculty by id={}!",id);
        return facultyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Faculty with id " + id + " not found"));
    }


    @Override
    public Faculty updateFaculty(Long id,Faculty faculty) {
        logger.info("Was invoked method updateFaculty with id={}", id);
        logger.debug("New faculty data: {}", faculty);

        Faculty existing = facultyRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Faculty with id={} not found", id);
                    return new NoSuchElementException(
                            "Faculty with id " + id + " not found");
                });

        existing.setName(faculty.getName());
        existing.setColor(faculty.getColor());

        return facultyRepository.save(existing);

    }
    @Override
    public void deleteFaculty(Long id) {
        logger.info("Was invoked method deleteFaculty with id={}",id);
        if (!facultyRepository.existsById(id)) {
            logger.warn("Attempt to delete non-existing faculty with id={}", id);
        }
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> filterByColor(String color) {
        logger.info("Was invoked method filterByColor with color {}.",color);
        Collection<Faculty> colors=facultyRepository.findAll();
        List<Faculty> result=new ArrayList<>();
        for (Faculty faculty:colors){
            if(faculty.getColor().equals(color)){
                result.add(faculty);
            }
        }
        logger.debug("Found {} faculties with color {}.",result.size(),color);
        return result;
    }

    @Override
    public Collection<Faculty> findByNameOrColor(String value) {
        logger.info("Was invoked method findByNameOrColor with value {}.",value);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(value, value);
    }

    @Override
    public List<Student> getFacultyStudents(Long id) {
        logger.info("Was invoked method getFacultyStudents with id={}", id);

        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Faculty not found with id={}", id);
                    return new NoSuchElementException("Faculty not found");
                });

        logger.debug("Found {} students for faculty with id={}",
                faculty.getStudents().size(), id);

        return new ArrayList<>(faculty.getStudents());

    }
}
