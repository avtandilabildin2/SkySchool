package ru.hogwarts.school.service;


import ru.hogwarts.school.entity.Student;

import java.util.Collection;

public interface StudentService {
    Student createStudent(Student student);
    Student findStudent(Long id);
    Student updateStudent(Student student);
    void deleteStudent(Long id);
    Collection<Student> filterByAge(int age);

}
