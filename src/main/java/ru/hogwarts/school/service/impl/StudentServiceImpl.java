package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private static final Logger logger= LoggerFactory.getLogger(StudentServiceImpl.class);
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.info("Was invoced method for create student!");
        logger.debug("Student data: {}",student);
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(Long id) {
        logger.info("Was invoced method for find student with id={}",id);
        return studentRepository.findById(id).orElseThrow(() -> {
            logger.error("There is no student with id={}", id);
            throw new NoSuchElementException("Student with id " + id + " not found");
        });
    }

    @Override
    public Student updateStudent(Long id,Student student) {
        logger.info("Was invoced method for update student with id={}",id);
        logger.debug("Student data: {}",student);
        Student existing=studentRepository.findById(id).orElseThrow(()->{
            logger.error("There is no student with id={}", id);
            throw new NoSuchElementException("Student with id " + id + " not found");
        });


        existing.setName(student.getName());
        existing.setAge(student.getAge());
        return studentRepository.save(existing);

    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Was invoced method for delete student with id={}",id);
        if(!studentRepository.existsById(id)){
            logger.warn("There is no student with id={}", id);
        }

        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> filterByAge(int age) {
        logger.info("Was invoced method for filter by student age={}",age);
        return studentRepository.findAllByAge(age);

    }
    @Override
    public Collection<Student> filterByAgeBetween(int min, int max) {
        logger.info("Was invoced method for filter by student age between {} and {}",min,max);
        if (min>max){
            logger.warn("Min value is greater than max value");
        }
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getStudentFaculty(Long id) {
        logger.info("Was invoced method for get student faculty with id={}",id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("There is no student with id={}", id);
                    return new NoSuchElementException("Student not found");
                });
        return student.getFaculty();
    }
    @Override
    public Collection<Student> findStudentsByFacultyId(Long facultyId) {
        logger.info("Was invoced method for find students by faculty id={}",facultyId);
        return studentRepository.findAllByFacultyId(facultyId);
    }

    @Override
    public Long getStudentCount() {
        logger.info("Was invoced method for get student count");
        Long count= studentRepository.getStudentCount();
        logger.debug("Student count: {}",count);
        return count;
    }

    @Override
    public Double getAverageAge() {
        logger.info("Was invoced method for get average age");
        Double averageAge= studentRepository.getAverageAge();
        logger.debug("Average age: {}",averageAge);
        return averageAge;
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.info("Was invoced method for get last five students");
        List<Student> lastFiveStudents= studentRepository.getLastFiveStudents();
        logger.debug("Last five students: {}",lastFiveStudents);
        return lastFiveStudents;
    }


}