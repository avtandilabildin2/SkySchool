package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> students=new HashMap<>();
    private Long id=0L;
    @Override
    public Student createStudent(Student student) {
        student.setId(++id);
        students.put(id,student);
        return student;
    }

    @Override
    public Student findStudent(Long id) {
        return students.get(id);
    }

    @Override
    public Student updateStudent(Student student) {
        students.put(student.getId(),student);
        return student;
    }

    @Override
    public void deleteStudent(Long id) {
        students.remove(id);
    }

    @Override
    public Collection<Student> filterByAge(int age) {
        Collection<Student> ages=students.values();
        Collection<Student> result=new ArrayList<>();
        for(Student student:ages){
            if(student.getAge()==age){
                result.add(student);
            }
        }
        return result;
    }


}
