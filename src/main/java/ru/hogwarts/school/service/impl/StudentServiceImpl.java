package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
       return studentRepository.save(student);
    }

    @Override
    public Student findStudent(Long id) {

        return studentRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Student with id " + id + " not found")
        );
    }

    @Override
    public Student updateStudent(Long id,Student student) {
        Student existing=studentRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Student with id " + id + " not found")
        );


        existing.setName(student.getName());
        existing.setAge(student.getAge());
       return studentRepository.save(existing);

    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> filterByAge(int age) {
        Collection<Student> ages=studentRepository.findAll();
        Collection<Student> result=new ArrayList<>();
        for(Student student:ages){
            if(student.getAge()==age){
                result.add(student);
            }
        }
        return result;
    }


}
