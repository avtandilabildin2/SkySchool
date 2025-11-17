package ru.hogwarts.school.service;


import ru.hogwarts.school.entity.Student;

import java.util.Collection;
import java.util.Optional;

public interface StudentService {
    Student createStudent(Student student);
    Student findStudent(Long id);
    Student updateStudent(Long id,Student student);
    void deleteStudent(Long id);
    Collection<Student> filterByAge(int age);

}
