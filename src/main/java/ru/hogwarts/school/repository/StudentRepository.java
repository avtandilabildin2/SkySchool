package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.entity.Student;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Collection<Student> findByAgeBetween(int min, int max);

    Collection<Student> findAllByFacultyId(Long facultyId);

    Collection<Student> findAllByAge(int age);
    @Query(value = "select count(*) from student", nativeQuery = true)
    Long getStudentCount();
    @Query(value = "select avg(age) from student", nativeQuery = true)
    Double getAverageAge();
    @Query(value = "select * from student order by age desc limit 5", nativeQuery = true)
    List<Student> getLastFiveStudents();



}
