package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.entity.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {
}
