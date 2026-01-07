package ru.hogwarts.school.service;


import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student createStudent(Student student);
  Student findStudent(Long id);
  Student updateStudent(Long id,Student student);
  void deleteStudent(Long id);
  Collection<Student> filterByAge(int age);
  Collection<Student> filterByAgeBetween(int min, int max);
  Faculty getStudentFaculty(Long id);
  Collection<Student> findStudentsByFacultyId(Long facultyId);
  Long getStudentCount();
  Double getAverageAge();
  List<Student> getLastFiveStudents();
  List<String> getStudentsNamesStartingWithA();
  double getAverageStudentsAge();
  List<Student> getAllStudents();

}
